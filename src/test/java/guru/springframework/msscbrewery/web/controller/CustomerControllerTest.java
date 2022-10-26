package guru.springframework.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    CustomerDto johnDoe;

    @Before
    public void setUp() {
        johnDoe = CustomerDto.builder().id(UUID.randomUUID())
                .name("John Doe")
                .build();
    }

    @Test
    public void getCustomer() throws Exception {
        given(customerService.getCustomerById(any(UUID.class))).willReturn(johnDoe);

        mockMvc.perform(get("/api/v1/customer/" + johnDoe.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(johnDoe.getId().toString())))
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    public void handlePost() throws Exception {
        //given
        CustomerDto customerDto = johnDoe;
        customerDto.setId(null);
        CustomerDto savedDto = CustomerDto.builder().id(UUID.randomUUID()).name("New Customer").build();
        String customerJson = objectMapper.writeValueAsString(customerDto);

        given(customerService.save(any())).willReturn(savedDto);

        mockMvc.perform(post("/api/v1/customer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isCreated());

    }

    @Test
    public void handleUpdate() throws Exception {
        //given
        CustomerDto customerDto = johnDoe;
        customerDto.setId(null);
        String custDtoJson = objectMapper.writeValueAsString(customerDto);
        UUID uuid = UUID.randomUUID();

        //when
        mockMvc.perform(put("/api/v1/customer/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(custDtoJson))
                .andExpect(status().isNoContent());

        then(customerService).should().update(eq(uuid), any());
    }

    @Test
    public void deleteById() throws Exception {
        //given
        UUID uuid = UUID.randomUUID();
        //when
        mockMvc.perform(delete("/api/v1/customer/"+ uuid))
                .andExpect(status().isOk());

        then(customerService).should().delete(uuid);
    }
}