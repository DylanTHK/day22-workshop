package com.workshop.day22.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workshop.day22.model.Rsvp;
import com.workshop.day22.repo.RsvpRepoImpl;

@RestController
@RequestMapping("/api/rsvps")
public class RsvpController {
    
    @Autowired
    RsvpRepoImpl rsvpRepo;

    @GetMapping
    public ResponseEntity<List<Rsvp>> getAll() {
        
        List<Rsvp> rsvpResults = rsvpRepo.findAll();

        if (rsvpResults.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
 
        return new ResponseEntity<>(rsvpResults, HttpStatus.OK);
    }

    // TODO revivew repoImpl (LIKE %% not working)
    @GetMapping("/")
    public ResponseEntity<List<Rsvp>> getByName(@RequestParam("name") String name) {
        System.out.println("Name query: " + name);
        List<Rsvp> rsvpResults = rsvpRepo.findByName(name);

        if (rsvpResults.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rsvpResults, HttpStatus.OK);
    }

    

}
