package id.co.los.listener;

import id.co.los.constant.AppConstants;
import id.co.los.dto.DocumentCheckDto;
import id.co.los.dto.DocumentStatusDto;
import id.co.los.model.Customer;
import id.co.los.model.Document;
import id.co.los.repository.CustomerRepository;
import id.co.los.repository.DocumentRepository;
import id.co.los.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentSubscriber {

    private final CustomerRepository customerRepository;

    private final DocumentRepository documentRepository;

    private final CustomerService customerService;


    @Transactional
    @KafkaListener(topics = AppConstants.TOPIC_DOCUMENT_CHECK, groupId = AppConstants.GROUP_ID)
    public void check(DocumentCheckDto documentCheckDto) {


        String customerId = documentCheckDto.getCustomerId();

        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isEmpty()) {
            return;
        }

        List<DocumentStatusDto> documentStatusDtos = documentCheckDto.getDocuments();


        documentStatusDtos.forEach(documentStatusDto -> {
            Optional<Document> optionalDocument = documentRepository.findByIdAndCustomerId(documentStatusDto.getId(), customerId);
            if (optionalDocument.isPresent()) {
                Document document = optionalDocument.get();
                document.setChecklist(documentStatusDto.getChecklist());
                documentRepository.save(document);
            }
        });


        Long countReject = documentRepository.countReject(customerId);

        Customer customer = optionalCustomer.get();
        customer.setApprove(countReject == 0);

        customerRepository.save(customer);

        customerService.reloadCache(customerId);

    }


}
