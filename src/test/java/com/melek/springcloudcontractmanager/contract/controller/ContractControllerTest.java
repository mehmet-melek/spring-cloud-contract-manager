package com.melek.springcloudcontractmanager.contract.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melek.springcloudcontractmanager.contract.dto.BranchDto;
import com.melek.springcloudcontractmanager.contract.dto.ContractDto;
import com.melek.springcloudcontractmanager.contract.exception.ContractNotFoundException;
import com.melek.springcloudcontractmanager.contract.service.ContractService;
import com.melek.springcloudcontractmanager.util.TestDataUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContractController.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractService contractService;

    @Test
    @DisplayName("When post a sample contract dto to '/api/contract/create' should return created and status code should return ok")
    void testCreateContract() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ContractDto contractDto = TestDataUtil.getSampleContractDto();

        when(contractService.createContract(Mockito.any())).thenReturn("created");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/contract/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contractDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", is("created")));

    }

    @Test
    @DisplayName("When delete contract with contract id should return ok and deleted response")
    void testDeleteContract() throws Exception {
        when(contractService.deleteContract(1L)).thenReturn("Deleted");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/contract/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Deleted")));

        verify(contractService, times(1)).deleteContract(1L);
    }

    @Test
    @DisplayName("When delete non exist contract should return contract not found exception")
    void givenContractIsNotExist_whenDeleteContract_thenReturnNotFoundException() throws Exception {
        //arrange
        when(contractService.deleteContract(1L)).thenThrow(new ContractNotFoundException("1"));
        //act
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/contract/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$", is("No contract was found for 1")));
        //assert
    }

    @Test
    @DisplayName("When update consumers on contract with contract id and consumer list should return updated")
    void testUpdateConsumer() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        when(contractService.updateConsumer(any(), Mockito.any())).thenReturn("updated");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/contract/update/consumer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestDataUtil.getSampleConsumerDtoList())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("updated")));
    }

    @Test
    @DisplayName("When update environmet with contract id should return updated")
    void giveContractIsExist_whenUpdateEnvironment_shouldReturnUpdated() throws Exception {
        Set<BranchDto> branchDto = TestDataUtil.getSampleBranchDto();
        when(contractService.updateBranch(eq(1L), refEq(branchDto))).thenReturn("updated");
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/contract/update/environment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branchDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("When search contract with project,product and application should return given contract")
    void searchContractWithProjectProductAndApplication_shouldReturnGivenContracts() throws Exception {

        String testProject = "testProject";
        String testProduct = "testProduct";
        String testApplication = "testApplication";

        when(contractService.getContractsWithApplicationInfo(testProject, testProduct, testApplication, null))
                .thenReturn(TestDataUtil.getSampleContractSearchResponse());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contract/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("project", testProject)
                        .param("product", testProduct)
                        .param("application", testApplication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }


    @Test
    @DisplayName("When update contract info with contract id should return updated")
    void updateContractWithContractId_shouldReturnUpdated() throws Exception {
        ContractDto contractDto = TestDataUtil.getSampleContractDto();

        when(contractService.updateContract(any(), any())).thenReturn("updated");
        //  when(contractService.updateContract(any(), any())).thenReturn("updated");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(contractDto));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/contract/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contractDto))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("updated")));
    }


}

