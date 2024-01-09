package com.melek.springcloudcontractmanager.contract.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private String groupName;
    private String artifactName;
}
