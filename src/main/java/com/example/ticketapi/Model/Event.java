package com.example.ticketapi.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(25) not null unique")
    @NotNull(message = "name cant be null!")
    private String name;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "age limit cant be null!")
    private Integer ageLimit;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "capacity cant be null!")
    private Integer capacity;

    @Column(columnDefinition = "varchar(20) not null ")
    @NotNull(message = "category cant be null!")
    private String category;

    @Column(columnDefinition = "decimal not null ")
    @NotNull(message = "price cant be null!")
    private Double price;

    @FutureOrPresent(message = "wrong date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    @OneToMany(mappedBy = "event", cascade = CascadeType.DETACH)
    @PrimaryKeyJoinColumn
    private Set<Ticket> tickets;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;
}
