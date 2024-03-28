package com.melek.springcloudcontractmanager.gitoperations;

import com.melek.springcloudcontractmanager.contract.dto.ContractFile;
import com.melek.springcloudcontractmanager.contract.exception.ContractNotFoundException;
import com.melek.springcloudcontractmanager.util.ContractFileConverter;
import com.melek.springcloudcontractmanager.util.TestDataUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ContractFileOperationsTest {

    @InjectMocks
    private ContractFileOperations contractFileOperations;
    @Mock
    private GitService gitService;
    @Mock
    private ContractFileConverter contractFileConverter;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(contractFileOperations, "tempPath", "target/temp");
        ReflectionTestUtils.setField(contractFileOperations, "testLocalPath", "target/test");
        ReflectionTestUtils.setField(contractFileOperations, "devLocalPath", "target/dev");
        ReflectionTestUtils.setField(contractFileOperations, "uatLocalPath", "target/uat");
        ReflectionTestUtils.setField(contractFileOperations, "productVersion", "0.0.1-SNAPSHOT/contracts");
        ReflectionTestUtils.setField(contractFileOperations, "basePath", "META-INF");
    }

    @Test
    @DisplayName("ContractFileOperations - Add contract to local repo and call git push")
    void testAddOrUpdateContractOnLocalRepoAndPushToGit() throws Exception {
        ContractFile contractFile = TestDataUtil.getSampleContractFile();
        contractFileOperations.addOrUpdateContractOnLocalRepoAndPushToGit(contractFile, "updated");
        verify(contractFileConverter, times(1)).createContractYamlFileFromContractFileObject(
                eq(contractFile),
                endsWith("target/test/META-INF/sample-provider-groupId/sample-provider-artifactId/0.0.1-SNAPSHOT/contracts/sampleDirectory/Test contract.yaml"));
        verify(contractFileConverter, times(1)).createContractYamlFileFromContractFileObject(
                eq(contractFile),
                endsWith("target/dev/META-INF/sample-provider-groupId/sample-provider-artifactId/0.0.1-SNAPSHOT/contracts/sampleDirectory/Test contract.yaml"));
        verify(contractFileConverter, times(1)).createContractYamlFileFromContractFileObject(
                eq(contractFile),
                endsWith("target/uat/META-INF/sample-provider-groupId/sample-provider-artifactId/0.0.1-SNAPSHOT/contracts/sampleDirectory/Test contract.yaml"));
        verify(contractFileConverter, times(1)).createContractYamlFileFromContractFileObject(
                eq(contractFile),
                endsWith("target/temp/META-INF/sample-provider-groupId/sample-provider-artifactId/0.0.1-SNAPSHOT/contracts/sampleDirectory/Test contract.yaml"));
        verify(gitService, times(4)).commitAndPushChanges(any(), any());
    }

    @Test
    @DisplayName("ContractFileOperations - Delete contract file from local repo and push to git")
    void testDeleteContractFromLocalRepoAndPushToGit() throws Exception {
        ContractFile contractFile = TestDataUtil.getSampleContractFile();
        assertThrows(ContractNotFoundException.class, () ->
                contractFileOperations.deleteContractFromLocalRepoAndPushToGit(contractFile)
        );
    }


}