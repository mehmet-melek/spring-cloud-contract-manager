package com.melek.springcloudcontractmanager.contract.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IdCreationServiceTest {

    @InjectMocks
    private IdCreationService idCreationService;

    @Mock
    private ContractService contractService;


    @Test
    @DisplayName("IdCreationService - Create a new id by increasing the max id value by one")
    void testGenerateUniqueId() {
        when(contractService.getMaxContractId()).thenReturn(2L);
        Long actualId = idCreationService.generateUniqueId();
        Assertions.assertEquals(3L, actualId);
    }
}