package id.co.los.listener;

import id.co.los.constant.AppConstants;
import id.co.los.dto.CustomerRegistrationDto;
import id.co.los.dto.CustomerStatusDto;
import id.co.los.dto.DocumentRegistrationDto;
import id.co.los.dto.DocumentStatusDto;
import id.co.los.model.Customer;
import id.co.los.model.Document;
import id.co.los.repository.CustomerRepository;
import id.co.los.repository.DocumentRepository;
import id.co.los.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerSubscriber {

    private final CustomerRepository customerRepository;

    private final DocumentRepository documentRepository;

    private final CustomerService customerService;



    @Transactional
    @KafkaListener(topics = AppConstants.TOPIC_CUSTOMER_REGISTRATION, groupId = AppConstants.GROUP_ID)
    public void registration(CustomerRegistrationDto customerRegistrationDto) {
        Customer customer = Customer.builder()
                .id(customerRegistrationDto.getId())
                .ktp(customerRegistrationDto.getKtp())
                .name(customerRegistrationDto.getName())
                .dateOfBirth(customerRegistrationDto.getDateOfBirth())
                .address(customerRegistrationDto.getAddress())
                .approve(null)
                .build();
        customer = customerRepository.save(customer);
        List<DocumentRegistrationDto> documentRegistrationDtos = customerRegistrationDto.getDocuments();
        Customer finalCustomer = customer;


        documentRepository.deleteAllByCustomerId(finalCustomer.getId());

        documentRegistrationDtos.forEach(documentRegistrationDto -> {

            Document document = Document.builder()
                    .customer(finalCustomer)
                    .name(documentRegistrationDto.getName())
                    .checklist(null)
                    .build();

            documentRepository.save(document);

        });
        customerService.reloadCache(finalCustomer.getId());
    }




}
