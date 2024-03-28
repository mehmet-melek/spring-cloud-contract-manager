package com.melek.springcloudcontractmanager.contract.service;

import com.melek.springcloudcontractmanager.contract.dto.BranchDto;
import com.melek.springcloudcontractmanager.contract.mapper.BranchMapper;
import com.melek.springcloudcontractmanager.contract.model.Branch;
import com.melek.springcloudcontractmanager.contract.repository.BranchRepository;
import com.melek.springcloudcontractmanager.util.TestDataUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchServiceTest {

    @InjectMocks
    private BranchService branchService;
    @Mock
    private BranchRepository branchRepository;
    @Spy
    private BranchMapper branchMapper = Mappers.getMapper(BranchMapper.class);


    @Test
    @DisplayName("BranchService - Save and get saved branches")
    void testCreateAndGetBranches() throws Exception {

        Set<BranchDto> branchDtoSet = TestDataUtil.getSampleBranchDto();
        Set<Branch> branches = TestDataUtil.getSampleBranch();
        for (Branch branch : branches) {
            when(branchRepository.findByName(branch.getName())).thenReturn(Optional.of(branch));
        }
        when(branchRepository.findByName("TEMP")).thenReturn(Optional.empty());

        Set<Branch> savedBranches = branchService.createAndGetBranches(branchDtoSet);
        Assertions.assertEquals(branchDtoSet.size(), savedBranches.size());
        verify(branchMapper, times(1)).branchDtoListToBranchList(branchDtoSet);
        verify(branchRepository, times(4)).findByName(anyString());
        verify(branchRepository, times(1)).save(any());

    }


    @Test
    @DisplayName("BranchService - Save branches from local repo and get saved branches")
    void testSaveInitialBranches() throws Exception {

        Set<Branch> branches = TestDataUtil.getSampleBranch();
        for (Branch branch : branches) {
            when(branchRepository.findByName(branch.getName())).thenReturn(Optional.of(branch));
        }

        Set<Branch> savedBranches = branchService.saveInitialBranches(branches);
        assertEquals(branches.size(), savedBranches.size());

    }


}