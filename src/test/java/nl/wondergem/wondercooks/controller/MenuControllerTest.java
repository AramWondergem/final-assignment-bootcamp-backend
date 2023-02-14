package nl.wondergem.wondercooks.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.wondergem.wondercooks.dto.MenuDto;
import nl.wondergem.wondercooks.dto.UserDtoSmall;
import nl.wondergem.wondercooks.dto.inputDto.MenuInputDto;
import nl.wondergem.wondercooks.model.MenuType;
import nl.wondergem.wondercooks.security.JwtService;
import nl.wondergem.wondercooks.service.MenuService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MenuController.class)
@AutoConfigureMockMvc(addFilters = false)
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuService menuService;

    @MockBean
    JwtService jwtService;


    MenuInputDto menuInputDto;

    MenuDto menuDto;

    UserDtoSmall userDtoSmall;
    UserDtoSmall userDtoSmall1;
    UserDtoSmall userDtoSmall2;

    @BeforeEach
    void setUp() {

        userDtoSmall = new UserDtoSmall();
        userDtoSmall.id = 1;
        userDtoSmall.username = "cook";

        userDtoSmall1 = new UserDtoSmall();
        userDtoSmall1.id = 2;
        userDtoSmall1.username = "customer";

        userDtoSmall2 = new UserDtoSmall();
        userDtoSmall2.id = 3;
        userDtoSmall2.username = "customer";

        Set<UserDtoSmall> customers = new HashSet<>();
        customers.add(userDtoSmall1);
        customers.add(userDtoSmall2);

        menuInputDto = new MenuInputDto();
        menuInputDto.cookId = 1;

        menuInputDto.customersId = new int[]{2, 3};

        menuInputDto.title = "Best Title ever";
        menuInputDto.starter = "starter";
        menuInputDto.main = "main";
        menuInputDto.side = "side";
        menuInputDto.dessert = "dessert";
        menuInputDto.menuDescription = "menu description";
        menuInputDto.menuPictureURL = "menu url";
        menuInputDto.menuType = "VEGAN";
        menuInputDto.warmUpInstruction = "warm-up instruction";
        menuInputDto.numberOfMenus = 30;
        menuInputDto.priceMenu = 12.50f;
        menuInputDto.tikkieLink = "www.tikkie.nl";
        menuInputDto.sendToCustomers = false;

        menuDto = new MenuDto();
        menuDto.id = 1;
        menuDto.cook = userDtoSmall;
        menuDto.customers = customers;
        menuDto.title = "Best Title ever";
        menuDto.starter = "starter";
        menuDto.main = "main";
        menuDto.side = "side";
        menuDto.dessert = "dessert";
        menuDto.menuDescription = "menu description";
        menuDto.menuPictureURL = "menu url";
        menuDto.menuType = MenuType.VEGAN;
        menuDto.warmUpInstruction = "warm-up instruction";
        menuDto.numberOfMenus = 30;
        menuDto.priceMenu = 12.50f;
        menuDto.tikkieLink = "www.tikkie.nl";
        menuDto.sendToCustomers = false;
    }



    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createMenu() throws Exception {
        //Arrange
        given(menuService.saveMenu(menuInputDto)).willReturn(menuDto);

        //Act and assert
        mockMvc.perform(post("/api/v1/menus").contentType(MediaType.APPLICATION_JSON).content(asJsonString(menuInputDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/menus/1"))
                .andExpect(content().string("menu created"));
    }


    @Test
    void getMenu() throws Exception {
        //Arrange
        given(menuService.getMenu(1)).willReturn(menuDto);

        //act and assert
        mockMvc.perform(get("/api/v1/menus/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("customers.length()").value(2))
                .andExpect(jsonPath("cook.id").value(1))
                .andExpect(jsonPath("sendToCustomers").value(false));
    }

    @Test
    void updateMenu() throws Exception {
        //Act and assert
        mockMvc.perform(put("/api/v1/menus/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(menuInputDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("menu updated"));
    }

    @Test
    void deleteMenu() throws Exception {
        //Act and assert
        mockMvc.perform(delete("/api/v1/menus/1")).andExpect(status().isNoContent());
    }

    @Test
    void sendMenu() throws Exception {
        //Act and Assert
        mockMvc.perform(put("/api/v1/menus/send/1")).andExpect(status().isOk()).andExpect(content().string("menu send to customers"));
    }

    @Test
    void sendAcceptMails() throws Exception {
        //Act and Assert
        mockMvc.perform(put("/api/v1/menus/send/accepted/1")).andExpect(status().isOk()).andExpect(content().string("mails send to accepted orders"));
    }

    @Test
    void sendDeclineMails() throws Exception {
        //Act and Assert
        mockMvc.perform(put("/api/v1/menus/send/declined/1")).andExpect(status().isOk()).andExpect(content().string("mails send to declined orders"));
    }

    @Test
    void sendTikkie() throws Exception {
        //Act and Assert
        mockMvc.perform(put("/api/v1/menus/send/tikkie/1")).andExpect(status().isOk()).andExpect(content().string("tikkielink update is sent"));
    }
}