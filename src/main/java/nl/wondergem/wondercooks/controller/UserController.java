package nl.wondergem.wondercooks.controller;

import nl.wondergem.wondercooks.dto.UserDto;
import nl.wondergem.wondercooks.dto.inputDto.UserInputDto;
import nl.wondergem.wondercooks.dto.inputDto.UserUpdateDto;
import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.model.EmailDetails;
import nl.wondergem.wondercooks.service.EmailServiceImpl;
import nl.wondergem.wondercooks.service.UserService;
import nl.wondergem.wondercooks.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("${apiPrefix}/users")
public class UserController {

    @Autowired
    private Environment env;

    @Autowired
    EmailServiceImpl emailService;
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @PostMapping("")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserInputDto userInputDto, BindingResult br) {

        if (br.hasErrors()) {
            String errorMessage = Util.badRequestMessageGenerator(br);
            throw new BadRequestException(errorMessage);
        } else {
            UserDto userDto = service.saveUser(userInputDto);
            URI uri = Util.uriGenerator(env.getProperty("apiPrefix")+ "/users/");
            EmailDetails emailDetails = new EmailDetails("wonderreclame@gmail.com", "nieuw account", "nieuw account");
            emailService.sendSimpleMail(emailDetails);
            return ResponseEntity.created(uri).body("user created");
        }
    }
    // get one user
    @GetMapping ("")
    public ResponseEntity<Object> getUser() {
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(service.getUser(ud.getUsername()));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers(){
        return ResponseEntity.ok(service.getAllUsers());
    }


    @PutMapping("")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto, BindingResult br) {
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (br.hasErrors()) {
            String errorMessage = Util.badRequestMessageGenerator(br);
            throw new BadRequestException(errorMessage);
        } else {
            UserDto userDto = service.updateUser(userUpdateDto,ud.getUsername());
            return ResponseEntity.ok(userDto);
        }

    }

    @PutMapping("/cook")
    public ResponseEntity<Object> updateRoleWithCook() {
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        service.updateRoleWithCook(ud.getUsername());
        return ResponseEntity.ok().body("Roles updated with cook");

    }

    @DeleteMapping("")
    public ResponseEntity<Object> deleteUser() {
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        service.deleteUser(ud.getUsername());
        return ResponseEntity.noContent().build();
    }

}
