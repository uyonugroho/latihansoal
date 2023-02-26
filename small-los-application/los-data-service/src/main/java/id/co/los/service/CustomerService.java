package id.co.los.service;

import id.co.los.constant.AppConstants;
import id.co.los.dto.CustomerStatusDto;
import id.co.los.dto.DocumentStatusDto;
import id.co.los.model.Customer;
import id.co.los.model.Document;
import id.co.los.repository.CustomerRepository;
import id.co.los.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final CacheManager cacheManager;
    private final CustomerRepository customerRepository;

    private final DocumentRepository documentRepository;

    public void reloadCache(String customerId) {

        final Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isPresent()) {

            Customer customer = optionalCustomer.get();
            CustomerStatusDto customerStatusDto = CustomerStatusDto.builder()
                    .id(customer.getId())
                    .ktp(customer.getKtp())
                    .name(customer.getName())
                    .dateOfBirth(customer.getDateOfBirth())
                    .address(customer.getAddress())
                    .approve(customer.getApprove())
                    .documents(new ArrayList<>())
                    .build();


            Set<Document> documents = documentRepository.findByCustomer(customer);

            documents.forEach(document -> {

                DocumentStatusDto documentStatusDto = DocumentStatusDto.builder()
                        .id(document.getId())
                        .name(document.getName())
                        .checklist(document.getChecklist())
                        .build();

                customerStatusDto.getDocuments().add(documentStatusDto);

            });

            cacheManager.getCache(AppConstants.CACHE_CUSTOMER_STATUS)
                    .put(customer.getId(),customerStatusDto);

        }
    }



}
