package nl.wondergem.wondercooks.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.wondergem.wondercooks.dto.DeliveryDto;
import nl.wondergem.wondercooks.security.JwtService;
import nl.wondergem.wondercooks.service.DeliveryService;
import nl.wondergem.wondercooks.service.OrderService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DeliveryController.class)
@AutoConfigureMockMvc(addFilters = false)
class DeliveryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryService deliveryService;

    @MockBean
    JwtService jwtService;


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
    void updateDelivery() throws Exception {
        //Arrange

        DeliveryDto deliveryDto = new DeliveryDto();

        deliveryDto.setPaid(false);
        deliveryDto.setETA(LocalDateTime.of(2022, 10, 23, 19, 0, 0));

        //Act and assert
        mockMvc.perform(put("/api/v1/deliveries/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(deliveryDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("delivery updated"));
    }

    @Test
    void sendETA() throws Exception {
        //Act and Assert
        mockMvc.perform(put("/api/v1/deliveries/eta/1")).andExpect(status().isOk()).andExpect(content().string("ETA is sent to customer"));
    }
}