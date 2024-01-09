package com.melek.springcloudcontractmanager.gitoperations;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

@Service
public class GitService {

    @Value("${spring.cloud.contract.remote-repository-url}")
    private String remoteContractRepositoryUrl;

    @Value("${spring.cloud.contract.local-dev-path}")
    private String devLocalPath;

    @Value("${spring.cloud.contract.local-test-path}")
    private String testLocalPath;

    @Value("${spring.cloud.contract.local-uat-path}")
    private String uatLocalPath;

    @Value("${spring.cloud.contract.dev-branch-name}")
    private String devBranchName;

    @Value("${spring.cloud.contract.test-branch-name}")
    private String testBranchName;

    @Value("${spring.cloud.contract.uat-branch-name}")
    private String uatBranchName;


    @Value("${git.user}")
    private String gitUser;

    @Value("${git.token}")
    private String gitToken;

    private final Logger logger = LoggerFactory.getLogger(GitService.class);


    private void cloneBranch(String branchName, String directoryPath) {
        try {
            logger.info("Cloning branch {} into {}", branchName, directoryPath);
            Git.cloneRepository()
                    .setURI(remoteContractRepositoryUrl)
                    .setDirectory(new File(directoryPath))
                    .setBranch(branchName)
                    .call();
            Git.cloneRepository();
            logger.info("Completed cloning of branch {}", branchName);
        } catch (GitAPIException e) {
            logger.error("Exception occurred while cloning branch {} ", branchName);
        }
    }


    public void commitAndPushChanges(String directoryPath, String commitMessage) {
        try (Git git = Git.open(new File(directoryPath))) {
            git.add().addFilepattern(".").setUpdate(true).call();
            git.add().addFilepattern(".").call();

            git.commit()
                    .setMessage(commitMessage)
                    .call();
            logger.info("Commit completed: {}", commitMessage);

            UsernamePasswordCredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(gitUser, gitToken);
            git.push().setCredentialsProvider(credentialsProvider).call();
            logger.info("Push completed");

        } catch (IOException | GitAPIException e) {
            logger.error("Error during commit/push: {}", e.getMessage());
        }
    }


    public void cloneLongLivedBranches() {
        deleteContractRepositoryDirectoryIfExist();
        cloneBranch(devBranchName, devLocalPath);
        cloneBranch(testBranchName, testLocalPath);
        cloneBranch(uatBranchName, uatLocalPath);
    }


    public void deleteContractRepositoryDirectoryIfExist() {
        try {
            Path pathToBeDeleted = Paths.get("contractRepository");
            deleteDirectoryRecursively(pathToBeDeleted);
            logger.info("Existing directory deleted before clone process");
        } catch (IOException e) {
            logger.info("An error occurred while deleting the folder: {}", e.getMessage());
        }
    }


    private void deleteDirectoryRecursively(Path path) throws IOException {
        if (Files.exists(path)) {
            try (Stream<Path> walk = Files.walk(path)) {
                walk.sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
        }
    }

}