package com.melek.springcloudcontractmanager.contract.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melek.springcloudcontractmanager.contract.dto.BranchDto;
import com.melek.springcloudcontractmanager.contract.dto.ContractDto;
import com.melek.springcloudcontractmanager.contract.dto.ContractFile;
import com.melek.springcloudcontractmanager.contract.dto.ProductDto;
import com.melek.springcloudcontractmanager.contract.model.Contract;
import com.melek.springcloudcontractmanager.contract.response.ContractSearchResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Component
public interface ContractMapper {

    ContractMapper INSTANCE = Mappers.getMapper(ContractMapper.class);

    final Logger logger = LoggerFactory.getLogger(ContractMapper.class);

    @Mapping(target = "request", expression = "java(jsonStringToMap(contract.getRequest()))")
    @Mapping(target = "response", expression = "java(jsonStringToMap(contract.getResponse()))")
    Set<ContractSearchResponse> contractListToContractSearchResponse(Set<Contract> contract);


    @Mapping(target = "request", expression = "java(mapToJsonString(contractFile.getRequest()))")
    @Mapping(target = "response", expression = "java(mapToJsonString(contractFile.getResponse()))")
    @Mapping(target = "directory", source = "metadata.directory")
    @Mapping(target = "status", source = "metadata.status")
    @Mapping(target = "consumer", source = "metadata.consumer")
    @Mapping(target = "provider", source = "metadata.provider")
    @Mapping(target = "branch", source = "metadata.branch")
    @Mapping(target = "id", source = "metadata.id")
    @Mapping(target = "project", source = "metadata.project")
    @Mapping(target = "product", source = "metadata.product")
    @Mapping(target = "application", source = "metadata.application")
    Contract contractFileToContract(ContractFile contractFile);

    @Mapping(source = "directory", target = "metadata.directory")
    @Mapping(source = "status", target = "metadata.status")
    @Mapping(source = "consumer", target = "metadata.consumer")
    @Mapping(source = "provider", target = "metadata.provider")
    @Mapping(source = "branch", target = "metadata.branch")
    @Mapping(source = "project", target = "metadata.project")
    @Mapping(source = "product", target = "metadata.product")
    @Mapping(source = "application", target = "metadata.application")
    ContractFile contractDtoToContractFile(ContractDto contractDto);

    @Mapping(source = "directory", target = "metadata.directory")
    @Mapping(source = "status", target = "metadata.status")
    @Mapping(source = "consumer", target = "metadata.consumer")
    @Mapping(source = "provider", target = "metadata.provider")
    @Mapping(source = "project", target = "metadata.project")
    @Mapping(source = "product", target = "metadata.product")
    @Mapping(source = "application", target = "metadata.application")
    @Mapping(source = "branch", target = "metadata.branch")
    @Mapping(source = "id", target = "metadata.id")
    @Mapping(target = "request", expression = "java(jsonStringToMap(contract.getRequest()))")
    @Mapping(target = "response", expression = "java(jsonStringToMap(contract.getResponse()))")
    ContractFile contractToContractFile(Contract contract);

    @Mapping(target = "request", expression = "java(mapToJsonString(contractDto.getRequest()))")
    @Mapping(target = "response", expression = "java(mapToJsonString(contractDto.getResponse()))")
    Contract contractDtoToContract(ContractDto contractDto);


    @Mapping(source = "provider", target = ".")
    ProductDto contractDtoToProvider(ContractDto contractDto);

    default Set<ProductDto> contractDtoToConsumer(ContractDto contractDto) {
        if (contractDto.getConsumer() == null) {
            return Collections.emptySet();
        }
        return contractDto.getConsumer().stream()
                .map(this::mapProductDto)
                .collect(Collectors.toSet());
    }

    default Set<BranchDto> contractDtoToBranchDto(ContractDto contractDto) {
        if (contractDto.getBranch() == null) {
            return Collections.emptySet();
        }
        return contractDto.getBranch().stream()
                .map(this::mapBranchDto)
                .collect(Collectors.toSet());
    }

    ProductDto mapProductDto(ProductDto productDto);

    BranchDto mapBranchDto(BranchDto branchDto);

    default Map<String, Object> jsonStringToMap(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (json != null && !json.isEmpty()) {
                return objectMapper.readValue(json, Map.class);
            } else {
                return Collections.emptyMap();
            }
        } catch (IOException e) {
            throw new RuntimeException("JSON parsing error", e);
        }
    }


    default String mapToJsonString(Map<String, Object> map) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (map != null && !map.isEmpty()) {
                return objectMapper.writeValueAsString(map);
            } /*else {
                return "{}"; // Boş bir JSON nesnesi döndür
            }*/
        } catch (IOException e) {
            logger.error("JSON writing error:", e);
        }
        return "{}"; // Boş bir JSON nesnesi döndür        }

    }


}


