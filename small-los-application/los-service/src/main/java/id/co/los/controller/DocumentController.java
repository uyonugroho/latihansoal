package id.co.los.controller;


import id.co.los.dto.CustomerRegistrationDto;
import id.co.los.dto.DocumentCheckDto;
import id.co.los.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/check")
    public ResponseEntity<Object> check(@Valid @RequestBody DocumentCheckDto documentCheckDto) {

        documentService.check(documentCheckDto);
        return ResponseEntity.ok(documentCheckDto);
    }
}
