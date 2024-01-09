package com.melek.springcloudcontractmanager.contract.service;

import com.melek.springcloudcontractmanager.contract.dto.BranchDto;
import com.melek.springcloudcontractmanager.contract.dto.ContractDto;
import com.melek.springcloudcontractmanager.contract.dto.ProductDto;
import com.melek.springcloudcontractmanager.contract.mapper.ContractMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RequestValidationService {

    private final ContractMapper contractMapper;

    public RequestValidationService(ContractMapper contractMapper) {
        this.contractMapper = contractMapper;
    }


    public Set<ProductDto> validateRequestAndGetConsumerDto(ContractDto contractDto) {
        return contractMapper.contractDtoToConsumer(contractDto);
    }

    public ProductDto validateRequestAndGetProviderDto(ContractDto contractDto) {
        return contractMapper.contractDtoToProvider(contractDto);
    }

    public Set<BranchDto> validateRequestAndGetBranchDto(ContractDto contractDto) {
        return contractMapper.contractDtoToBranchDto(contractDto);
    }
}
