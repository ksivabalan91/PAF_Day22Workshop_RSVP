package paf.week1.day22workshoprsvp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import paf.week1.day22workshoprsvp.model.RSVP;
import paf.week1.day22workshoprsvp.service.RSVPservice;

@RestController
@RequestMapping
public class RSVPcontroller {
    @Autowired
    RSVPservice rsvpService;

    @GetMapping("/api/rsvps")
    public List<RSVP> getll(){        
        return rsvpService.getAll();
    }

    @GetMapping("/api/rsvp")
    public ResponseEntity<List<RSVP>> getByName(@RequestParam String q){
        if (rsvpService.getByName(q).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok().body(rsvpService.getByName(q));
        
    }

    @PostMapping(path="/api/rsvp", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> createRSVP(@RequestBody MultiValueMap<String,String> form){
        RSVP rsvp = new RSVP();
        
        if (null==form.getFirst("fullName")
            ||null==form.getFirst("email")
            ||null==form.getFirst("phone")
            ||null==form.getFirst("confirmationDate")
            ||null==form.getFirst("comments")
            ){return ResponseEntity.badRequest().body("bad request, form values null, rsvp not created");}

        rsvp.setFullName(form.getFirst("fullName"));        
        rsvp.setEmail(form.getFirst("email"));        
        rsvp.setPhone(form.getFirst("phone"));        
        rsvp.setConfirmationDate(java.sql.Date.valueOf(form.getFirst("confirmationDate")));        
        rsvp.setComments(form.getFirst("comments"));
        
        
        if(rsvpService.createRSVP(rsvp)==1){
            return ResponseEntity.status(201).body("Created successfully");
        };
        return ResponseEntity.badRequest().body("bad request, rsvp not created");
    }

    @PutMapping(path="/api/rsvp",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> updateRSVP(@RequestBody MultiValueMap<String,String> form){
        Optional<RSVP> opt = rsvpService.getByEmail(form.getFirst("email"));
        
        if(opt.isEmpty()){
           return ResponseEntity.notFound().build();
        }

        RSVP rsvp = new RSVP();

        if (null==form.getFirst("fullName")
        ||null==form.getFirst("email")
        ||null==form.getFirst("phone")
        ||null==form.getFirst("confirmationDate")
        ||null==form.getFirst("comments")
        ){return ResponseEntity.badRequest().body("bad request, form values null, rsvp cannot be updated");}

        rsvp.setFullName(form.getFirst("fullName"));        
        rsvp.setEmail(form.getFirst("email"));        
        rsvp.setPhone(form.getFirst("phone"));        
        rsvp.setConfirmationDate(java.sql.Date.valueOf(form.getFirst("confirmationDate")));        
        rsvp.setComments(form.getFirst("comments"));

        if(rsvpService.updateRSVP(rsvp)==1){
            return ResponseEntity.status(201).body("Updated successfully");
        };

        return ResponseEntity.badRequest().body("bad request, rsvp not updated");
    }
    // @PutMapping(path="/api/rsvp/{email}",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    // public ResponseEntity<String> updateRSVP(@RequestBody MultiValueMap<String,String> form, @PathVariable String email){
    //     Optional<RSVP> opt = rsvpService.getByEmail(email);
        
    //     if(opt.isEmpty()){
    //        return ResponseEntity.notFound().build();
    //     }

    //     RSVP rsvp = new RSVP();

    //     if (null==form.getFirst("fullName")
    //     ||null==form.getFirst("email")
    //     ||null==form.getFirst("phone")
    //     ||null==form.getFirst("confirmationDate")
    //     ||null==form.getFirst("comments")
    //     ){return ResponseEntity.badRequest().body("bad request, form values null, rsvp cannot be updated");}

    //     rsvp.setFullName(form.getFirst("fullName"));        
    //     rsvp.setEmail(form.getFirst("email"));        
    //     rsvp.setPhone(form.getFirst("phone"));        
    //     rsvp.setConfirmationDate(java.sql.Date.valueOf(form.getFirst("confirmationDate")));        
    //     rsvp.setComments(form.getFirst("comments"));

    //     if(rsvpService.updateRSVP(rsvp)==1){
    //         return ResponseEntity.status(201).body("Updated successfully");
    //     };

    //     return ResponseEntity.badRequest().body("bad request, rsvp not updated");
    // }

    @GetMapping(path="/api/rsvps/count")
    public ResponseEntity<Integer> getCount(){
        return ResponseEntity.status(201).body(rsvpService.getCount());
    }
    
    
}
