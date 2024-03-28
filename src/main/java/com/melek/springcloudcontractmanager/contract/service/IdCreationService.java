package com.melek.springcloudcontractmanager.contract.service;

import com.melek.springcloudcontractmanager.contract.repository.ContractRepository;
import org.springframework.stereotype.Service;

@Service
public class IdCreationService {

    private final ContractRepository contractRepository;

    public IdCreationService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Long generateUniqueId() {
        Long maxId = contractRepository.findMaxId();
        return (maxId == null) ? 1 : maxId + 1;
    }

}
