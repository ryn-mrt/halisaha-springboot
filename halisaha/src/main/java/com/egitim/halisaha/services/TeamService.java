package com.egitim.halisaha.services;

import com.egitim.halisaha.entities.Footballer;
import com.egitim.halisaha.entities.Team;
import com.egitim.halisaha.repositories.FootballerRepository;
import com.egitim.halisaha.repositories.TeamRepository;
import com.egitim.halisaha.utility.Rest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    final TeamRepository teamRepository;
    private final FootballerRepository footballerRepository;

    public ResponseEntity insert(@RequestBody Team team){
        teamRepository.save(team);
        return Rest.success(team);

    }
    public ResponseEntity teamCreate(){
        int kadroSayisi =6;
        int yedekSayisi =3;
        int toplamSayi = kadroSayisi+yedekSayisi;
        int countTeamALimit = toplamSayi ;
        int countTeamBLimit =toplamSayi;

        int countTeamA = (int)footballerRepository.countByFootballerTeam_TnameIgnoreCase("A");
        int countTeamB = (int)footballerRepository.countByFootballerTeam_TnameIgnoreCase("B");

        if(countTeamA<kadroSayisi || countTeamB<kadroSayisi)
            return Rest.fail("","Takım oluşturmak için her takım için en az 6 adet başvuru olması gerekmektedir", HttpStatus.EXPECTATION_FAILED );

        if(countTeamA<toplamSayi)
            countTeamALimit = countTeamA;
        if(countTeamB<toplamSayi)
            countTeamBLimit = countTeamB;

        Sort sort = Sort.by("yas").ascending();
        List<Footballer> footballersA;
        footballersA = footballerRepository.findByFootballerTeam_TnameEqualsIgnoreCase("A",sort)
                .stream()
                .limit(countTeamALimit)
                .collect(Collectors.toList());

        List<Footballer> footballersB;
        footballersB = footballerRepository.findByFootballerTeam_TnameEqualsIgnoreCase("B",sort)
                .stream()
                .limit(countTeamBLimit)
                .collect(Collectors.toList());

        List<Footballer> kadroA = footballersA.subList(0,6);
        List<Footballer> kadroAYedek = footballersA.subList(6,countTeamALimit);
        List<Footballer> kadroB =footballersB.subList(0,6);
        List<Footballer> kadroBYedek = footballersB.subList(6,countTeamBLimit);

      Map<String,Object> teams = new HashMap<>();
      teams.put("A", kadroA);
      teams.put("B", kadroB);
      teams.put("A-Yedek",kadroAYedek);
      teams.put("B-Yedek",kadroBYedek);
      return Rest.success(teams);
    }
    public ResponseEntity getTeamsFootballerCount() {
        int countTeamA = (int) footballerRepository.countByFootballerTeam_TnameIgnoreCase("A");
        int countTeamB = (int) footballerRepository.countByFootballerTeam_TnameIgnoreCase("B");
        Map<String, Integer> teamsCounts = new HashMap<>();
        teamsCounts.put("A", countTeamA);
        teamsCounts.put("B", countTeamB);
        return Rest.success(teamsCounts);
    }


}
