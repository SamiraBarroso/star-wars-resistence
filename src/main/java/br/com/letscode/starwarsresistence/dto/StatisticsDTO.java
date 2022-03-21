package br.com.letscode.starwarsresistence.dto;

import br.com.letscode.starwarsresistence.enums.ItemInventory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Builder
@Getter
public class StatisticsDTO{
    private Double percentageOfTraitors;
    private Double percentageOfRebels;
    private Map<ItemInventory, Double> averageOfItems;
    private int lostPoints;
}
