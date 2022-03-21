package br.com.letscode.starwarsresistence.service;

import br.com.letscode.starwarsresistence.dto.StatisticsDTO;
import br.com.letscode.starwarsresistence.enums.ItemInventory;
import br.com.letscode.starwarsresistence.model.Item;
import br.com.letscode.starwarsresistence.model.Rebel;
import br.com.letscode.starwarsresistence.repository.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final RebelRepository rebelRepository;

    public StatisticsDTO sendStatistics() {
        List<Rebel> rebels = rebelRepository.findAll();
        int quantTotal = rebels.size();
        int quantTraitors = countTraitor(rebels);
        int quantRebels = quantTotal - quantTraitors;

        Map<ItemInventory, Double> mapAverage = averageItemsRebel(rebels);

        int sumLostPoints = lostPointsTraitors(rebels);
        Double percentageRebels = (double) quantRebels / quantTotal;
        Double percentageTraitors = (double) quantTraitors / quantTotal;

        return StatisticsDTO.builder()
                .averageOfItems(mapAverage)
                .percentageOfRebels(Double.isNaN(percentageRebels) ? 0.0 : percentageRebels)
                .percentageOfTraitors(Double.isNaN(percentageTraitors) ? 0.0 : percentageTraitors)
                .lostPoints(sumLostPoints)
                .build();
    }

    private int countTraitor(List<Rebel> rebels) {
        return rebels.stream().filter(Rebel::isTraitor).collect(Collectors.toList()).size();
    }

    private Map<ItemInventory, Double> averageItemsRebel(List<Rebel> rebels) {
        List<Item> items = rebels.stream().filter((rebel -> !rebel.isTraitor()))
                .map(Rebel::getInventory)
                .flatMap(List::stream).collect(Collectors.toList());
        Map<ItemInventory, Double> inventory = items.stream()
                .collect(Collectors.groupingBy(Item::getItem, Collectors.averagingInt(Item::getQuantity)));

        return inventory;
    }

    private int lostPointsTraitors(List<Rebel> rebels) {
        int sum = rebels.stream().filter(Rebel::isTraitor)
                .map(Rebel::getInventory)
                .flatMap(List::stream).collect(Collectors.toList())
                .stream().mapToInt(Item::getQuantity).sum();
        return sum;
    }
}
