package com.melek.springcloudcontractmanager.gitoperations;

import com.melek.springcloudcontractmanager.contract.mapper.ContractMapper;
import com.melek.springcloudcontractmanager.contract.repository.ContractRepository;
import com.melek.springcloudcontractmanager.contract.service.BranchService;
import com.melek.springcloudcontractmanager.contract.service.ProductService;
import com.melek.springcloudcontractmanager.util.ContractFileConverter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InitialDataLoaderServiceTest {

    @InjectMocks
    private InitialDataLoaderService initialDataLoaderService;
    @Mock
    private ContractRepository contractRepository;
    @Spy
    private ContractMapper contractMapper = Mappers.getMapper(ContractMapper.class);
    @Mock
    private BranchService branchService;
    @Mock
    private ProductService productService;
    @Spy
    private ContractFileConverter contractFileConverter;
    @Mock
    private Environment environment;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(initialDataLoaderService, "repositoryPath", "src/test/resources/testContractRepository");
    }

    @Test
    @DisplayName("InitialDataLoaderService - Load all data test")
    void testLoadInitialDate() throws Exception {

        when(branchService.saveInitialBranches(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(productService.saveInitialConsumers(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(productService.saveInitialProvider(any())).thenAnswer(invocation -> invocation.getArgument(0));
        initialDataLoaderService.loadInitialData();

        Mockito.verify(contractRepository, times(3)).saveAndFlush(any());

    }

}