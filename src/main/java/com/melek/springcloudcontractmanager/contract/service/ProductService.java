package com.melek.springcloudcontractmanager.contract.service;

import com.melek.springcloudcontractmanager.contract.dto.ProductDto;
import com.melek.springcloudcontractmanager.contract.mapper.ProductMapper;
import com.melek.springcloudcontractmanager.contract.model.Product;
import com.melek.springcloudcontractmanager.contract.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Set<Product> createAndGetConsumer(Set<ProductDto> consumerDto) {
        Set<Product> consumers = productMapper.productDtoListToProductList(consumerDto);
        return consumers.stream()
                .map(product -> {
                    Optional<Product> existingProduct = productRepository.findByArtifactName(product.getArtifactName());
                    return existingProduct.orElseGet(() -> productRepository.save(product));
                })
                .collect(Collectors.toSet());
    }


    public Product createAndGetProvider(ProductDto providerDto) {
        Product provider = productMapper.productDtoToProduct(providerDto);
        Optional<Product> existingProduct = productRepository.findByArtifactName(provider.getArtifactName());
        return existingProduct.orElseGet(() -> productRepository.save(provider));
    }

    public Set<Product> saveInitialConsumers(Set<Product> consumers) {
        return consumers.stream()
                .map(product -> {
                    Optional<Product> existingProduct = productRepository.findByArtifactName(product.getArtifactName());
                    return existingProduct.orElseGet(() -> productRepository.save(product));
                })
                .collect(Collectors.toSet());
    }

    public Product saveInitialProvider(Product provider) {
        Optional<Product> existingProduct = productRepository.findByArtifactName(provider.getArtifactName());
        return existingProduct.orElseGet(() -> productRepository.save(provider));
    }
}





