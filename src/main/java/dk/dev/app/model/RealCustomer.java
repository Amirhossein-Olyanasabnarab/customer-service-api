package dk.dev.app.model;

import dk.dev.app.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class RealCustomer extends Customer {

    private String nationality;
    private String age;

    public RealCustomer(){
        this.setType(CustomerType.REAL);
    }
}
