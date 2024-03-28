package com.melek.springcloudcontractmanager.contract.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.melek.springcloudcontractmanager.contract.dto.BranchDto;
import com.melek.springcloudcontractmanager.contract.dto.ContractDto;
import com.melek.springcloudcontractmanager.contract.dto.ProductDto;
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
    public String createContract(@RequestBody ContractDto contractDto) {
        return contractService.createContract(contractDto);
    }

    @PutMapping("/update/{contractId}")
    @ResponseStatus(HttpStatus.OK)
    public String updateContract(@PathVariable Long contractId,
                                 @RequestBody ContractDto contractDto) {
        return contractService.updateContract(contractId, contractDto);
    }

    @PutMapping("/update/consumer/{contractId}")
    @ResponseStatus(HttpStatus.OK)
    public String updateConsumer(
            @PathVariable Long contractId,
            @RequestBody Set<ProductDto> consumer) {
        return contractService.updateConsumer(contractId, consumer);
    }

    @PutMapping("/update/environment/{contractId}")
    @ResponseStatus(HttpStatus.OK)
    public String updateEnvironment(
            @PathVariable Long contractId,
            @RequestBody Set<BranchDto> branchDto) {
        return contractService.updateBranch(contractId, branchDto);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Set<ContractSearchResponse> getContractWithApplicationInfo(
            @RequestParam String project,
            @RequestParam String product,
            @RequestParam String application,
            @RequestParam(required = false) String environment) {
        return contractService.getContractsWithApplicationInfo(project, product, application, environment);
    }

    @DeleteMapping("/delete/{contractId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteContract(@PathVariable Long contractId) {
        return contractService.deleteContract(contractId);
    }


}
