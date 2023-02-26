package id.co.los.controller;


import id.co.los.dto.CustomerRegistrationDto;
import id.co.los.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping("/registration")
    public ResponseEntity<Object> registration(@Valid @RequestBody CustomerRegistrationDto customerRegistrationDto) {
        customerService.registration(customerRegistrationDto);
        return ResponseEntity.ok(customerRegistrationDto);
    }

    @GetMapping("/status/{customerId}")
    public ResponseEntity<Object> status(@PathVariable String customerId) {
        return ResponseEntity.ok(customerService.status(customerId));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
