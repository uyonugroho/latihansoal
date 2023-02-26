package id.co.los.service;

import id.co.los.constant.AppConstants;
import id.co.los.dto.CustomerRegistrationDto;
import id.co.los.dto.CustomerStatusDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void registration(CustomerRegistrationDto customerRegistrationDto){
        kafkaTemplate.send(AppConstants.TOPIC_CUSTOMER_REGISTRATION, customerRegistrationDto);
    }

    @Cacheable(cacheNames = AppConstants.CACHE_CUSTOMER_STATUS, key = "#customerId")
    public CustomerStatusDto status(String customerId){
        return null;
    }

}
