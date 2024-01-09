package com.melek.springcloudcontractmanager.contract.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractFile {

    private boolean ignored = false;
    private String name;
    private String description;
    private Map<String, Object> request;
    private Map<String, Object> response;
    private MetaDataDto metadata;


}
