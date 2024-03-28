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
import com.melek.springcloudcontractmanager.contract.response.ContractSearchResponse;
import com.melek.springcloudcontractmanager.gitoperations.ContractFileOperations;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
    private static final String CREATED = "created";
    private static final String UPDATED = "updated";

    public ContractService(ProductService productService, IdCreationService idCreationService, BranchService branchService, ContractMapper contractMapper, ContractRepository contractRepository, RequestValidationService requestValidationService, ContractFileOperations contractFileOperations) {
        this.productService = productService;
        this.idCreationService = idCreationService;
        this.branchService = branchService;
        this.contractMapper = contractMapper;
        this.contractRepository = contractRepository;
        this.requestValidationService = requestValidationService;
        this.contractFileOperations = contractFileOperations;
    }

    /**
     * Creates a new contract.
     *
     * @param contractDto The contract data transfer object
     * @return A message indicating the contract creation status
     */
    public String createContract(ContractDto contractDto) {
        Contract contractEntity = prepareContract(contractDto);
        ContractFile contractFile = contractMapper.contractDtoToContractFile(contractDto);
        contractFile.getMetadata().setId(contractEntity.getId());
        contractRepository.saveAndFlush(contractEntity);
        contractFileOperations.addOrUpdateContractOnLocalRepoAndPushToGit(contractFile, CREATED);
        return String.format("Contract Created: %s", contractDto.getName());
    }


    /**
     * Prepares a contract entity for persistence.
     *
     * @param contractDto The contract data transfer object
     * @return The prepared contract entity
     */
    private Contract prepareContract(ContractDto contractDto) {

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


    /**
     * Deletes a contract by ID.
     *
     * @param contractId The contract ID
     * @return A message indicating the contract deletion status
     * @throws ContractNotFoundException If the contract is not found
     */
    public String deleteContract(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));
        ContractFile contractFile = contractMapper.contractToContractFile(contract);
        contractFileOperations.deleteContractFromLocalRepoAndPushToGit(contractFile);
        contractRepository.deleteById(contractId);
        return String.format("Contract deleted: %s", contract.getName());
    }

    /**
     * Updates the consumers of a contract.
     *
     * @param contractId The contract ID
     * @param consumers  The new consumers
     * @return A message indicating the consumer update status
     * @throws ContractNotFoundException If the contract is not found
     */
    public String updateConsumer(Long contractId, Set<ProductDto> consumers) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));
        contract.setConsumer(productService.createAndGetConsumer(consumers));
        ContractFile contractFile = contractMapper.contractToContractFile(contract);
        contractRepository.saveAndFlush(contract);
        contractFileOperations.addOrUpdateContractOnLocalRepoAndPushToGit(contractFile, UPDATED);

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Consumers updated.\nNew consumer list:\n");
        consumers.forEach(consumer -> messageBuilder.append(String.format("%s:%s%n", consumer.getGroupId(), consumer.getArtifactId())));
        return messageBuilder.toString();
    }

    /**
     * Updates the branches of a contract.
     *
     * @param contractId   The contract ID
     * @param newBranchDto The new branches
     * @return A message indicating the branch update status
     * @throws ContractNotFoundException If the contract is not found
     */
    public String updateBranch(Long contractId, Set<BranchDto> newBranchDto) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));
        ContractFile contractFile = contractMapper.contractToContractFile(contract);
        Set<BranchDto> existingBranches = contractFile.getMetadata().getBranch();
        Set<BranchDto> branchesToBeDeleted = new HashSet<>();

        for (BranchDto existingBranch : existingBranches) {
            boolean existsInNewBranches = newBranchDto.contains(existingBranch);
            if (!existsInNewBranches) {
                branchesToBeDeleted.add(existingBranch);
            }
        }

        Set<Branch> newBranches = branchService.createAndGetBranches(newBranchDto);
        contract.setBranch(newBranches);
        contractRepository.saveAndFlush(contract);
        if (!branchesToBeDeleted.isEmpty()) {
            contractFile.getMetadata().setBranch(branchesToBeDeleted);
            contractFileOperations.deleteContractFromLocalRepoAndPushToGit(contractFile);
        }
        contractFile.getMetadata().setBranch(newBranchDto);
        contractFileOperations.addOrUpdateContractOnLocalRepoAndPushToGit(contractFile, UPDATED);

        //Todo: branch deletion notify to consumer

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Environments updated\nNew environment list:\n");
        newBranchDto.forEach(branch -> messageBuilder.append(String.format("%s%n", branch.getName())));
        return messageBuilder.toString();
    }

    /**
     * Updates an existing contract with the given data.
     *
     * @param contractId  The ID of the contract to update
     * @param contractDto The contract data transfer object containing the updated data
     * @return A message indicating the contract update status
     * @throws ContractNotFoundException If the contract is not found
     */
    public String updateContract(Long contractId, ContractDto contractDto) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));
        Contract newContract = contractMapper.contractDtoToContract(contractDto);
        newContract.setId(contract.getId());
        newContract.setConsumer(contract.getConsumer());
        newContract.setProvider(contract.getProvider());
        newContract.setBranch(contract.getBranch());
        ContractFile contractFile = contractMapper.contractToContractFile(newContract);
        contractFileOperations.addOrUpdateContractOnLocalRepoAndPushToGit(contractFile, UPDATED);
        contractRepository.saveAndFlush(newContract);
        return String.format("Contract updated: %s", contractDto.getName());
    }

    public Set<ContractSearchResponse> getContractsWithApplicationInfo(String project, String product, String application, String environment) {
        Set<Contract> contracts;
        if (environment != null) {
            contracts = contractRepository.findByProjectAndProductAndApplicationAndBranch_Name(project, product, application, environment);
        } else {
            contracts = contractRepository.findByProjectAndProductAndApplication(project, product, application);
        }
        if (contracts.isEmpty()) {
            throw new ContractNotFoundException(project, product, application);
        }
        return contractMapper.contractListToContractSearchResponse(contracts);
    }

    public Long getMaxContractId() {
        return contractRepository.findMaxId();
    }
}
