package com.melek.springcloudcontractmanager.contract.exception;

public class ContractNotFoundException extends RuntimeException {
    public ContractNotFoundException(String project, String product, String application) {
        super(String.format("No contract was found for %s:%s:%s in the any environment!", project, product, application));
    }

    public ContractNotFoundException(String project, String product, String application, String environment) {
        super(String.format("No contract was found for %s:%s:%s in the %s environment!", project, product, application, environment));
    }

    public ContractNotFoundException(String artifactId) {
        super(String.format("No contract was found for %s", artifactId));
    }

    public ContractNotFoundException(Long contractId) {
        super(String.format("Contract was not found. Contract Id: %s", contractId));
    }
}
