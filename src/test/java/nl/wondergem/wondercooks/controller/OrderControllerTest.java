package nl.wondergem.wondercooks.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.wondergem.wondercooks.dto.MenuDtoSmall;
import nl.wondergem.wondercooks.dto.OrderDto;
import nl.wondergem.wondercooks.dto.UserDtoSmall;
import nl.wondergem.wondercooks.dto.inputDto.OrderInputDto;
import nl.wondergem.wondercooks.model.MenuType;
import nl.wondergem.wondercooks.security.JwtService;
import nl.wondergem.wondercooks.service.OrderService;
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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    JwtService jwtService;

    private OrderInputDto orderInputDto;

    private OrderDto orderDto;

    private MenuDtoSmall menuDtoSmall;

    private UserDtoSmall userDtoSmall1;


    @BeforeEach
    void setUp() {

        orderInputDto = new OrderInputDto();

        orderInputDto.setOrderCustomerId(2);
        orderInputDto.setNumberOfMenus(2);
        orderInputDto.setAllergies("pinda");
        orderInputDto.setAllergiesExplanation("I will die");
        orderInputDto.setStreetAndNumber("dorpsstraat 1");
        orderInputDto.setStartDeliveryWindow(LocalTime.of(17, 0));
        orderInputDto.setEndDeliveryWindow(LocalTime.of(18, 0));
        orderInputDto.setZipcode("1412ZZ");
        orderInputDto.setCity("City");
        orderInputDto.setComments("hallo");
        orderInputDto.setOrderDateAndTime(LocalDateTime.of(2020, 10, 10, 17, 0));


        menuDtoSmall = new MenuDtoSmall();
        menuDtoSmall.id = 1;
        menuDtoSmall.title = "Best Title ever";
        menuDtoSmall.starter = "starter";
        menuDtoSmall.main = "main";
        menuDtoSmall.side = "side";
        menuDtoSmall.dessert = "dessert";
        menuDtoSmall.menuDescription = "menu description";
        menuDtoSmall.menuPictureURL = "menu url";
        menuDtoSmall.menuType = MenuType.VEGAN;
        menuDtoSmall.warmUpInstruction = "warm-up instruction";
        menuDtoSmall.orderDeadline = LocalDateTime.of(2022, 12, 24, 17, 0);
        menuDtoSmall.startDeliveryWindow = LocalDateTime.of(2022, 12, 25, 17, 0);
        menuDtoSmall.endDeliveryWindow = LocalDateTime.of(2022, 12, 25, 19, 0);
        menuDtoSmall.numberOfMenus = 30;
        menuDtoSmall.priceMenu = 12.50f;
        menuDtoSmall.tikkieLink = "www.tikkie.nl";
        menuDtoSmall.sendToCustomers = false;

        userDtoSmall1 = new UserDtoSmall();
        userDtoSmall1.id = 1;
        userDtoSmall1.username = "orderCustomer";

        orderDto = new OrderDto();
        orderDto.setId(1);
        orderDto.setMenu(menuDtoSmall);
        orderDto.setOrderCustomer(userDtoSmall1);
        orderDto.setNumberOfMenus(2);
        orderDto.setAllergies("pinda");
        orderDto.setAllergiesExplanation("I will die");
        orderDto.setStartDeliveryWindow(LocalTime.of(17, 0));
        orderDto.setEndDeliveryWindow(LocalTime.of(18, 0));
        orderDto.setStreetAndNumber("dorpsstraat 1");
        orderDto.setZipcode("1412ZZ");
        orderDto.setCity("City");
        orderDto.setComments("hallo");
        orderDto.setOrderDateAndTime(LocalDateTime.of(2020, 10, 10, 17, 0));
        orderDto.setDelivery(null);
    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper =
                    new ObjectMapper().registerModule(new JavaTimeModule())
                            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createOrder() throws Exception {
        //Arrange

        orderInputDto.setMenuId(1);

        given(orderService.saveOrder(orderInputDto)).willReturn(orderDto);


        //Act and assert
        mockMvc.perform(post("/v1/orders").contentType(MediaType.APPLICATION_JSON).content(asJsonString(orderInputDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/v1/orders/1"))
                .andExpect(content().string("order created"));

    }


    @Test
    void getOrder() throws Exception {
        //Arrange
        given(orderService.getOrder(1)).willReturn(orderDto);

        //act and assert
        mockMvc.perform(get("/v1/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("menu.id").value(1))
                .andExpect(jsonPath("orderCustomer.id").value(1))
                .andExpect(jsonPath("orderDateAndTime").value(LocalDateTime.of(2020, 10, 10, 17, 0).format(DateTimeFormatter.ISO_DATE_TIME)));
    }

    @Test
    void updateOrder() throws Exception {
        //Act and assert
        mockMvc.perform(put("/v1/orders/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(orderInputDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("order updated"));
    }

    @Test
    void deleteOrder() throws Exception {
        //Act and assert
        mockMvc.perform(delete("/v1/orders/1")).andExpect(status().isNoContent());
    }

    @Test
    void acceptOrder() throws Exception {
        //Act and assert
        mockMvc.perform(put("/v1/orders/accept/1")).andExpect(status().isOk())
                .andExpect(content().string("order accepted"));
    }

    @Test
    void declineOrder() throws Exception {
        //Act and assert
        mockMvc.perform(put("/v1/orders/decline/1")).andExpect(status().isOk())
                .andExpect(content().string("order declined"));
    }
}