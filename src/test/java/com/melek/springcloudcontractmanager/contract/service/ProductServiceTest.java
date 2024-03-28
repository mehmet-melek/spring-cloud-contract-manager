package com.melek.springcloudcontractmanager.contract.service;

import com.melek.springcloudcontractmanager.contract.dto.ProductDto;
import com.melek.springcloudcontractmanager.contract.mapper.ProductMapper;
import com.melek.springcloudcontractmanager.contract.model.Product;
import com.melek.springcloudcontractmanager.contract.repository.ProductRepository;
import com.melek.springcloudcontractmanager.util.TestDataUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Spy
    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Test
    @DisplayName("ProductService - Create consumer with given products dto and return all products")
    void testCreateAndGetConsumer() throws Exception {
        Set<ProductDto> productDtos = TestDataUtil.getSampleConsumerDtoList();
        when(productRepository.findByArtifactId(productDtos.stream().findFirst().get().getArtifactId())).thenReturn(TestDataUtil.getSampleConsumerList().stream().findFirst());
        Set<Product> actualProducts = productService.createAndGetConsumer(productDtos);
        assertEquals(2, actualProducts.size());
        verify(productMapper, times(1)).productDtoListToProductList(anySet());
        verify(productRepository, times(1)).save(any(Product.class));

    }

    @Test
    @DisplayName("ProductService - Create provider with provider info and return saved provide")
    void testCreateAndGetProvider() throws Exception {
        ProductDto providerDto = TestDataUtil.getSampleProviderDto();
        Product product = TestDataUtil.getSampleProvider();
        when(productRepository.findByArtifactId(providerDto.getArtifactId())).thenReturn(Optional.of(product));
        Product actualProduct = productService.createAndGetProvider(providerDto);
        assertEquals(1, actualProduct.getId());
        verify(productMapper, times(1)).productDtoToProduct(refEq(providerDto));
    }

    @Test
    @DisplayName("ProductService - Save initial consumer when app start")
    void testSaveInitialConsumers() throws Exception {
        Set<Product> existConsumers = TestDataUtil.getSampleConsumerList();
        for (Product consumer : existConsumers) {
            when(productRepository.findByArtifactId(consumer.getArtifactId())).thenReturn(Optional.of(consumer));
        }
        Set<Product> actualConsumers = productService.saveInitialConsumers(existConsumers);

        Assertions.assertEquals(existConsumers.size(), actualConsumers.size());
        Assertions.assertTrue(actualConsumers.containsAll(existConsumers));
    }

    @Test
    @DisplayName("ProductService - Save initial provider when app start")
    void testSaveInitialProvider() throws Exception {
        Product productForSave = TestDataUtil.getSampleProvider();
        when(productRepository.findByArtifactId(productForSave.getArtifactId())).thenReturn(Optional.of(productForSave));
        Product actualProvider = productService.saveInitialProvider(productForSave);
        Assertions.assertEquals(productForSave, actualProvider);
    }
}