package com.melek.springcloudcontractmanager.contract.controller;

import com.melek.springcloudcontractmanager.contract.dto.BranchDto;
import com.melek.springcloudcontractmanager.contract.dto.ContractDto;
import com.melek.springcloudcontractmanager.contract.dto.ProductDto;
import com.melek.springcloudcontractmanager.contract.response.ContractCreationResponse;
import com.melek.springcloudcontractmanager.contract.response.ContractSearchResponse;
import com.melek.springcloudcontractmanager.contract.service.ContractService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/contract")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ContractCreationResponse createContract(@RequestBody ContractDto contractDto) {
        return contractService.createContract(contractDto);
    }

    @PutMapping("/update/{contractId}")
    @ResponseStatus(HttpStatus.OK)
    public ContractCreationResponse updateContract(@PathVariable Long contractId, @RequestBody ContractDto contractDto) {
        return contractService.createContract(contractDto);
    }

    @PutMapping("/update/consumer/{contractId}")
    @ResponseStatus(HttpStatus.OK)
    public ContractCreationResponse updateConsumer(@PathVariable Long contractId, @RequestBody Set<ProductDto> consumer) {
        return contractService.updateConsumer(contractId,consumer);
    }

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public Set<ContractSearchResponse> getContractsWithArtifactId(@RequestParam String artifactId) {
        return contractService.getContractsWithArtifactId(artifactId);
    }

    @GetMapping("/find/env")
    @ResponseStatus(HttpStatus.OK)
    public Set<ContractSearchResponse> getContractsWithArtifactIdAndEnv(@RequestParam String artifactId, @RequestParam String env) {
        return contractService.getContractsWithArtifactIdAndEnv(artifactId, env);
    }
    @DeleteMapping("/delete/{contractId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteContract(@PathVariable Long contractId) {
        return contractService.deleteContract(contractId);
    }



}
