package com.melek.springcloudcontractmanager.contract.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private String groupId;
    private String artifactId;
}
