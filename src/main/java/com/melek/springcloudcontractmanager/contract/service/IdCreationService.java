package com.melek.springcloudcontractmanager.contract.service;

import com.melek.springcloudcontractmanager.contract.repository.ContractRepository;
import org.springframework.stereotype.Service;

@Service
public class IdCreationService {

    private final ContractService contractService;

    public IdCreationService(ContractService contractService) {
        this.contractService = contractService;
    }

    public Long generateUniqueId() {
        Long maxId = contractService.getMaxContractId();
        return (maxId == null) ? 1 : maxId + 1;
    }

}
