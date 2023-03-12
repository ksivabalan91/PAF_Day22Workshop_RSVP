package paf.week1.day22workshoprsvp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import paf.week1.day22workshoprsvp.model.RSVP;
import paf.week1.day22workshoprsvp.repository.RSVPrepo;

@Service
public class RSVPservice {
    @Autowired
    RSVPrepo rsvpRepo;

    public List<RSVP> getAll() {
        return rsvpRepo.getAll();
    }

    public List<RSVP> getByName(String q) {
        return rsvpRepo.getByName(q);
    }

    public Optional<RSVP> getByEmail(String email){
        return rsvpRepo.getByEmail(email);
    }

    public int createRSVP(RSVP rsvp) {
        int i = 0;
        i = rsvpRepo.createRSVP(rsvp);
        return i;
    }

    public int updateRSVP(RSVP rsvp) {
        int i = 0;
        i = rsvpRepo.updateRSVP(rsvp);
        return i;
    }

    public int getCount() {
        return rsvpRepo.getCount();
    }

    
    
}
