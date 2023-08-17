package com.egitim.halisaha.controllers;

import com.egitim.halisaha.entities.Team;
import com.egitim.halisaha.services.TeamService;
import com.egitim.halisaha.utility.Rest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    final TeamService teamService;
@PostMapping("/insert")
    public ResponseEntity insert(@RequestBody Team team){
     return teamService.insert(team);

}

@GetMapping("/teamCreate")
    public ResponseEntity teamCreate(){
    return teamService.teamCreate();

}
    @GetMapping("/getTeamsFootballerCount")
    public ResponseEntity getTeamsCount(){
        return teamService.getTeamsFootballerCount();

    }





}
