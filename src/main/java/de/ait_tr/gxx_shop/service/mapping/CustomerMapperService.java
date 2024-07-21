package de.ait_tr.gxx_shop.service.mapping;
/*
@date 21.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.dto.CustomerDto;
import de.ait_tr.gxx_shop.domain.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CartMappingService.class)
public interface CustomerMapperService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Customer mapDtoToEntity(CustomerDto dto);

    CustomerDto mapEntityToDto(Customer entity);
}
