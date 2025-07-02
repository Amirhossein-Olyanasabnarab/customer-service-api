package dk.dev.app.model;

import dk.dev.app.enums.CustomerType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@Entity
@Table(name = "legal_customer")
@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue("LEGAL")
public class LegalCustomer extends Customer {

    @Column(name = "company_name")
    private String companyName;
    private String industry;

    public LegalCustomer(){
        this.setType(CustomerType.LEGAL);
    }
}
