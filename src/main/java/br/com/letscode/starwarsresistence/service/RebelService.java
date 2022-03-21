package br.com.letscode.starwarsresistence.service;

import br.com.letscode.starwarsresistence.dto.ItemDTO;
import br.com.letscode.starwarsresistence.enums.ItemInventory;
import br.com.letscode.starwarsresistence.exception.DuplicateItemsInventoryException;
import br.com.letscode.starwarsresistence.exception.InvalidNegotiationException;
import br.com.letscode.starwarsresistence.exception.InvalidReportException;
import br.com.letscode.starwarsresistence.exception.RebelNotFoundException;
import br.com.letscode.starwarsresistence.model.Item;
import br.com.letscode.starwarsresistence.model.Location;
import br.com.letscode.starwarsresistence.model.Rebel;
import br.com.letscode.starwarsresistence.model.Report;
import br.com.letscode.starwarsresistence.repository.LocationRepository;
import br.com.letscode.starwarsresistence.repository.RebelRepository;
import br.com.letscode.starwarsresistence.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RebelService {
    private final LocationRepository locationRepository;
    private final RebelRepository rebelRepository;
    private final ReportRepository reportRepository;

    public List<Rebel> getAllRebel(){
        return rebelRepository.findAll();
    }

    public Rebel getRebelById(Long id){
        return rebelRepository.findById(id).orElseThrow(() -> new RebelNotFoundException("Rebel " + id + " not found"));
    }

    public Rebel saveRebel(Rebel rebel){
        long inventorySizeDistinctItems = rebel.getInventory().stream().distinct().count();

        if (inventorySizeDistinctItems != ItemInventory.values().length) {
            throw new DuplicateItemsInventoryException("Duplicate items in the inventory is not allow");
        }
        return rebelRepository.save(rebel);
    }

    public Location updateRebelLocation(Location location, Long id){
        Rebel rebel = getRebelById(id);
        rebel.setLocation(location);

        return rebelRepository.save(rebel).getLocation();
    }

    public void deleteRebel(Long rebelId){
        if (!rebelRepository.existsById(rebelId)) {
            throw new RebelNotFoundException("Rebel " + rebelId + " not found");
        }
        rebelRepository.deleteById(rebelId);
    }

    public void reportRebelTraitor(Rebel accuser, Rebel traitor){
        if (traitor.getId().equals(accuser.getId())) {
            throw new InvalidReportException("The rebel can't self-report.");
        }

        Optional<Report> accuserFound = reportRepository.findByAccuserAndTraitor(accuser, traitor);
        if (!accuserFound.isEmpty()) {
            throw new InvalidReportException("Report already registered.");
        }

        Report report = Report.builder()
                .accuser(accuser)
                .traitor(traitor)
                .build();
        traitor.getReports().add(report);

        if (traitor.getReports().size() == 3) {
            traitor.setTraitor(true);
        }
        reportRepository.save(report);
        rebelRepository.save(traitor);
    }

    public void negociateItems(Long buyerId, Long sellerId, List<Item> buyerItems, List<Item> sellerItems) {
        Rebel buyerRebel = getRebelById(buyerId);
        Rebel sellerRebel = getRebelById(sellerId);
        checkTreason(buyerRebel);
        checkTreason(sellerRebel);

        if (buyerId.equals(sellerId)) {
            throw new InvalidNegotiationException("The rebel can't trade with himself.");
        }

        if (!validateQuantityOfItemsInInventory(buyerRebel,buyerItems)) {
            throw new InvalidNegotiationException("The rebel ID " + buyerRebel.getId() + " doesn't have enough for this trade. ");
        }

        if (!validateQuantityOfItemsInInventory(sellerRebel, sellerItems)) {
            throw new InvalidNegotiationException("The rebel ID " + sellerRebel.getId() + " doesn't have enough for this trade. ");
        }

        validateDuplicateItemsInRequestInventory(buyerItems);
        validateDuplicateItemsInRequestInventory(sellerItems);

        checkPoints(buyerItems, sellerItems);

        addItems(buyerRebel.getInventory(), sellerItems);
        removeItems(buyerRebel.getInventory(), buyerItems);
        addItems(sellerRebel.getInventory(), buyerItems);
        removeItems(sellerRebel.getInventory(),sellerItems);

        rebelRepository.saveAll(List.of(buyerRebel, sellerRebel));
    }

    private boolean validateQuantityOfItemsInInventory(Rebel rebel, List<Item> items) {
        return items.stream()
                .allMatch((itemFromRequest) -> rebel.getInventory().stream()
                        .anyMatch((rebelItem) -> {
                            if (itemFromRequest.getItem().equals(rebelItem.getItem())) {
                                return rebelItem.getQuantity() >= itemFromRequest.getQuantity();
                            }
                            return false;
                        }));
    }

    private void validateDuplicateItemsInRequestInventory(List<Item> items) {
        Integer rebelItemsFromRequestSize = (int) items.stream().map(Item::getItem).distinct().count();
        if (rebelItemsFromRequestSize < items.size()) {
            throw new DuplicateItemsInventoryException("Duplicate items for trade not allow.");
        }
    }

    private void removeItems(List<Item> inventory, List<Item> items) {
        for (Item itemToTrade: items) {
            for (Item itemFullInventory: inventory) {
                if (itemFullInventory.getItem().equals(itemToTrade.getItem())) {
                    itemFullInventory.setQuantity(itemFullInventory.getQuantity() - itemToTrade.getQuantity());
                    break;
                }
            }
        }
    }

    private void addItems(List<Item> inventory, List<Item> items) {
        for (Item itemToTrade: items) {
            for (Item itemFullInventory: inventory) {
                if (itemFullInventory.getItem().equals(itemToTrade.getItem())) {
                    itemFullInventory.setQuantity(itemFullInventory.getQuantity() + itemToTrade.getQuantity());
                    break;
                }
            }
        }
    }

    private void checkPoints(List<Item> buyerItems, List<Item> sellerItems) {
        Integer purchasePrice = buyerItems.stream()
                .mapToInt((item) -> item.getQuantity() * item.getItem().getPoints()).sum();
        Integer paymentPrice = sellerItems.stream()
                .mapToInt((item) -> item.getQuantity() * item.getItem().getPoints()).sum();

        boolean equals = purchasePrice.equals(paymentPrice);
        if(!equals) {
            throw new InvalidNegotiationException("The sum of rebel item points are not equivalent.");
        }
     }

    private void checkTreason(Rebel rebel) {
        if (rebel.isTraitor()) {
            throw new InvalidNegotiationException("Negotiation invalid. The rebel ID " + rebel.getId() + " is a traitor!!!");
        }
    }

}
