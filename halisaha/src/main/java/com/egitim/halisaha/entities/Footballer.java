package com.egitim.halisaha.entities;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Data
public class Footballer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fid;

    @NotEmpty
    @NotNull
    private String ad;

    @NotEmpty
    @NotNull
    private String soyad;

    @Column(unique = true)
    @Email
    @Size(min = 10, max = 100)
    @NotEmpty
    @NotNull
    private String email;

    @Size(min = 4)
    @NotEmpty
    @NotNull
    private String sifre;

    @Range(message = "18 yaşından büyükler sisteme kayıt olabilir.", min = 18, max = 50)
    @NotNull
    private Integer yas;

    @ManyToOne
    private Team footballerTeam;

}
