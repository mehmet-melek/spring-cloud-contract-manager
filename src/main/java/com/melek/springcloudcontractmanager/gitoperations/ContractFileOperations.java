package com.melek.springcloudcontractmanager.gitoperations;

import com.melek.springcloudcontractmanager.contract.dto.ContractFile;
import com.melek.springcloudcontractmanager.contract.exception.ContractNotFoundException;
import com.melek.springcloudcontractmanager.util.ContractFileConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.NoSuchFileException;
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

    @Value("${spring.cloud.contract.temp-path}")
    private String tempPath;
    private final String userDirectory = System.getProperty("user.dir");

    public void addOrUpdateContractOnLocalRepoAndPushToGit(ContractFile contractFile, String processType) {
        contractFile.getMetadata().getBranch().forEach(branch -> {
            String environment = branch.getName();
            String contractDirectoryAndName = getContractDirectoryAndName(contractFile, environment);
            logger.info("The contract named '{}' will be {} in the '{}' environment.", contractFile.getName(), processType, environment);
            contractFileConverter.createContractYamlFileFromContractFileObject(contractFile, contractDirectoryAndName);
            gitService.commitAndPushChanges(userDirectory + File.separator + getBranchDirectory(environment), String.format("Contract %s: %s", processType, contractFile.getName()));
        });

    }

    public void deleteContractFromLocalRepoAndPushToGit(ContractFile contractFile) {
        contractFile.getMetadata().getBranch().forEach(branchDto -> {
            String environment = branchDto.getName();
            String jsonPath = getContractDirectoryAndName(contractFile, environment);
            logger.info("The contract named '{}' will be deleted from '{}' environment.", contractFile.getName(), environment);
            File file = new File(jsonPath);
            if (file.delete()) {
                logger.info("Contract file deleted.Name: {}", contractFile.getName());
            } else {
                throw new ContractNotFoundException(contractFile.getName());
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
            default -> tempPath;
        };
    }

    private Path createContractPath(ContractFile contractFile, String localPath) {
        return Paths.get(userDirectory, localPath, basePath,
                contractFile.getMetadata().getProvider().getGroupId(),
                contractFile.getMetadata().getProvider().getArtifactId(),
                productVersion,
                contractFile.getMetadata().getDirectory(),
                contractFile.getName() + ".yaml");
    }


}
