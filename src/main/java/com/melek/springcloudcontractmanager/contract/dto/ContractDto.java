package com.melek.springcloudcontractmanager.contract.dto;

import com.melek.springcloudcontractmanager.contract.model.ContractStatus;
import lombok.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractDto {

    private boolean ignored = false;
    private String name;
    private String description;
    private Map<String, Object> request;
    private Map<String, Object> response;
    private String directory = "base";
    private ContractStatus status;
    private Set<ProductDto> consumer = new HashSet<>();
    private ProductDto provider;
    private Set<BranchDto> branch = new HashSet<>();

    //"", ", "label", "outputMessage", "input", "", "", "", "inProgress", "priority", ""])

}

