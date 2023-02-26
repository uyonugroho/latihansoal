package id.co.los.service;

import id.co.los.constant.AppConstants;
import id.co.los.dto.DocumentCheckDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void check(DocumentCheckDto documentCheckDto){
        kafkaTemplate.send(AppConstants.TOPIC_DOCUMENT_CHECK, documentCheckDto);
    }
}
