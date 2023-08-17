package com.egitim.halisaha.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;
   // @Column(unique = true)
    private String tname;

   // private Footballer footballer;



}
