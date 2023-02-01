package com.workshop.day22.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.workshop.day22.model.Rsvp;

@Repository
public class RsvpRepoImpl {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    

    // Query Strings to add to insert to jdbcTemplate methods
    private final String findAllSQL = "select * from rsvp";

    // StringQuery to read names containing fred ()
    private final String findByNameSQL = "select * from rsvp where full_name like '%'?'%'";

    // String query for INSERT (DML)
    private final String insertSQL = """
        insert into rsvp (full_name, email, phone, confirmation_date, comments) 
        values (?, ?, ?, ?, ?)""";

    // String query to update (not all values need to be filled)
    private final String updateSQL = """
        update rsvp 
        set full_name = ?, email = ?, phone = ?, confirmation_date = ?, comments = ?
        where id = ?""";

    private final String countSQL = "select count(*) as cnt from rsvp";



    public List<Rsvp> findAll() {
        List<Rsvp> rsvpList = jdbcTemplate.query(findAllSQL, BeanPropertyRowMapper.newInstance(Rsvp.class));
        return rsvpList;
    }

    public List<Rsvp> findByName(String name) {

        // "select * from rsvp where full_name like '%?%'"
        List<Rsvp> resultList = jdbcTemplate.query(findByNameSQL, BeanPropertyRowMapper.newInstance(Rsvp.class), name);
        // check if args parameter is valid for query(sql, mapper, args)
        return resultList;
    }

    // method for insert query
    public Boolean save(Rsvp rsvp) {
        Integer result = jdbcTemplate.update(insertSQL, rsvp.getFullName(), 
            rsvp.getEmail(), rsvp.getPhone(), rsvp.getConfirmationDate(), rsvp.getComments());
        return result > 0 ? true : false;
    }

    // method for update sql
    public Boolean update(Rsvp rsvp) {
        Integer result = jdbcTemplate.update(updateSQL, rsvp.getFullName(), 
            rsvp.getEmail(), rsvp.getPhone(), rsvp.getConfirmationDate(), rsvp.getComments());
        return result > 0 ? true : false;
    }

    // method for count
    public Integer count() {
        Integer result = 0;
        result = jdbcTemplate.queryForObject(countSQL, Integer.class);
        return result;
    }

}
