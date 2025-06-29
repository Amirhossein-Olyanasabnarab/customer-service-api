package dk.dev.app.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dk.dev.app.enums.CustomerType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")


@Schema(description = "Customer entity representing a customer")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class CustomerDto {
    @Schema(description = "Unique identifier for the customer", example = "1")
    private Long id;
    @Schema(description = "First name & Last name of customer", example = "John Doe")
    private String fullName;
    @Schema(description = "Phone number of the customer", example = "+1234567")
    private String phoneNumber;
    @Schema(description = "Email of customer", example = "john@gmail.com")
    private String email;
    private CustomerType type;
}
