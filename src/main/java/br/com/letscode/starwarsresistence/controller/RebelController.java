package br.com.letscode.starwarsresistence.controller;

import br.com.letscode.starwarsresistence.converts.ItemMapper;
import br.com.letscode.starwarsresistence.converts.LocationMapper;
import br.com.letscode.starwarsresistence.converts.RebelMapper;
import br.com.letscode.starwarsresistence.dto.*;
import br.com.letscode.starwarsresistence.service.RebelService;
import br.com.letscode.starwarsresistence.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/rebels")
@RequiredArgsConstructor
public class RebelController {

    private final RebelService rebelService;
    private final RebelMapper rebelMapper;
    private final StatisticService statisticService;
    private final LocationMapper locationMapper;
    private final ItemMapper itemMapper;

    @GetMapping
    public List<RebelDTO> getAll(){
        return rebelService.getAllRebel().stream().map(rebelMapper::rebelToRebelDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RebelDTO> getById(@PathVariable Long id) {
        var rebelDTO = rebelMapper.rebelToRebelDTO(rebelService.getRebelById(id));
        return ResponseEntity.ok(rebelDTO);
    }

    @PostMapping
    public ResponseEntity<RebelDTO> save(@Valid @RequestBody RebelCreateDTO rebelCreateDTO) {
        var rebel = rebelService.saveRebel(rebelMapper.rebelCreateDTOTORebel(rebelCreateDTO));
        return ResponseEntity.ok(rebelMapper.rebelToRebelDTO(rebel));
    }

    @PutMapping(path = "/{rebelId}/report-location")
    private ResponseEntity<LocationDTO> updateRebelLocation(@PathVariable("rebelId") Long rebelId,
                                                                    @Valid @RequestBody LocationDTO locationDto) {
        var location = rebelService.updateRebelLocation(locationMapper.locationDTOToLocation(locationDto), rebelId);
        return ResponseEntity.ok(locationMapper.locationToLocationDTO(location));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        rebelService.deleteRebel(id);
        return new ResponseEntity<>("Delete made successfully", HttpStatus.OK);
    }

    @PostMapping(path = "/{accuserId}/report-traitor")
    private ResponseEntity<String> reportRebelTraitor(@PathVariable("accuserId") Long accuserId,
                                                              @Valid @RequestBody SendReportDTO reportDTO) {

        var accuser = this.rebelService.getRebelById(accuserId);
        var traitor = this.rebelService.getRebelById(reportDTO.traitor_id());
        rebelService.reportRebelTraitor(accuser, traitor);
        return new ResponseEntity<>("Report made successfully.", HttpStatus.OK);
    }



    @PostMapping(path = "/{buyerRebel}/negotiations")
    public ResponseEntity<String> negotiateItems(@PathVariable("buyerRebel") Long buyerId, @Valid @RequestBody NegotiationDTO negotiationDTO) {
        rebelService.negociateItems(buyerId, negotiationDTO.sellerId(),
                negotiationDTO.buyerItems().stream().map(itemMapper::itemDTOToItem).collect(Collectors.toList()),
                negotiationDTO.sellerItems().stream().map(itemMapper::itemDTOToItem).collect(Collectors.toList()));
        return new ResponseEntity<>("Negotiation  made successfully.", HttpStatus.OK);
    }

    @GetMapping(path = "/data")
    public ResponseEntity<StatisticsDTO> sendStatistics() {
        return new ResponseEntity<>(statisticService.sendStatistics(), HttpStatus.OK);
    }

}
