package com.melek.springcloudcontractmanager.contract.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.melek.springcloudcontractmanager.contract.dto.BranchDto;
import com.melek.springcloudcontractmanager.contract.dto.ProductDto;
import com.melek.springcloudcontractmanager.contract.model.Branch;
import com.melek.springcloudcontractmanager.contract.model.ContractStatus;
import com.melek.springcloudcontractmanager.contract.model.Product;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractSearchResponse {

    private Long id;
    private Boolean ignored;
    private String name;
    private String description;
    private String directory;
    private ContractStatus status;
    private Map<String, Object> request;
    private Map<String, Object> response;
    private Set<ProductDto> consumer = new HashSet<>();
    private ProductDto provider;
    private Set<BranchDto> branch = new HashSet<>();

}
