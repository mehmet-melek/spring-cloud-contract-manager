package com.melek.springcloudcontractmanager.util;

import com.melek.springcloudcontractmanager.contract.dto.ProductDto;
import com.melek.springcloudcontractmanager.contract.dto.BranchDto;
import com.melek.springcloudcontractmanager.contract.dto.ContractDto;
import com.melek.springcloudcontractmanager.contract.request.ContractRequest;

import java.util.HashSet;
import java.util.Set;

public class TestDataUtil {
    public static ContractRequest getSampleContractRequest() {

        ContractRequest contractRequest = ContractRequest.builder()
                .contract(getSampleContractDto())
                .branches(getSampleBranchDto())
                .provider(getSampleProvider())
                .consumers(getSampleConsumers())
                .build();
        return contractRequest;
    }


    public static ContractDto getSampleContractDto() {
        return ContractDto.builder()
                .name("test contract")
                .description("test contract description").build();
    }



    public static Set<BranchDto> getSampleBranchDto() {
        Set<BranchDto> branchDtoSet = new HashSet<>();
        branchDtoSet.add(BranchDto.builder()
                .name("test").build());
        branchDtoSet.add(BranchDto.builder()
                .name("test").build());
        return branchDtoSet;
    }

    public static ProductDto getSampleProvider() {
        return ProductDto.builder()
                .artifactName("sample-provider-artifact-name")
                .groupName("sample-provider-group-name").build();
    }

    public static Set<ProductDto> getSampleConsumers() {
        Set<ProductDto> consumers = new HashSet<>();
        consumers.add(ProductDto.builder().artifactName("sample-consumer-artifact-name-1").groupName("sample-consumer-group-name-1").build());
        consumers.add(ProductDto.builder().artifactName("sample-consumer-artifact-name-2").groupName("sample-consumer-group-name-2").build());
        return consumers;
    }
}
