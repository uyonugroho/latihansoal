package id.co.los.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DocumentStatusDto implements Serializable {
    private Long id;
    private String name;
    private Boolean checklist;
}
