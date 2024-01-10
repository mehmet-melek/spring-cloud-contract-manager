package com.melek.springcloudcontractmanager.contract.service;

import com.melek.springcloudcontractmanager.contract.dto.BranchDto;
import com.melek.springcloudcontractmanager.contract.dto.ContractDto;
import com.melek.springcloudcontractmanager.contract.dto.ContractFile;
import com.melek.springcloudcontractmanager.contract.dto.ProductDto;
import com.melek.springcloudcontractmanager.contract.exception.ContractNotFoundException;
import com.melek.springcloudcontractmanager.contract.mapper.ContractMapper;
import com.melek.springcloudcontractmanager.contract.model.Branch;
import com.melek.springcloudcontractmanager.contract.model.Contract;
import com.melek.springcloudcontractmanager.contract.model.Product;
import com.melek.springcloudcontractmanager.contract.repository.ContractRepository;
import com.melek.springcloudcontractmanager.contract.response.ContractCreationResponse;
import com.melek.springcloudcontractmanager.contract.response.ContractSearchResponse;
import com.melek.springcloudcontractmanager.gitoperations.ContractFileOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ContractService {

    private final ProductService productService;
    private final IdCreationService idCreationService;
    private final BranchService branchService;
    private final ContractMapper contractMapper;
    private final ContractRepository contractRepository;
    private final RequestValidationService requestValidationService;
    private final ContractFileOperations contractFileOperations;

    public ContractService(ProductService productService, IdCreationService idCreationService, BranchService branchService, ContractMapper contractMapper, ContractRepository contractRepository, RequestValidationService requestValidationService, ContractFileOperations contractFileOperations) {
        this.productService = productService;
        this.idCreationService = idCreationService;
        this.branchService = branchService;
        this.contractMapper = contractMapper;
        this.contractRepository = contractRepository;
        this.requestValidationService = requestValidationService;
        this.contractFileOperations = contractFileOperations;
    }


    public ContractCreationResponse createContract(ContractDto contractDto) {
        Contract contractEntity = perepareContract(contractDto);
        ContractFile contractFile = contractMapper.contractDtoToContractFile(contractDto);
        contractFile.getMetadata().setId(contractEntity.getId());
        return saveContractAndGetResponse(contractEntity, contractFile);
    }


    private Contract perepareContract(ContractDto contractDto) {

        Set<ProductDto> consumerDto = requestValidationService.validateRequestAndGetConsumerDto(contractDto);
        ProductDto providerDto = requestValidationService.validateRequestAndGetProviderDto(contractDto);
        Set<BranchDto> branchDto = requestValidationService.validateRequestAndGetBranchDto(contractDto);


        Product provider = productService.createAndGetProvider(providerDto);
        Set<Product> consumers = productService.createAndGetConsumer(consumerDto);
        Set<Branch> branches = branchService.createAndGetBranches(branchDto);

        Contract contract = contractMapper.contractDtoToContract(contractDto);
        contract.setProvider(provider);
        contract.setConsumer(consumers);
        contract.setBranch(branches);
        contract.setId(idCreationService.generateUniqueId());
        return contract;

    }

    private ContractCreationResponse saveContractAndGetResponse(Contract contractEntity, ContractFile contractFile) {
        contractRepository.save(contractEntity);
        contractFileOperations.addContractToLocalRepoAndPushToGit(contractFile);
        return contractMapper.contractToContractCreationResponse(contractEntity);
    }


    public Set<ContractSearchResponse> getContractsWithArtifactId(String artifactId) {
        Set<Contract> contracts = contractRepository.findByProviderArtifactName(artifactId);
        if (contracts.isEmpty()) {
            throw new ContractNotFoundException(artifactId);
        }
        return contractMapper.contractListToContractSearchResponse(contracts);
    }

    public Set<ContractSearchResponse> getContractsWithArtifactIdAndEnv(String artifactId, String env) {
        Set<Contract> contracts = contractRepository.findByProviderArtifactNameAndBranchName(artifactId, env);
        if (contracts.isEmpty()) {
            throw new ContractNotFoundException(artifactId, env);
        }
        return contractMapper.contractListToContractSearchResponse(contracts);
    }

    public String deleteContract(Long contractId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new ContractNotFoundException(contractId));
        ContractFile contractFile = contractMapper.contractToContractFile(contract);
        contractFileOperations.deleteContractFromLocalRepoAndPushToGit(contractFile);
        contractRepository.deleteById(contractId);
        return "Deleted";
    }

    public ContractCreationResponse updateConsumer(Long contractId, Set<ProductDto> consumer) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new ContractNotFoundException(contractId));
        contract.setConsumer(productService.createAndGetConsumer(consumer));
        ContractFile contractFile = contractMapper.contractToContractFile(contract);
        saveContractAndGetResponse(contract,contractFile);
        return null;
    }
}
