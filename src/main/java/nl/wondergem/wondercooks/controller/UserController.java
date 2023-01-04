package nl.wondergem.wondercooks.controller;

import nl.wondergem.wondercooks.dto.UserInputDto;
import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.service.UserService;
import nl.wondergem.wondercooks.util.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // get all users
    @PostMapping("")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserInputDto userInputDto, BindingResult br) {

        if (br.hasErrors()) {
            String erroMessage = Util.badRequestMessageGenerator(br);
            throw new BadRequestException(erroMessage);
        } else {
            String createdID = service.saveUser(userInputDto);
            URI uri = Util.uriGenerator("/users/", createdID);
            return ResponseEntity.created(uri).body("User created");
        }
    }
    // get one user
    @GetMapping ("/{username}")
    public ResponseEntity<Object> getUser(@PathVariable String username) {
        return ResponseEntity.ok(service.getUser(username));
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllUsers(){
        return ResponseEntity.ok(service.getAllUsers());
    }

    @PutMapping("/{username}")
    public ResponseEntity<Object> updateUser(@PathVariable String username, @Valid UserInputDto userInputDto, BindingResult br) {
        if (br.hasErrors()) {
            String erroMessage = Util.badRequestMessageGenerator(br);
            throw new BadRequestException(erroMessage);
        } else {
            service.updateUser(username, userInputDto);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Object> deleteRole (@PathVariable String username) {
        service.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

}
