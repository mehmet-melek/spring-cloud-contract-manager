package com.melek.springcloudcontractmanager.contract.repository;

import com.melek.springcloudcontractmanager.contract.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Set<Contract> findByProviderArtifactId(String providerArtifactId);

    @Query("SELECT c FROM Contract c JOIN c.provider p JOIN c.branch b WHERE p.artifactId = :artifactId AND b.name = :branchName")
    Set<Contract> findByProviderArtifactIdAndBranchName(@Param("artifactId") String artifactId, @Param("branchName") String branchName);

    Set<Contract> findByProjectAndProductAndApplication(String project, String product, String application);

    Set<Contract> findByProjectAndProductAndApplicationAndBranch_Name(String project, String product, String application, String branchName);


    @Query("SELECT MAX(e.id) FROM Contract e")
    Long findMaxId();


}
