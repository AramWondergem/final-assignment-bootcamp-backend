package nl.wondergem.wondercooks.controller;

import nl.wondergem.wondercooks.dto.MenuDto;
import nl.wondergem.wondercooks.dto.UserDto;
import nl.wondergem.wondercooks.dto.inputDto.MenuInputDto;
import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.model.EmailDetails;
import nl.wondergem.wondercooks.service.EmailServiceImpl;
import nl.wondergem.wondercooks.service.MenuService;
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
@RequestMapping("${apiPrefix}/menus")
public class MenuController {

    @Autowired
    private Environment env;

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }


    //PostMapping

    @PostMapping("")
    public ResponseEntity<Object> createMenu(@Valid @RequestBody MenuInputDto menuInputDto, BindingResult br) {

        if (br.hasErrors()) {
            String errorMessage = Util.badRequestMessageGenerator(br);
            throw new BadRequestException(errorMessage);
        } else {
            MenuDto menuDto = menuService.saveMenu(menuInputDto);
            URI uri = Util.uriGenerator(env.getProperty("apiPrefix") + "/menus/" + menuDto.id);
            return ResponseEntity.created(uri).body("menu created");
        }
    }


    //GetMapping

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMenu(@PathVariable long id) {
        return ResponseEntity.ok(menuService.getMenu(id));
    }

    //UpdateMapping

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMenu(@PathVariable long id, @Valid @RequestBody MenuInputDto menuInputDto, BindingResult br) {

        if (br.hasErrors()) {
            String errorMessage = Util.badRequestMessageGenerator(br);
            throw new BadRequestException(errorMessage);
        } else {
            menuService.updateMenu(menuInputDto, id);
            return ResponseEntity.ok("menu updated");
        }
    }

    //DeleteMapping

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteMenu(@PathVariable long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }

    //SendEmailMapping
    @PutMapping("/send/{id}")
    public ResponseEntity<Object> sendMenu(@PathVariable long id) {
        menuService.sendMenu(id);
        return ResponseEntity.ok("menu send to customers");
    }

}
