package dk.dev.app.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dk.dev.app.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)

@JsonSubTypes({
        @JsonSubTypes.Type(value = RealCustomer.class, name = "REAL"),
        @JsonSubTypes.Type(value = LegalCustomer.class, name = "LEGAL")
})


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public abstract class Customer {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private CustomerType type;

}
