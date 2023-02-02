package com.workshop.day22.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    // /api/rsvps
    @GetMapping
    public ResponseEntity<List<Rsvp>> getAll() {
        
        List<Rsvp> rsvpResults = rsvpRepo.findAll();

        if (rsvpResults.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
 
        return new ResponseEntity<>(rsvpResults, HttpStatus.OK);
    }

    // /api/rsvps?q=fred
    @GetMapping("/")
    public ResponseEntity<List<Rsvp>> getByName(@RequestParam("q") String name) {
        System.out.println("Name query: " + name);
        List<Rsvp> rsvpResults = rsvpRepo.findByName(name);

        if (rsvpResults.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rsvpResults, HttpStatus.OK);
    }

    // /api/rsvps - 
    // inserting rsvp with JSON
    @PostMapping
    public ResponseEntity<String> saveRsvp(@RequestBody Rsvp rsvp) {

        // add code to check for existing (if existing, update)

        Boolean saved = rsvpRepo.save(rsvp);

        if (saved) {
            return new ResponseEntity<>("RSVP record created Successfully", 
                HttpStatus.CREATED); // code 201
        } else {
            return new ResponseEntity<>("RSVP record Failed to create", 
                HttpStatus.NOT_ACCEPTABLE);
        }
    }

    // /api/rsvps/
    // update by id and json body
    @PostMapping("/{id}")
    public ResponseEntity<String> updateRsvpId(@PathVariable("id") Integer id, 
        @RequestBody Rsvp rsvp) {

        // method to check if existing (returns boolean)
        Rsvp r = rsvpRepo.findById(id);
        
        Boolean result = false;

        // checks for valid row
        // updates SQL table with rsvp object
        if (null != r) {
            result = rsvpRepo.update(rsvp, id);
        } 

        if (result) {
            return new ResponseEntity<>("RSVP record UPDATED Successfully", 
                HttpStatus.OK);
        } else {
            return new ResponseEntity<>("RSVP record NOT UPDATED Successfully", 
                HttpStatus.NOT_ACCEPTABLE); 
        }
    }

    // finds row with email, update row with rsvp (Requestbody)
    // /api/rsvps/
    @PutMapping("/{email}")
    public ResponseEntity<String> updateRsvpEmail(@PathVariable("email") String email, 
        @RequestBody Rsvp rsvp) {
        
        // call method from repo
        Boolean resultList = rsvpRepo.updateByEmail(rsvp, email);

        // send appropriate response entity
        if (resultList) {
            return new ResponseEntity<>("RSVP record UPDATED Successfully", 
                HttpStatus.OK);
        } else {
            return new ResponseEntity<>("RSVP record NOT UPDATED Successfully", 
                HttpStatus.NOT_ACCEPTABLE); 
        }

    }

    // /api/rsvps/count
    @GetMapping("/count")
    public Integer countAll() {
        
        Integer result = rsvpRepo.count();

        if (null != result) {
            return result;
        } else {
            return 0;
        }
    }


}
