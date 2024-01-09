package com.melek.springcloudcontractmanager.contract.exception;

public class ContractNotFoundException extends RuntimeException {
    public ContractNotFoundException(String artifactId, String env) {
        super(String.format("No contract was found for %s in the %s environment!", artifactId, env));
    }

    public ContractNotFoundException(String artifactId) {
        super(String.format("No contract was found for %s in the any environment!", artifactId));
    }

    public ContractNotFoundException(Long contractId) {
        super(String.format("Contract %s was not found!", contractId));
    }
}
