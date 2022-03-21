package br.com.letscode.starwarsresistence.service;

import br.com.letscode.starwarsresistence.enums.Genre;
import br.com.letscode.starwarsresistence.enums.ItemInventory;
import br.com.letscode.starwarsresistence.model.Item;
import br.com.letscode.starwarsresistence.model.Location;
import br.com.letscode.starwarsresistence.model.Rebel;
import br.com.letscode.starwarsresistence.repository.*;
import br.com.letscode.starwarsresistence.util.RebelUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class RebelServiceTest {
    @InjectMocks
    private RebelService rebelService;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private RebelRepository rebelRepository;

    @Mock
    private ReportRepository reportRepository;

    @Test
    @DisplayName("Save rebel when successful")
    void saveRebel_WhenSuccessful() {
        Rebel rebelToBeSaved = RebelUtil.createRebel(1L);

        Mockito.when(rebelService.saveRebel(rebelToBeSaved)).thenReturn(rebelToBeSaved);

        Rebel rebel = rebelService.saveRebel(rebelToBeSaved);
        Assertions.assertThat(rebel).isNotNull();
        Assertions.assertThat(rebel.getName()).isEqualTo("Test");
        Assertions.assertThat(rebel.getAge()).isEqualTo(20);
        Assertions.assertThat(rebel.getGenre()).isEqualTo(Genre.FEMALE);
        Assertions.assertThat(rebel.getLocation().getBaseName()).isEqualTo("Test");
        Assertions.assertThat(rebel.getLocation().getLatitude()).isEqualTo(123456789L);
        Assertions.assertThat(rebel.getLocation().getLongitude()).isEqualTo(123456789L);
    }
    @Test
    @DisplayName("Find Rebel by id with successful")
    void findRebelById_WhenSuccessful() {
        Rebel rebel = RebelUtil.createRebel(1L);

        Mockito.when(this.rebelRepository.findById(rebel.getId())).thenReturn(Optional.of(rebel));

        Rebel rebelFound = this.rebelService.getRebelById(1L);
        Assertions.assertThat(rebelFound).isNotNull();
        Assertions.assertThat(rebelFound.getName()).isEqualTo("Test");
        Assertions.assertThat(rebelFound.getAge()).isEqualTo(20);
        Assertions.assertThat(rebelFound.getGenre()).isEqualTo(Genre.FEMALE);
        Assertions.assertThat(rebelFound.getLocation().getBaseName()).isEqualTo("Test");
        Assertions.assertThat(rebelFound.getLocation().getLatitude()).isEqualTo(123456789L);
        Assertions.assertThat(rebelFound.getLocation().getLongitude()).isEqualTo(123456789L);
    }

    @Test
    @DisplayName("Find all Rebels when successful")
    void findAllRebels_ReturnsListOfRebels_WhenSuccessful() {
        Rebel rebel = RebelUtil.createRebel(1L);

        List<Rebel> rebels = List.of(rebel);

        Mockito.when(this.rebelService.getAllRebel()).thenReturn(rebels);

        List<Rebel> savedRebels = this.rebelService.getAllRebel();

        Assertions.assertThat(savedRebels).isNotEmpty().contains(rebel);
    }

    @Test
    @DisplayName("Report Rebel Traitor when successful")
    void reportRebelTraitor_WhenSuccessful() {
        Rebel accuser = RebelUtil.createRebel(1L);
        Rebel accused = RebelUtil.createRebel(2L);

        this.rebelService.reportRebelTraitor(accuser, accused);

        System.out.println(accused.getReports().size());
        Assertions.assertThat(accused.getReports()).isNotEmpty();
        Assertions.assertThat(accused.getReports().get(0).getAccuser()).isEqualTo(accuser);

    }

    @Test
    @DisplayName("Negotiation when successful")
    void trade_WhenSuccessful() {
        Rebel buyerRebel = RebelUtil.createRebel(1l);
        Rebel sellerRebel = RebelUtil.createRebel(2l);
        List<Item> buyerRebelItems = RebelUtil.createItemList(1, 0, 0, 0);
        List<Item> sellerRebelItems = RebelUtil.createItemList(0, 0, 1, 1);

        this.rebelService.negociateItems(1L, 2L, buyerRebelItems, sellerRebelItems);
        Assertions.assertThat(buyerRebel.getInventory().stream().filter((item) -> item.getItem().equals(ItemInventory.GUN)).findFirst().get().getQuantity()).isEqualTo(0);
        Assertions.assertThat(sellerRebel.getInventory().stream().filter((item) -> item.getItem().equals(ItemInventory.FOOD)).findFirst().get().getQuantity()).isEqualTo(0);
        Assertions.assertThat(sellerRebel.getInventory().stream().filter((item) -> item.getItem().equals(ItemInventory.MUNITION)).findFirst().get().getQuantity()).isEqualTo(0);

    }



}
