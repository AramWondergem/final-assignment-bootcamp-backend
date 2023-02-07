package nl.wondergem.wondercooks.integrationtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.wondergem.wondercooks.dto.inputDto.MenuInputDto;
import nl.wondergem.wondercooks.model.*;
import nl.wondergem.wondercooks.repository.CookCustomerRepository;
import nl.wondergem.wondercooks.repository.MenuRepository;
import nl.wondergem.wondercooks.repository.OrderRepository;
import nl.wondergem.wondercooks.repository.UserRepository;
import nl.wondergem.wondercooks.service.MenuService;
import nl.wondergem.wondercooks.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class MenuIntergrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuService menuService;

    @Autowired
    private CookCustomerRepository cookCustomerRepository;

    @Autowired
    private MenuRepository menuRepository;

    private User cook;
    private User customer1;
    private User customer2;

    private Order order1;

    private Order order2;

    private Menu menu;

    private MenuInputDto menuInputDto;





    @BeforeEach
    void setup(){

        if(cookCustomerRepository.count()>0){
            cookCustomerRepository.deleteAll();
        }

        if(orderRepository.count()>0){
            orderRepository.deleteAll();
        }

        if(menuRepository.count()>0){
            menuRepository.deleteAll();
        }




        if(userRepository.count()>0) {
            userRepository.deleteAll();
        }










        Set<Role> rolesCook = new HashSet<>();
        rolesCook.add(Role.COOK);
        rolesCook.add(Role.USER);

        cook = new User();
        cook.setUsername("cook");
        cook.setEmail("cook@test.nl");
        cook.setRoles(rolesCook);
        userRepository.save(cook);


        Set<Role> rolesCustomer = new HashSet<>();
        rolesCustomer.add(Role.USER);

        customer1 = new User();
        customer1.setUsername("customer1");
        customer1.setEmail("customer1@test.nl");
        customer1.setRoles(rolesCustomer);
        userRepository.save(customer1);

        customer2 = new User();
        customer2.setUsername("customer2");
        customer2.setEmail("customer2@test.nl");
        customer2.setRoles(rolesCustomer);
        userRepository.save(customer2);

        order1 = new Order();
        order1.setOrderCustomer(customer1);
        order1.setNumberOfMenus(2);
        order1.setAllergies("pinda");
        order1.setAllergiesExplanation("I will die");
        order1.setStartDeliveryWindow(LocalTime.of(17, 0));
        order1.setEndDeliveryWindow(LocalTime.of(18, 0));
        order1.setStreetAndNumber("dorpsstraat 1");
        order1.setZipcode("1412ZZ");
        order1.setCity("City");
        order1.setComments("hallo");
        order1.setOrderDateAndTime(LocalDateTime.of(2020, 10, 10, 17, 0));

        order2 = new Order();
        order2.setOrderCustomer(customer2);
        order2.setNumberOfMenus(4);
        order2.setAllergies("pinda");
        order2.setAllergiesExplanation("I will die");
        order2.setStartDeliveryWindow(LocalTime.of(17, 0));
        order2.setEndDeliveryWindow(LocalTime.of(18, 0));
        order2.setStreetAndNumber("dorpsstraat 1");
        order2.setZipcode("1412ZZ");
        order2.setCity("City");
        order2.setComments("hallo");
        order2.setOrderDateAndTime(LocalDateTime.of(2020, 10, 10, 17, 0));


        menu = new Menu();
        menu.setCook(cook);
        Set<User> customers = new HashSet<>();
        customers.add(customer1);
        customers.add(customer2);
        menu.setCustomers(customers);
        menu.setTitle("Best menu ever");
        menu.setMain("main");
        menu.setMenuType(MenuType.VEGAN);
        menu.setOrderDeadline(LocalDateTime.of(2024, 10,21,17,0,0));
        menu.setStartDeliveryWindow(LocalDateTime.of(2022, 10,23,17,0,0));
        menu.setEndDeliveryWindow(LocalDateTime.of(2022, 10,23,19,0,0));
        menu.setNumberOfMenus(4);
        menu.setPriceMenu(12.50f);
        menu.setSendToCustomers(true);

        menuRepository.save(menu);

        order1.setMenu(menuRepository.getReferenceById((long)1));
        order2.setMenu(menuRepository.getReferenceById((long)1));

        orderRepository.save(order1);
        orderRepository.save(order2);

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
        menuInputDto.menuType = "MEAT";
        menuInputDto.warmUpInstruction = "warm-up instruction";
        menuInputDto.numberOfMenus = 30;
        menuInputDto.priceMenu = 12.50f;
        menuInputDto.tikkieLink = "www.tikkie.nl";
        menuInputDto.sendToCustomers = false;
        menuInputDto.orderDeadline = LocalDateTime.of(2022, 10,21,17,0,0);
        menuInputDto.startDeliveryWindow = LocalDateTime.of(2022, 10,23,17,0,0);
        menuInputDto.endDeliveryWindow = LocalDateTime.of(2022, 10,23,19,0,0);


    }

    @Test
    void createMenu() throws Exception {
        // arrange

// act and assert
        mockMvc.perform(post("/v1/menus").contentType(MediaType.APPLICATION_JSON).content(asJsonString(menuInputDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location","http://localhost/v1/menus/2"))
                .andExpect(content().string("menu created"));
    }

    @Test
    void getMenu() throws Exception{



// act and assert
        mockMvc.perform(get("/v1/menus/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("customers.length()").value(2))
                .andExpect(jsonPath("cook.id").value(1))
                .andExpect(jsonPath("sendToCustomers").value(true))
                .andExpect(jsonPath("menuType").value("VEGAN"))
                .andExpect(jsonPath("title").value("Best menu ever"))
                .andExpect(jsonPath("startDeliveryWindow").value("2022-10-23T17:00:00"))
                .andExpect(jsonPath("endDeliveryWindow").value("2022-10-23T19:00:00"))
                .andExpect(jsonPath("numberOfMenus").value(4))
                .andExpect(jsonPath("priceMenu").value(12.5))
                .andExpect(jsonPath("orders.length()").value(2))
                .andExpect(jsonPath("orderDeadline").value("2022-10-21T17:00:00"));



    }

    @Test
    void updateMenu() throws Exception {

        //Act and assert
        mockMvc.perform(put("/v1/menus/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(menuInputDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("menu updated"));

        mockMvc.perform(get("/v1/menus/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("customers.length()").value(2))
                .andExpect(jsonPath("cook.id").value(1))
                .andExpect(jsonPath("sendToCustomers").value(false))
                .andExpect(jsonPath("menuType").value("MEAT"))
                .andExpect(jsonPath("title").value("Best Title ever"))
                .andExpect(jsonPath("startDeliveryWindow").value("2022-10-23T17:00:00"))
                .andExpect(jsonPath("endDeliveryWindow").value("2022-10-23T19:00:00"))
                .andExpect(jsonPath("numberOfMenus").value(30))
                .andExpect(jsonPath("priceMenu").value(12.5))
                .andExpect(jsonPath("orders.length()").value(2))
                .andExpect(jsonPath("orderDeadline").value("2022-10-21T17:00:00"));
    }

    @Test
    void deleteMenu() throws Exception {
        //Act and assert
        mockMvc.perform(delete("/v1/menus/1")).andExpect(status().isNoContent());
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

}
