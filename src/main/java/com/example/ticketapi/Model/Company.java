package com.example.ticketapi.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(columnDefinition ="varchar(25) not null" )
    @NotNull(message = "name cant be null!")
    private String name;

    @Column(columnDefinition ="decimal not null " )
    @NotNull(message = "revenue cant be null!")
    private Double revenue;

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL )
    @PrimaryKeyJoinColumn
    private Set<Event> events;
}
