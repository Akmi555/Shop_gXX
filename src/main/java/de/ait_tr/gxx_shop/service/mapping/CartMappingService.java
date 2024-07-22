package de.ait_tr.gxx_shop.service.mapping;
/*
@date 21.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.dto.CartDto;
import de.ait_tr.gxx_shop.domain.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMappingService {

    Cart mapDtoToEntity(CartDto dto);

    CartDto mapEntityToDto(Cart entity);
}
