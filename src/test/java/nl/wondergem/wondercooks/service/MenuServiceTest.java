package nl.wondergem.wondercooks.service;

import nl.wondergem.wondercooks.dto.MenuDto;
import nl.wondergem.wondercooks.dto.UserDtoSmall;
import nl.wondergem.wondercooks.dto.inputDto.MenuInputDto;
import nl.wondergem.wondercooks.mapper.MenuMapper;
import nl.wondergem.wondercooks.model.EmailDetails;
import nl.wondergem.wondercooks.model.Menu;
import nl.wondergem.wondercooks.model.MenuType;
import nl.wondergem.wondercooks.model.User;
import nl.wondergem.wondercooks.repository.MenuRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    MenuRepository menuRepository;
    @Mock
    MenuMapper menuMapper;
    @Mock
    EmailServiceImpl emailService;
    @InjectMocks
    MenuService menuService;

    MenuInputDto menuInputDto;
    Menu menu;
    MenuDto menuDto;
    UserDtoSmall userDtoSmall;
    UserDtoSmall userDtoSmall1;
    UserDtoSmall userDtoSmall2;

    User user;
    User user1;
    User user2;


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

        user = new User();
        user.setId(1);
        user.setUsername("cook");

        user1 = new User();
        user1.setId(2);
        user1.setUsername("customer");

        user2 = new User();
        user2.setId(3);
        user2.setUsername("customer");

        Set<User> customersEntity = new HashSet<>();
        customersEntity.add(user1);
        customersEntity.add(user2);

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
        menuInputDto.orderDeadline = LocalDateTime.of(2022, 12, 24, 17, 0);
        menuInputDto.startDeliveryWindow = LocalDateTime.of(2022, 12, 25, 17, 0);
        menuInputDto.endDeliveryWindow = LocalDateTime.of(2022, 12, 25, 19, 0);
        menuInputDto.numberOfMenus = 30;
        menuInputDto.priceMenu = 12.50f;
        menuInputDto.tikkieLink = "www.tikkie.nl";
        menuInputDto.sendToCustomers = false;

        menu = new Menu();
        menu.setId(1);
        menu.setCook(user);
        menu.setCustomers(customersEntity);
        menu.setTitle("Best Title ever");
        menu.setStarter("starter");
        menu.setMain("main");
        menu.setSide("side");
        menu.setDessert("dessert");
        menu.setMenuDescription("menu description");
        menu.setMenuPictureURL("menu url");
        menu.setMenuType(MenuType.VEGAN);
        menu.setWarmUpInstruction("warm-up instruction");
        menu.setOrderDeadline(LocalDateTime.of(2022, 12, 24, 17, 0));
        menu.setStartDeliveryWindow(LocalDateTime.of(2022, 12, 25, 17, 0));
        menu.setEndDeliveryWindow(LocalDateTime.of(2022, 12, 25, 19, 0));
        menu.setNumberOfMenus(30);
        menu.setPriceMenu(12.50f);
        menu.setTikkieLink("www.tikkie.nl");
        menu.setSendToCustomers(false);

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
        menuDto.orderDeadline = LocalDateTime.of(2022, 12, 24, 17, 0);
        menuDto.startDeliveryWindow = LocalDateTime.of(2022, 12, 25, 17, 0);
        menuDto.endDeliveryWindow = LocalDateTime.of(2022, 12, 25, 19, 0);
        menuDto.numberOfMenus = 30;
        menuDto.priceMenu = 12.50f;
        menuDto.tikkieLink = "www.tikkie.nl";
        menuDto.sendToCustomers = false;
    }

    @Test
    void saveMenu() {
        //Arrange
        when(menuMapper.menuInputDtoToMenu(any(MenuInputDto.class), any(Menu.class))).thenReturn(menu);
        when(menuRepository.save(menu)).thenReturn(menu);
        when(menuMapper.menuToMenuDto(menu)).thenReturn(menuDto);

        // Act
        MenuDto result = menuService.saveMenu(menuInputDto);

        //Assert
        verify(menuMapper, times(1)).menuInputDtoToMenu(any(MenuInputDto.class), any(Menu.class));
        verify(menuRepository, times(1)).save(menu);
        verify(menuMapper, times(1)).menuToMenuDto(menu);
        assertEquals(1, result.id);
        assertEquals("Best Title ever", result.title);
        assertEquals(MenuType.VEGAN, result.menuType);
    }

    @Test
    void getMenu() {
        //Arrange
        when(menuMapper.menuToMenuDto(menu)).thenReturn(menuDto);
        when(menuRepository.getReferenceById((long) 1)).thenReturn(menu);

        //Act
        MenuDto result = menuService.getMenu(1);

        //Assert
        verify(menuMapper, times(1)).menuToMenuDto(menu);
        verify(menuRepository, times(1)).getReferenceById((long) 1);
        assertEquals(1, result.id);
        assertEquals("Best Title ever", result.title);
        assertEquals(MenuType.VEGAN, result.menuType);
    }

    @Test
    void updateMenu() {
        //Arrange
        when(menuRepository.getReferenceById((long) 1)).thenReturn(menu);
        when(menuMapper.menuInputDtoToMenu(any(MenuInputDto.class), any(Menu.class))).thenReturn(menu);
        when(menuRepository.save(menu)).thenReturn(menu);
        //Act
        menuService.updateMenu(menuInputDto, 1);
        //Assert
        verify(menuRepository, times(1)).getReferenceById((long) 1);
        verify(menuMapper, times(1)).menuInputDtoToMenu(any(MenuInputDto.class), any(Menu.class));
        verify(menuRepository, times(1)).save(menu);
    }

    @Test
    void deleteMenu() {
        //Act
        menuService.deleteMenu(1);

        //Assert
        verify(menuRepository).deleteById((long)1);


    }

    @Test
    void sendMenu() {
        //Arrange
        when(menuRepository.getReferenceById((long) 1)).thenReturn(menu);


        //Act
        menuService.sendMenu(1);

        //Assert
        verify(menuRepository, times(1)).getReferenceById((long) 1);
        verify(emailService, times(2)).sendSimpleMail(any(EmailDetails.class));
        verify(menuRepository).save(any(Menu.class));
    }
}