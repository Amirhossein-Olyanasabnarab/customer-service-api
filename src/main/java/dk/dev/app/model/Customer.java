package dk.dev.app.model;

import dk.dev.app.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


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
