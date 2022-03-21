package br.com.letscode.starwarsresistence.util;

import br.com.letscode.starwarsresistence.enums.Genre;
import br.com.letscode.starwarsresistence.enums.ItemInventory;
import br.com.letscode.starwarsresistence.model.Item;
import br.com.letscode.starwarsresistence.model.Location;
import br.com.letscode.starwarsresistence.model.Rebel;

import java.util.ArrayList;
import java.util.List;

public class RebelUtil {

    public static Rebel createRebel(Long id){
        return Rebel.builder()
                .id(id)
                .name("Test")
                .age(20)
                .genre(Genre.FEMALE)
                .isTraitor(false)
                .location(Location.builder()
                        .id(1L)
                        .baseName("Test")
                        .latitude(123456789)
                        .longitude(123456789)
                        .build())
                .inventory(createItemList(1, 1, 1, 1))
                .reports(new ArrayList<>())
                .build();
    }

    public static List<Item> createItemList(int weaponQuantity, int waterQuantity, int foodQuantity, int bulletQuantity) {
        return List.of(Item.builder().id(1L).item(ItemInventory.GUN).quantity(weaponQuantity).build(),
                Item.builder().id(2L).item(ItemInventory.WATER).quantity(waterQuantity).build(),
                Item.builder().id(3L).item(ItemInventory.FOOD).quantity(foodQuantity).build(),
                Item.builder().id(4L).item(ItemInventory.MUNITION).quantity(bulletQuantity).build()
        );
    }

}
