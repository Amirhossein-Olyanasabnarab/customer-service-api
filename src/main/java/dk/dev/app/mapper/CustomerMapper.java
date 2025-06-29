package dk.dev.app.mapper;

import dk.dev.app.dto.CustomerDto;
import dk.dev.app.dto.LegalCustomerDto;
import dk.dev.app.dto.RealCustomerDto;
import dk.dev.app.model.Customer;
import dk.dev.app.model.LegalCustomer;
import dk.dev.app.model.RealCustomer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    RealCustomer toEntity(RealCustomerDto dto);
    RealCustomerDto toDto(RealCustomer entity);

    LegalCustomer toEntity(LegalCustomerDto dto);
    LegalCustomerDto toDto(LegalCustomer entity);

    default Customer toEntity(Object dto){
        if(dto instanceof RealCustomerDto){
            return toEntity((RealCustomerDto) dto);
        }else if(dto instanceof LegalCustomerDto){
            return toEntity((LegalCustomerDto) dto);
        }
        throw new IllegalArgumentException("Cannot convert " + dto + " to RealCustomerDto");
    }

    default CustomerDto toDto(Customer entity){
        if(entity instanceof RealCustomer){
            return toDto((RealCustomer) entity);
        }else if(entity instanceof LegalCustomer){
            return toDto((LegalCustomer) entity);
        }
        throw new IllegalArgumentException("Cannot convert " + entity + " to RealCustomerDto");
    }
}
