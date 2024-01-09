package com.melek.springcloudcontractmanager.contract.service;

import com.melek.springcloudcontractmanager.contract.dto.BranchDto;
import com.melek.springcloudcontractmanager.contract.mapper.BranchMapper;
import com.melek.springcloudcontractmanager.contract.model.Branch;
import com.melek.springcloudcontractmanager.contract.repository.BranchRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    public BranchService(BranchRepository branchRepository, BranchMapper branchMapper) {
        this.branchRepository = branchRepository;
        this.branchMapper = branchMapper;
    }

    public Set<Branch> createAndGetBranches(Set<BranchDto> branchDto) {
        Set<Branch> branches = branchMapper.branchDtoListToBranchList(branchDto);

        return branches.stream()
                .map(branch -> {
                    Optional<Branch> existingBranch = branchRepository.findByName(branch.getName());
                    return existingBranch.orElseGet(() -> branchRepository.save(branch));
                })
                .collect(Collectors.toSet());
    }

    public Set<Branch> getBranchSet(String env) {
        Set<Branch> branchSet = new HashSet<>();
        Branch branch = branchRepository.findByName(env).orElseThrow();
        branchSet.add(branch);
        return branchSet;
    }

    public Set<Branch> saveInitialBranches (Set<Branch> branches) {
        return branches.stream()
                .map(branch -> {
                    Optional<Branch> existingBranch = branchRepository.findByName(branch.getName());
                    return existingBranch.orElseGet(() -> branchRepository.save(branch));
                })
                .collect(Collectors.toSet());
    }
}
