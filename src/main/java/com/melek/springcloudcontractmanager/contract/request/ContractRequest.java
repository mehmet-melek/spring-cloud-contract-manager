package com.melek.springcloudcontractmanager.contract.request;

import com.melek.springcloudcontractmanager.contract.dto.ContractDto;
import com.melek.springcloudcontractmanager.contract.dto.MetaDataDto;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractRequest {
    private ContractDto contract;
    private MetaDataDto metadata;
}
