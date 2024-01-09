package com.melek.springcloudcontractmanager.contract.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contract")
public class Contract {

    @Id
    private Long id;
    private Boolean ignored;
    private String name;
    private String description;
    private ContractStatus status;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JsonIgnore
    @JoinTable(
            name = "contract_branch",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id"))
    private Set<Branch> branch = new HashSet<>();

    private String directory;
    private String request;
    private String response;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product provider;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "contract_consumer",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> consumer = new HashSet<>();

}
