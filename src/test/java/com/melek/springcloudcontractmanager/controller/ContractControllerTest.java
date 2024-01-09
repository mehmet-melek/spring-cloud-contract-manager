package com.melek.springcloudcontractmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melek.springcloudcontractmanager.contract.controller.ContractController;
import com.melek.springcloudcontractmanager.contract.request.ContractRequest;
import com.melek.springcloudcontractmanager.contract.service.ContractService;
import com.melek.springcloudcontractmanager.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContractController.class)
class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractService contractService;

/*    @Test
    void testCreateContract() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ContractRequest contractRequest = TestDataUtil.getSampleContractRequest();

        when(contractService.createContract(Mockito.refEq(contractRequest))).thenReturn(null);
        mockMvc.perform(post("/api/contract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contractRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }*/

}