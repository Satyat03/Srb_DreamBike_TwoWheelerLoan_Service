package com.srb.cibil.app.controller;

import java.util.Date;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srb.cibil.app.model.Cibil;

@RestController
@RequestMapping("/cibil")
public class CibilController {

	@GetMapping("/{cibilid}")
	public ResponseEntity<Cibil> generateCibilScore(@PathVariable ("cibilid")int cibilid) {

		Random cb = new Random();
		Cibil c = new Cibil();
		
		c.setCibilid(cibilid);
		int cibilscore = cb.nextInt(300, 900);
		c.setCibilScore(cibilscore);
		
		
		if (cibilscore >= 801 && cibilscore <= 900) {
            c.setCibilRemark("Excellent !!");
        } else if (cibilscore >= 761 && cibilscore <= 800) {
            c.setCibilRemark("Very Good");
        } else if (cibilscore >= 701 && cibilscore <= 760) {
            c.setCibilRemark("Good");
        } else if (cibilscore >= 601 && cibilscore <= 700) {
            c.setCibilRemark("Average");
        } else if (cibilscore >= 300 && cibilscore <= 600) {
            c.setCibilRemark("Need Help !!");
        }

		String remark = c.getCibilRemark();
        if ("Excellent !!".equals(remark) || "Very Good".equals(remark) || "Good".equals(remark)) {
            c.setStatus("Approved");
        } else {
            c.setStatus("Rejected");
        }
		
		c.setCibilScoreDataTime(new Date());
		System.out.println(c);

		return new ResponseEntity<Cibil>(c,HttpStatus.CREATED);
	}

	




}
