package com.melek.springcloudcontractmanager.contract.dto;

import com.melek.springcloudcontractmanager.contract.model.ContractStatus;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetaDataDto {

    private Long id;
    private String directory = "base";
    private ContractStatus status;
    private String project;
    private String product;
    private String application;
    private Set<ProductDto> consumer = new HashSet<>();
    private ProductDto provider;
    private Set<BranchDto> branch = new HashSet<>();


}
