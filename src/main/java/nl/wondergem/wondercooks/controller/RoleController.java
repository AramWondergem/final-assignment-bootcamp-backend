package nl.wondergem.wondercooks.controller;


import nl.wondergem.wondercooks.dto.RoleDto;
import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.service.RoleService;
import nl.wondergem.wondercooks.util.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }


    @PostMapping("")
    public ResponseEntity<Object> createRole(@Valid @RequestBody RoleDto role, BindingResult br) {

        if (br.hasErrors()) {
            String erroMessage = Util.badRequestMessageGenerator(br);
            throw new BadRequestException(erroMessage);
        } else {
            String createdID = service.createRole(role);
            URI uri = Util.uriGenerator("/roles/", createdID);
            return ResponseEntity.created(uri).body("Role created");
        }
    }



    @GetMapping("")
    public ResponseEntity<Object> getAllRoles() {
        return ResponseEntity.ok(service.getAllRoles());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRole (@PathVariable String id) {
        service.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
