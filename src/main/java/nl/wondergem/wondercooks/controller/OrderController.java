package nl.wondergem.wondercooks.controller;

import nl.wondergem.wondercooks.dto.OrderDto;
import nl.wondergem.wondercooks.dto.inputDto.OrderInputDto;
import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.service.OrderService;
import nl.wondergem.wondercooks.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Controller
@RequestMapping("${apiPrefix}/orders")
public class OrderController {

    @Autowired
    private Environment env;

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    //Post
    @PostMapping("")
    public ResponseEntity<Object> createOrder(@Valid @RequestBody OrderInputDto orderInputDto, BindingResult br) throws Exception {

        if (br.hasErrors()) {
            String errorMessage = Util.badRequestMessageGenerator(br);
            throw new BadRequestException(errorMessage);
        } else {
            OrderDto orderDto = orderService.saveOrder(orderInputDto);
            URI uri = Util.uriGenerator(env.getProperty("apiPrefix") + "/orders/" + orderDto.getId());
            return ResponseEntity.created(uri).header("Order-id", String.valueOf(orderDto.getId())).body("order created");
        }
    }

    //Get
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrder(@PathVariable long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    //update
    // check if number of menu's is the same or higher

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable long id, @Valid @RequestBody OrderInputDto orderInputDto, BindingResult br) {

        if (br.hasErrors()) {
            String errorMessage = Util.badRequestMessageGenerator(br);
            throw new BadRequestException(errorMessage);
        } else {
            orderService.updateOrder(orderInputDto, id);
            return ResponseEntity.ok("order updated");
        }
    }

    //delete
    //check if it is before orderDeadline of menu

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/accept/{id}")
    public ResponseEntity<Object> acceptOrder(@PathVariable long id) {
        orderService.acceptOrder(id);
        return ResponseEntity.ok("order accepted");
    }

    @PutMapping("/decline/{id}")
    public ResponseEntity<Object> declineOrder(@PathVariable long id) {
        orderService.declineOrder(id);
        return ResponseEntity.ok("order declined");
    }




}
