package com.egitim.halisaha.services;

import com.egitim.halisaha.entities.Footballer;
import com.egitim.halisaha.entities.Team;
import com.egitim.halisaha.repositories.FootballerRepository;
import com.egitim.halisaha.repositories.TeamRepository;
import com.egitim.halisaha.utility.Rest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FootballerService {
    final HttpServletRequest servletRequest;
    final FootballerRepository footballerRepository;
    final TeamRepository teamRepository;
    final TinkEncDec tinkEncDec;
    public ResponseEntity register(  Footballer footballer){
        try {
            if(footballer.getYas()<18 || footballer.getYas()>50)
                return Rest.fail(footballer, "Futbolcunun yaşı 18- 50 aralığında olmalı", HttpStatus.BAD_REQUEST);

            Optional<Footballer> optionalFootballer= footballerRepository.findByEmailEqualsIgnoreCase(footballer.getEmail());
            if(optionalFootballer.isPresent())
                return Rest.fail(footballer,"Bu maille daha önce kayıt alınmıştır", HttpStatus.BAD_REQUEST);
            footballer.setSifre(tinkEncDec.encrypt(footballer.getSifre()));
            footballerRepository.save(footballer);
            return Rest.success(footballer.getFid().toString() + " id ile takım seçmelisiniz.");
       } catch (Exception e) {
            return Rest.fail(e.getMessage(), "Futbolcu VT eklenemedi", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
  

    public ResponseEntity<Long> login(Footballer footballer){
        Optional<Footballer> footballerFromDB =
                footballerRepository.findByEmailEqualsIgnoreCase(footballer.getEmail());
        if(footballerFromDB.isPresent()){
            String password = tinkEncDec.decrypt(footballerFromDB.get().getSifre());
            if(footballer.getSifre().equals(password)){
                servletRequest.getSession().setAttribute("admin","adm");
                servletRequest.getSession().setAttribute("fid",footballerFromDB.get().getFid());
                return Rest.success(footballerFromDB.get().getFid());
            }
        }
        return Rest.fail(footballer.getEmail(),"Email veya Şifre hatalı!",HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity teamInsert(String teamName) {
        String[] teams = {"A", "B"};
        boolean teamIsValid = false;
        for (String item : teams) {
            if (teamName.equalsIgnoreCase(item)) {
                teamIsValid = true;
                break;
            }
        }

        if (!teamIsValid)
            return Rest.fail("Girilen takım:" + teamName, "Takım A ve B için kayıt olunmalıdır", HttpStatus.BAD_REQUEST);

        //Daha önce girdiği takım varmı kontrolü

        Object adminObj = servletRequest.getSession().getAttribute("admin");
        Object fidObj = servletRequest.getSession().getAttribute("fid");
        Long fid = Long.parseLong( servletRequest.getSession().getAttribute("fid").toString());
        Optional<Footballer> footballer = footballerRepository.findById(fid);
        if(footballer.isPresent()) {
            if(footballer.get().getFootballerTeam()!=null)
                return Rest.fail("", "Daha önce takım kaydı alınmıştır", HttpStatus.BAD_REQUEST);


            Optional<Team> team = teamRepository.findFirstByTnameEqualsIgnoreCase(teamName);
            footballer.get().setFootballerTeam(team.get());
            footballer.ifPresent(footballerRepository::save);
            return Rest.success("Takım Kaydınız alınmıştır..");
        }
        else{
            return Rest.fail("", "Futbolcu id bulunamadı", HttpStatus.BAD_REQUEST);

        }



    }

    public ResponseEntity<Footballer> getAllFootballers(){
        return Rest.success(footballerRepository.findAll());
    }
}
