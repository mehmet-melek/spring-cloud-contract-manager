package com.melek.springcloudcontractmanager.gitoperations;

import com.melek.springcloudcontractmanager.contract.dto.ContractFile;
import com.melek.springcloudcontractmanager.contract.exception.ContractNotFoundException;
import com.melek.springcloudcontractmanager.contract.mapper.ContractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ContractFileOperations {

    private final GitService gitService;

    private final Logger logger = LoggerFactory.getLogger(ContractFileOperations.class);

    private final ContractFileConverter contractFileConverter;

    public ContractFileOperations(GitService gitService, ContractFileConverter contractFileConverter) {
        this.gitService = gitService;
        this.contractFileConverter = contractFileConverter;
    }


    @Value("${spring.cloud.contract.local-dev-path}")
    private String devLocalPath;

    @Value("${spring.cloud.contract.local-test-path}")
    private String testLocalPath;

    @Value("${spring.cloud.contract.local-uat-path}")
    private String uatLocalPath;

    @Value("${spring.cloud.contract.product-version}")
    private String productVersion;

    @Value("${spring.cloud.contract.base-path}")
    private String basePath;

    private final String userDirectory = System.getProperty("user.dir");


    public void addContractToLocalRepoAndPushToGit(ContractFile contractFile) {

        contractFile.getMetadata().getBranch().forEach(branch -> {
            String environment = branch.getName();
            String contractDirectoryAndName = getContractDirectoryAndName(contractFile, environment);
            logger.info("The contract named '{}' will be created in the '{}' environment.", contractFile.getName(), environment);
            contractFileConverter.createContractYamlFileFromContractFileEntity(contractFile, contractDirectoryAndName);
            gitService.commitAndPushChanges(userDirectory + File.separator + getBranchDirectory(environment), "Contract crated: " + contractFile.getName());
        });

    }

    public void deleteContractFromLocalRepoAndPushToGit(ContractFile contractFile) {
        contractFile.getMetadata().getBranch().forEach(branchDto -> {
            String environment = branchDto.getName();
            String jsonPath = getContractDirectoryAndName(contractFile, environment);
            logger.info("The contract named '{}' will be deleted from '{}' environment.", contractFile.getName(), environment);
            File file = new File(jsonPath);
            if (file.delete()) {
                logger.info("Contract file deleted.");
            } else {
                throw new ContractNotFoundException(1L);
            }
            gitService.commitAndPushChanges(userDirectory + File.separator + getBranchDirectory(environment), "Contract deleted: " + contractFile.getName());
        });
    }


    private String getContractDirectoryAndName(ContractFile contractFile, String branch) {
        String branchDirectory = getBranchDirectory(branch);
        Path contractPath = createContractPath(contractFile, branchDirectory);
        return contractPath.toString();
    }

    private String getBranchDirectory(String branch) {
        return switch (branch) {
            case "DEV" -> devLocalPath;
            case "TEST" -> testLocalPath;
            case "UAT" -> uatLocalPath;
            default -> "temp";
        };
    }

    private Path createContractPath(ContractFile contractFile, String localPath) {
        return Paths.get(userDirectory, localPath, basePath,
                contractFile.getMetadata().getProvider().getGroupName(),
                contractFile.getMetadata().getProvider().getArtifactName(),
                productVersion,
                contractFile.getMetadata().getDirectory(),
                contractFile.getName() + ".yaml");
    }


}
