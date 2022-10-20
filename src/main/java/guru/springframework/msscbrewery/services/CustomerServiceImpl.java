package guru.springframework.msscbrewery.services;

import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDto getCustomerById(UUID id) {
        return CustomerDto.builder()
                .name("John Doe")
                .id(UUID.randomUUID()).build();
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        return CustomerDto.builder()
                .name(customerDto.getName())
                .id(UUID.randomUUID())
                .build();
    }

    @Override
    public void update(UUID id, CustomerDto customerDto) {
        //todo
    }

    @Override
    public void delete(UUID id) {
        //todo
    }
}
