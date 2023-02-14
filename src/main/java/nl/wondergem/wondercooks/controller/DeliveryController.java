package nl.wondergem.wondercooks.controller;


import nl.wondergem.wondercooks.dto.DeliveryDto;
import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.service.DeliveryService;
import nl.wondergem.wondercooks.util.StringGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("${apiPrefix}/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    //update delivery

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDelivery(@PathVariable long id, @Valid @RequestBody DeliveryDto deliveryDto, BindingResult br) {

        if (br.hasErrors()) {
            String errorMessage = StringGenerator.badRequestMessageGenerator(br);
            throw new BadRequestException(errorMessage);
        } else {
            deliveryService.updateDelivery(deliveryDto, id);
            return ResponseEntity.ok("delivery updated");
        }
    }

    @PutMapping("/eta/{id}")
    public ResponseEntity<Object> sendETA(@PathVariable long id) {

        deliveryService.sendETA(id);
        return ResponseEntity.ok("ETA is sent to customer");

    }


}
