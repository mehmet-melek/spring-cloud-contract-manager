package com.melek.springcloudcontractmanager.contract.repository;

import com.melek.springcloudcontractmanager.contract.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch,Long> {
    Optional<Branch> findByName(String name);
}
