package com.egitim.halisaha.controllers;

import com.egitim.halisaha.entities.Footballer;
import com.egitim.halisaha.services.FootballerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/footballer")
public class FootbollerController {
    final FootballerService footballerService;

    @PostMapping("/footballerRegister")
    public ResponseEntity register(@Valid  @RequestBody Footballer footballer){
        return footballerService.register(footballer);

    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Footballer footballer){
        return footballerService.login(footballer);

    }
    @PostMapping("/teamInsert/{teamName}")
    public ResponseEntity teamInsert(@PathVariable String teamName){
      return footballerService.teamInsert(teamName);

    }

    @GetMapping("/allFootballers")
    public ResponseEntity allFootballers(){
        return footballerService.getAllFootballers();

    }

}
