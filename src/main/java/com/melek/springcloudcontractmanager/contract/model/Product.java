package com.melek.springcloudcontractmanager.contract.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonManagedReference
    private Long id;

    private String groupName;

    @Column(unique = true)
    private String artifactName;

    @OneToMany(mappedBy = "provider")
    @JsonIgnore
    private Set<Contract> providedContracts = new HashSet<>();

    @ManyToMany(mappedBy = "consumer")
    @JsonIgnore
    private Set<Contract> consumedContracts = new HashSet<>();


}
