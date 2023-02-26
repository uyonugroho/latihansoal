package id.co.los.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerRegistrationDto implements Serializable {

    @NotEmpty
    private String id;

    @NotEmpty
    private String ktp;

    @NotEmpty
    private String name;

    private Date dateOfBirth;

    private String address;

    private List<DocumentRegistrationDto> documents;
}
