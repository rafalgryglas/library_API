package com.library.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "READERS")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String lastname;

    @Column(name = "Created")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate dateCreateAccount;

    public Reader(String name, String lastname, LocalDate dateCreateAccount) {
        this.name = name;
        this.lastname = lastname;
        this.dateCreateAccount = dateCreateAccount;
    }
}

