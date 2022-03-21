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
        int quantityTotal = rebels.size();
        int quantityOfTraitors = countTraitor(rebels);
        int quantityOfRebels = quantityTotal - quantityOfTraitors;

        Map<ItemInventory, Double> mapAverage = calculateAverageOfItemsPerRebel(rebels);

        int sumLostPoints = countLostPointsBecauseOfTraitors(rebels);
        Double percentageOfRebels = (double) quantityOfRebels / quantityTotal;
        Double percentageOfTraitors = (double) quantityOfTraitors / quantityTotal;

        return StatisticsDTO.builder()
                .averageOfItems(mapAverage)
                .percentageOfRebels(Double.isNaN(percentageOfRebels) ? 0.0 : percentageOfRebels)
                .percentageOfTraitors(Double.isNaN(percentageOfTraitors) ? 0.0 : percentageOfTraitors)
                .lostPoints(sumLostPoints)
                .build();
    }

    private int countTraitor(List<Rebel> rebels) {
        return rebels.stream().filter(Rebel::isTraitor).collect(Collectors.toList()).size();
    }

    private Map<ItemInventory, Double> calculateAverageOfItemsPerRebel(List<Rebel> rebels) {

        List<Item> items = rebels.stream().filter((rebel -> !rebel.isTraitor()))
                .map(Rebel::getInventory)
                .flatMap(List::stream).collect(Collectors.toList());

        Map<ItemInventory, Double> itemInventoryMap = items.stream()
                .collect(Collectors.groupingBy(Item::getItem, Collectors.averagingInt(Item::getQuantity)));

        return itemInventoryMap;
    }

    private int countLostPointsBecauseOfTraitors(List<Rebel> rebels) {
        int sum = rebels.stream().filter(Rebel::isTraitor)
                .map(Rebel::getInventory)
                .flatMap(List::stream).collect(Collectors.toList())
                .stream().mapToInt(Item::getQuantity).sum();
        return sum;
    }
}
