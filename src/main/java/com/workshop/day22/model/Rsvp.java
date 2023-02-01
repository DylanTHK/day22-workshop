package com.workshop.day22.repo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rsvp {
    private Integer id;

    private String fullName;

    private String email;

    private Integer phone; // numeric fields are integers

    private Date confirmationDate;

    private String comments;

}
