package id.co.los.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class DocumentCheckDto implements Serializable {

    private String customerId;
    private List<DocumentStatusDto> documents;
}
