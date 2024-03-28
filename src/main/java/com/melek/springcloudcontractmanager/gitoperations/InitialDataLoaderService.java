package com.melek.springcloudcontractmanager.gitoperations;

import com.melek.springcloudcontractmanager.contract.dto.ContractFile;
import com.melek.springcloudcontractmanager.contract.mapper.ContractMapper;
import com.melek.springcloudcontractmanager.contract.model.Branch;
import com.melek.springcloudcontractmanager.contract.model.Contract;
import com.melek.springcloudcontractmanager.contract.model.Product;
import com.melek.springcloudcontractmanager.contract.repository.ContractRepository;
import com.melek.springcloudcontractmanager.contract.service.BranchService;
import com.melek.springcloudcontractmanager.contract.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


@Service
public class InitialDataLoaderService {

    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;
    private final BranchService branchService;
    private final ProductService productService;

    private final ContractFileConverter contractFileConverter;
    Logger logger = LoggerFactory.getLogger(InitialDataLoaderService.class);

    @Value("${spring.cloud.contract.repository-path}")
    private String repositoryPath;

    public InitialDataLoaderService(ContractRepository contractRepository, ContractMapper contractMapper, BranchService branchService, ProductService productService, ContractFileConverter contractFileConverter) {
        this.contractRepository = contractRepository;
        this.contractMapper = contractMapper;
        this.branchService = branchService;
        this.productService = productService;
        this.contractFileConverter = contractFileConverter;
    }


    public void loadInitialData() {
        logger.info("Contracts in the remote repository are being loaded...");


        List<ContractFile> entities = getExistingContractsFromRepositoryRoot();
        for (ContractFile contractFile : entities) {
            Contract entity = contractMapper.contractFileToContract(contractFile);
            Set<Branch> branches = branchService.saveInitialBranches(entity.getBranch());
            entity.setBranch(branches);
            Set<Product> consumers = productService.saveInitialConsumers(entity.getConsumer());
            entity.setConsumer(consumers);
            Product provider = productService.saveInitialProvider(entity.getProvider());
            entity.setProvider(provider);
            contractRepository.saveAndFlush(entity);
        }

        logger.info("Completed.");
        logger.info("Ready to enjoy!");

    }


    private List<ContractFile> getExistingContractsFromRepositoryRoot() {
        List<ContractFile> contractFiles = new ArrayList<>();

        Path directory = Paths.get(repositoryPath);
        try (Stream<Path> files = Files.walk(directory)) {
            files.forEach(file -> {
                if (file.toFile().getName().endsWith(".yaml")) {
                    ContractFile contractFile = contractFileConverter.convertContractFileToContractJsonObject(file.toFile());
                    contractFiles.add(contractFile);
                }
            });
        } catch (IOException e) {
            logger.error("Error reading JSON file:", e);
        }
        return contractFiles;
    }





}
