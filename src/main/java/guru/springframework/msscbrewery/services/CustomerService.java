package guru.springframework.msscbrewery.services;

import guru.springframework.msscbrewery.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {
    CustomerDto getCustomerById(UUID id);

    CustomerDto save(CustomerDto customerDto);

    void update(UUID id, CustomerDto customerDto);

    void delete(UUID id);
}
