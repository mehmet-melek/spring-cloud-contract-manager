package com.melek.springcloudcontractmanager.contract.service;

import com.melek.springcloudcontractmanager.contract.dto.BranchDto;
import com.melek.springcloudcontractmanager.contract.dto.ContractDto;
import com.melek.springcloudcontractmanager.contract.dto.ProductDto;
import com.melek.springcloudcontractmanager.contract.mapper.ContractMapper;
import com.melek.springcloudcontractmanager.contract.model.Branch;
import com.melek.springcloudcontractmanager.contract.model.Contract;
import com.melek.springcloudcontractmanager.contract.repository.ContractRepository;
import com.melek.springcloudcontractmanager.contract.response.ContractSearchResponse;
import com.melek.springcloudcontractmanager.gitoperations.ContractFileOperations;
import com.melek.springcloudcontractmanager.util.TestDataUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContractServiceTest {

    @InjectMocks
    private ContractService contractService;

    @Mock
    private ProductService productService;
    @Mock
    private IdCreationService idCreationService;
    @Mock
    private BranchService branchService;
    @Spy
    private ContractMapper contractMapper = Mappers.getMapper(ContractMapper.class);
    @Mock
    private ContractRepository contractRepository;
    @Spy
    private RequestValidationService requestValidationService = new RequestValidationService(contractMapper);
    @Mock
    private ContractFileOperations contractFileOperations;


    @Test
    @DisplayName("Create contract if contract dto is valid")
    void testCreateContract() throws Exception {

        //Arrange
        ContractDto contractDto = TestDataUtil.getSampleContractDto();
        when(productService.createAndGetProvider(refEq(contractDto.getProvider()))).thenReturn(TestDataUtil.getSampleProvider());
        when(productService.createAndGetConsumer(refEq(contractDto.getConsumer()))).thenReturn(TestDataUtil.getSampleConsumerList());
        when(branchService.createAndGetBranches(refEq(contractDto.getBranch()))).thenReturn(TestDataUtil.getSampleBranch());
        when(idCreationService.generateUniqueId()).thenReturn(1L);
        //Act
        String message = contractService.createContract(contractDto);
        //Assert
        assertEquals(message, String.format("Contract Created: %s", contractDto.getName()));
        //verify
        verify(contractRepository, times(1)).saveAndFlush(any());
        verify(contractFileOperations, times(1)).addOrUpdateContractOnLocalRepoAndPushToGit(any(), eq("created"));

    }

    @Test
    @DisplayName("ContractService - Delete contract with contract id")
    void testDeleteContract() throws Exception {
        Contract contract = TestDataUtil.getSampleContract();
        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));
        String result = contractService.deleteContract(1L);
        assertEquals(String.format("Contract deleted: %s", contract.getName()), result);
        verify(contractRepository, times(1)).deleteById(1L);
        verify(contractFileOperations, times(1)).deleteContractFromLocalRepoAndPushToGit(any());
    }

    @Test
    @DisplayName("ContractService - Update consumer with contract id and consumer list")
    void testUpdateConsumer() throws Exception {
        Set<ProductDto> newConsumers = TestDataUtil.getSampleConsumerDtoList();
        Contract contract = TestDataUtil.getSampleContract();
        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));
        when(productService.createAndGetConsumer(newConsumers)).thenReturn(contract.getConsumer());
        String result = contractService.updateConsumer(1L, newConsumers);
        Assertions.assertTrue(result.contains(contract.getConsumer().iterator().next().getArtifactId()));
        verify(contractRepository, times(1)).saveAndFlush(any());
        verify(contractFileOperations, times(1)).addOrUpdateContractOnLocalRepoAndPushToGit(any(), eq("updated"));
    }

    @Test
    @DisplayName("ContractService - Update environments when new environments do not include existing environments should save only new environments ")
    void testUpdateEnvironment_shouldSaveOnlyNewEnvironment() throws Exception {
        Set<BranchDto> newBranchesDto = TestDataUtil.getSampleBranchDto();
        newBranchesDto.iterator().next().setName("NEW");
        Set<Branch> newBranches = TestDataUtil.getSampleBranch();
        newBranches.iterator().next().setName("NEW");
        Contract contract = TestDataUtil.getSampleContract();
        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));
        when(branchService.createAndGetBranches(refEq(newBranchesDto))).thenReturn(newBranches);

        String result = contractService.updateBranch(1L, newBranchesDto);

        Assertions.assertTrue(result.contains("NEW"));
        Assertions.assertFalse(result.contains("test") & result.contains("dev"));
        verify(contractRepository, times(1)).saveAndFlush(any());
        verify(contractFileOperations, times(1)).deleteContractFromLocalRepoAndPushToGit(any());
        verify(contractFileOperations, times(1)).addOrUpdateContractOnLocalRepoAndPushToGit(any(), eq("updated"));

    }

    @Test
    @DisplayName("ContractService - Update contract with contract id contract info")
    void testUpdateContract_shouldReturnUpdated() throws Exception {
        ContractDto contractDto = TestDataUtil.getSampleContractDto();
        Contract contract = TestDataUtil.getSampleContract();
        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));

        String response = contractService.updateContract(1L, contractDto);

        Assertions.assertEquals(String.format("Contract updated: %s", contract.getName()), response);
        verify(contractFileOperations, times(1)).addOrUpdateContractOnLocalRepoAndPushToGit(any(), eq("updated"));
        verify(contractRepository, times(1)).saveAndFlush(any());
    }

    @Test
    @DisplayName("ContractService - Search contracts with application info and environment should return contracts only on given environment")
    void testGetContractWithApplicationIfoWithEnvironmentInfo() throws Exception {
        String project = "testProject";
        String product = "testProduct";
        String application = "testApplication";
        String environment = "test";
        Set<Contract> contract = TestDataUtil.getSampleContractList();
        when(contractRepository.findByProjectAndProductAndApplicationAndBranch_Name(project, product, application, environment)).thenReturn(contract);
        Set<ContractSearchResponse> response = contractService.getContractsWithApplicationInfo(project, product, application, environment);
        Assertions.assertFalse(response.isEmpty());
    }

    @Test
    @DisplayName("ContractService - Search contracts with only application info should return contracts for all environment")
    void testGetContractWithApplicationIfoWithoutEnvironmentInfo() throws Exception {
        String project = "testProject";
        String product = "testProduct";
        String application = "testApplication";
        String environment = null;
        Set<Contract> contract = TestDataUtil.getSampleContractList();
        when(contractRepository.findByProjectAndProductAndApplication(project, product, application)).thenReturn(contract);
        Set<ContractSearchResponse> response = contractService.getContractsWithApplicationInfo(project, product, application, environment);
        Assertions.assertFalse(response.isEmpty());
    }


    @Test
    @DisplayName("ContractService - Get max contract id from db")
    void testGetMaxContractId() throws Exception {
        when(contractRepository.findMaxId()).thenReturn(11L);
        Long actualId = contractService.getMaxContractId();
        Assertions.assertEquals(11, actualId);
    }
}