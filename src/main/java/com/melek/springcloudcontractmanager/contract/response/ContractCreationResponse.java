package com.melek.springcloudcontractmanager.contract.response;

import com.melek.springcloudcontractmanager.contract.dto.BranchDto;
import com.melek.springcloudcontractmanager.contract.dto.ProductDto;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractCreationResponse {
    private Long id;
    private String name;
    private Set<BranchDto> branch = new HashSet<>();
    private Set<ProductDto> consumer = new HashSet<>();
    private ProductDto provider;
}