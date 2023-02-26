package id.co.los.repository;

import id.co.los.model.Customer;
import id.co.los.model.Document;
import id.co.los.service.CustomerService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    Set<Document> findByCustomer(Customer customer);

    Optional<Document> findByIdAndCustomerId(Long id, String customerId);


    @Query(nativeQuery = true, value = "SELECT count(1) FROM document WHERE (checklist = false or checklist is null) AND customer_id = :customerId")
    Long countReject(@Param("customerId") String customerId);


    void deleteAllByCustomerId(String customerId);



}
