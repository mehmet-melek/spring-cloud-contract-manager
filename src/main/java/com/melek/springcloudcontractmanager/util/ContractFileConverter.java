package com.melek.springcloudcontractmanager.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melek.springcloudcontractmanager.contract.dto.ContractFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ContractFileConverter {

    Logger logger = LoggerFactory.getLogger(ContractFileConverter.class);


    public ContractFile convertContractFileToContractJsonObject(File file) {
        ContractFile contractFile = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            contractFile = objectMapper.readValue(file, ContractFile.class);
        } catch (IOException e) {
            logger.error("Error reading contract file: {}", file.getAbsolutePath(), e);
        }
        return contractFile;
    }

    public void createContractYamlFileFromContractFileObject(ContractFile contractFile, String contractDirectoryAndName) {
        try {
            File file = new File(contractDirectoryAndName);
            file.getParentFile().mkdirs();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, contractFile);
            logger.info("Contract file created.");
        } catch (IOException e) {
            logger.info("An error occurred while creating the contract! : {}", e.getMessage());
        }
    }


}
