package com.melek.springcloudcontractmanager.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.melek.springcloudcontractmanager.contract.dto.*;
import com.melek.springcloudcontractmanager.contract.model.Branch;
import com.melek.springcloudcontractmanager.contract.model.Contract;
import com.melek.springcloudcontractmanager.contract.model.ContractStatus;
import com.melek.springcloudcontractmanager.contract.model.Product;
import com.melek.springcloudcontractmanager.contract.response.ContractSearchResponse;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestDataUtil {
    public static ContractDto getSampleContractDto() {

        return ContractDto.builder()
                .name("Test contract")
                .description("Test contract description")
                .project("testProject")
                .product("testProduct")
                .application("testApplication")
                .ignored(false)
                .directory("test/directory")
                .branch(getSampleBranchDto())
                .provider(getSampleProviderDto())
                .consumer(getSampleConsumerDtoList())
                .request(getSampleRequestResponse("sample-request.json"))
                .response(getSampleRequestResponse("sample-response.json"))
                .status(ContractStatus.ACTIVE)
                .build();
    }


    public static Set<BranchDto> getSampleBranchDto() {
        Set<BranchDto> branchDtoSet = new HashSet<>();
        branchDtoSet.add(BranchDto.builder()
                .name("TEST").build());
        branchDtoSet.add(BranchDto.builder()
                .name("DEV").build());
        branchDtoSet.add(BranchDto.builder()
                .name("UAT").build());
        branchDtoSet.add(BranchDto.builder()
                .name("TEMP").build());
        return branchDtoSet;
    }

    public static ProductDto getSampleProviderDto() {
        return ProductDto.builder()
                .artifactId("sample-provider-artifactId")
                .groupId("sample-provider-groupId").build();
    }

    public static Product getSampleProvider() {
        return Product.builder()
                .id(1L)
                .artifactId("sample-provider-artifactId")
                .groupId("sample-provider-groupId").build();
    }

    public static Set<ProductDto> getSampleConsumerDtoList() {
        Set<ProductDto> consumers = new HashSet<>();
        consumers.add(ProductDto.builder().artifactId("sample-consumer-artifactId-1").groupId("sample-consumer-groupId-1").build());
        consumers.add(ProductDto.builder().artifactId("sample-consumer-artifactId-2").groupId("sample-consumer-groupId-2").build());
        return consumers;
    }

    public static Map<String, Object> getSampleRequestResponse(String name) {
        Map reqRes = new HashMap<>();

        try {

            File file = new File("src/test/resources/test-data/" + name);
            ObjectMapper objectMapper = new ObjectMapper();
            reqRes = objectMapper.readValue(file, reqRes.getClass());
        } catch (IOException e) {
            System.out.println("HATA");
        }

        return reqRes;

    }

    public static Set<ContractSearchResponse> getSampleContractSearchResponse() {
        Set<ContractSearchResponse> searchResponseSet = new HashSet<>();

        ContractSearchResponse response = ContractSearchResponse.builder()
                .id(1L)
                .name("Test contract")
                .description("Test contract description")
                .ignored(false)
                .directory("test/dirrctory")
                .branch(getSampleBranchDto())
                .provider(getSampleProviderDto())
                .consumer(getSampleConsumerDtoList())
                .request(getSampleRequestResponse("sample-request.json"))
                .response(getSampleRequestResponse("sample-response.json"))
                .status(ContractStatus.ACTIVE)
                .build();

        searchResponseSet.add(response);

        return searchResponseSet;
    }

    public static Set<Product> getSampleConsumerList() {
        Set<Product> consumers = new HashSet<>();
        consumers.add(Product.builder().id(1L).artifactId("sample-consumer-artifactId-1").groupId("sample-consumer-groupId-1").build());
        consumers.add(Product.builder().id(2L).artifactId("sample-consumer-artifactId-2").groupId("sample-consumer-groupId-2").build());
        return consumers;
    }

    public static Set<Branch> getSampleBranch() {
        Set<Branch> branchSet = new HashSet<>();
        branchSet.add(Branch.builder()
                .name("TEST").build());
        branchSet.add(Branch.builder()
                .name("DEV").build());
        branchSet.add(Branch.builder()
                .name("UAT").build());
        branchSet.add(Branch.builder()
                .name("TEMP").build());
        return branchSet;
    }

    public static Contract getSampleContract() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();


        return Contract.builder()
                .id(1L)
                .name("Test contract")
                .description("Test contract description")
                .project("testProject")
                .product("testProduct")
                .application("testApplication")
                .ignored(false)
                .directory("test/directory")
                .branch(getSampleBranch())
                .provider(getSampleProvider())
                .consumer(getSampleConsumerList())
                .request(objectMapper.writeValueAsString(getSampleRequestResponse("sample-request.json")))
                .response(objectMapper.writeValueAsString(getSampleRequestResponse("sample-response.json")))
                .status(ContractStatus.ACTIVE)
                .build();
    }

    public static Set<Contract> getSampleContractList() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Set<Contract> contracts = new HashSet<>();

        Contract contract = Contract.builder()
                .id(1L)
                .name("Test contract")
                .description("Test contract description")
                .project("testProject")
                .product("testProduct")
                .application("testApplication")
                .ignored(false)
                .directory("test/directory")
                .branch(getSampleBranch())
                .provider(getSampleProvider())
                .consumer(getSampleConsumerList())
                .request(objectMapper.writeValueAsString(getSampleRequestResponse("sample-request.json")))
                .response(objectMapper.writeValueAsString(getSampleRequestResponse("sample-response.json")))
                .status(ContractStatus.ACTIVE)
                .build();

        Contract contract2 = Contract.builder()
                .id(2L)
                .name("Test contract2")
                .description("Test contract description")
                .project("testProject")
                .product("testProduct")
                .application("testApplication")
                .ignored(false)
                .directory("test/directory")
                .branch(getSampleBranch())
                .provider(getSampleProvider())
                .consumer(getSampleConsumerList())
                .request(objectMapper.writeValueAsString(getSampleRequestResponse("sample-request.json")))
                .response(objectMapper.writeValueAsString(getSampleRequestResponse("sample-response.json")))
                .status(ContractStatus.ACTIVE)
                .build();
        contracts.add(contract);
        contracts.add(contract2);
        return contracts;
    }

    public static ContractFile getSampleContractFile() throws Exception {
        return ContractFile.builder()
                .name("Test contract")
                .description("Test contract description")
                .ignored(false)
                .request(getSampleRequestResponse("sample-request.json"))
                .response(getSampleRequestResponse("sample-response.json"))
                .metadata(getSampleMetadataDto())
                .build();
    }

    public static MetaDataDto getSampleMetadataDto() {
        return MetaDataDto.builder()
                .id(1L)
                .project("testProject")
                .product("testProduct")
                .application("testApplication")
                .directory("sampleDirectory")
                .branch(getSampleBranchDto())
                .provider(getSampleProviderDto())
                .consumer(getSampleConsumerDtoList())
                .status(ContractStatus.ACTIVE)
                .build();
    }
}
