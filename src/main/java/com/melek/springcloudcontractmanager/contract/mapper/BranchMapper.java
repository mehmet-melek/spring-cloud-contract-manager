package com.melek.springcloudcontractmanager.contract.mapper;

import com.melek.springcloudcontractmanager.contract.dto.BranchDto;
import com.melek.springcloudcontractmanager.contract.model.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.Set;

@Mapper(componentModel = "spring")
@Component
public interface BranchMapper {

    BranchMapper INSTANCE = Mappers.getMapper(BranchMapper.class);

    Set<Branch> branchDtoListToBranchList(Set<BranchDto> branchDtoSet);

}
