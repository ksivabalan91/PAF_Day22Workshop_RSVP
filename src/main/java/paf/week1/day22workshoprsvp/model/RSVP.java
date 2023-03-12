package paf.week1.day22workshoprsvp.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RSVP {

    @NotNull
    @Size(min=10,max=100,message="Fullname must be between 10 to 150 characters")
    private String fullName;
    
    @Id
    @Email(message="enter valid email")
    @Size(max=100,message="email need to be shorter then 150 characters")
    private String email;
    
    private String phone;
    
    private Date confirmationDate;
    
    private String comments;
    
}
