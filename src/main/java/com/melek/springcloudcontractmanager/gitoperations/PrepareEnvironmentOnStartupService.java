package com.melek.springcloudcontractmanager.gitoperations;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Component
@Transactional
public class PrepareEnvironmentOnStartupService implements CommandLineRunner {
    private final GitService gitService;
    private final InitialDataLoaderService initialDataLoaderService;


    public PrepareEnvironmentOnStartupService(GitService gitService, InitialDataLoaderService initialDataLoaderService) {
        this.gitService = gitService;
        this.initialDataLoaderService = initialDataLoaderService;
    }

    @Override
    public void run(String... args) throws Exception {
        gitService.deleteContractRepositoryDirectoryIfExist();
        gitService.cloneLongLivedBranches();
        initialDataLoaderService.loadInitialData();
    }


}
