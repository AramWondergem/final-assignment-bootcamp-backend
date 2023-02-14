package nl.wondergem.wondercooks.controller;

import nl.wondergem.wondercooks.dto.inputDto.UserInputDto;
import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.security.MyUserDetails;
import nl.wondergem.wondercooks.service.CookCustomerService;
import nl.wondergem.wondercooks.service.UserService;
import nl.wondergem.wondercooks.util.StringGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("${apiPrefix}/cookcustomer")
public class CookCustomerController {

    private final CookCustomerService cookCustomerService;

    public CookCustomerController(CookCustomerService cookCustomerService, UserService userService) {
        this.cookCustomerService = cookCustomerService;
    }

    @PostMapping("/cook")
    public ResponseEntity<Object> createRelation(@Valid @RequestBody UserInputDto userInputDto, BindingResult br) {

        if (br.hasErrors()) {
            String errorMessage = StringGenerator.badRequestMessageGenerator(br);
            throw new BadRequestException(errorMessage);
        } else {
            MyUserDetails ud = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            cookCustomerService.createRelationCook(ud, userInputDto);

            return ResponseEntity.ok("relation created");
        }
    }

    @PostMapping("/customer/{id}")
    public ResponseEntity<Object> createRelation(@PathVariable long id) {

        MyUserDetails ud = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cookCustomerService.createRelationCustomer(ud, id);

        return ResponseEntity.ok("relation created");

    }


}
