package com.srb.cibil.app.controller;

import java.util.Date;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srb.cibil.app.model.Cibil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cibil")
@CrossOrigin("*")
@Slf4j
public class CibilController {

    @GetMapping("/{cibilid}")
    public ResponseEntity<Cibil> generateCibilScore(@PathVariable("cibilid") int cibilid) {
        log.info("Received request to generate CIBIL score for ID: {}", cibilid);

        Random cb = new Random();
        Cibil c = new Cibil();

        c.setCibilid(cibilid);

        int cibilscore = cb.nextInt(300, 900);
        c.setCibilScore(cibilscore);
        log.info("Generated CIBIL score: {}", cibilscore);

        if (cibilscore >= 801 && cibilscore <= 900) {
            c.setCibilRemark("Excellent !!");
        } else if (cibilscore >= 761 && cibilscore <= 800) {
            c.setCibilRemark("Very Good");
        } else if (cibilscore >= 601 && cibilscore <= 760) {
            c.setCibilRemark("Good");
        } else if (cibilscore >= 351 && cibilscore <= 600) {
            c.setCibilRemark("Average");
        } else if (cibilscore >= 300 && cibilscore <= 350) {
            c.setCibilRemark("Need Help !!");
        }

        log.info("CIBIL remark for score {} is '{}'", cibilscore, c.getCibilRemark());

        String remark = c.getCibilRemark();
        if ("Excellent !!".equals(remark) || "Very Good".equals(remark) || "Good".equals(remark) || "Average".equals(remark)) {
            c.setStatus("Approved");
        } else {
            c.setStatus("Rejected");
        }

        log.info("CIBIL status for ID {} is '{}'", cibilid, c.getStatus());

        c.setCibilScoreDataTime(new Date());
        log.info("CIBIL data prepared for ID {} at {}", cibilid, c.getCibilScoreDataTime());

        return new ResponseEntity<>(c, HttpStatus.CREATED);
    }
}
