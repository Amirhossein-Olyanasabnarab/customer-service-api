package dk.dev.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class LegalCustomerDto extends CustomerDto {
    @Schema(description = "Company name of customer", example = "AS CO")
    private String companyName;
    @Schema(description = "Industry of customer", example = "IT")
    private String industry;
}
