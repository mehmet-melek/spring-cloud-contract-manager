package com.melek.springcloudcontractmanager.contract.mapper;

import com.melek.springcloudcontractmanager.contract.dto.ProductDto;
import com.melek.springcloudcontractmanager.contract.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.Set;

@Mapper(componentModel = "spring")
@Component
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    Product productDtoToProduct(ProductDto productDto);
    Set<Product> productDtoListToProductList(Set<ProductDto> productDtoSet);

}
