package nl.wondergem.wondercooks.controller;

import nl.wondergem.wondercooks.dto.UserDto;
import nl.wondergem.wondercooks.dto.inputDto.PasswordRequest;
import nl.wondergem.wondercooks.dto.inputDto.UserInputDto;
import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.service.UserService;
import nl.wondergem.wondercooks.util.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("${apiPrefix}/users")
public class UserController {

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
            URI uri = Util.uriGenerator("/{apiPrefix}/users/");
            return ResponseEntity.created(uri).body(userDto);
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
    //todo misschien weghalen
//    @PutMapping("/{username}")
//    public ResponseEntity<Object> updateUser(@PathVariable String username, @Valid UserInputDto userInputDto, BindingResult br) {
//        if (br.hasErrors()) {
//            String erroMessage = Util.badRequestMessageGenerator(br);
//            throw new BadRequestException(erroMessage);
//        } else {
//            service.updateUser(username, userInputDto);
//            return ResponseEntity.noContent().build();
//        }
//    }

    @PutMapping("")
    public ResponseEntity<Object> updatePassword(@Valid @RequestBody PasswordRequest passwordRequest) {

        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        service.updatePassword(ud,passwordRequest);
        return ResponseEntity.ok().body("password updated");

    }

    @PutMapping("/cook")
    public ResponseEntity<Object> updateRoleWithCook() {

        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        service.updateRoleWithCook(ud);
        return ResponseEntity.ok().body("Roles updated with cook");

    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Object> deleteRole (@PathVariable String username) {
        service.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

}
