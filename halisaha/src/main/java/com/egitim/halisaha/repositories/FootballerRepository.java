package com.egitim.halisaha.repositories;

import com.egitim.halisaha.entities.Footballer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FootballerRepository extends JpaRepository<Footballer, Long> {
    Optional<Footballer> findByEmailEqualsIgnoreCase(String email);
    List<Footballer> findByFootballerTeam_TnameEqualsIgnoreCase(String tname, Sort sort);
    long countByFootballerTeam_TnameIgnoreCase(String tname);



}
