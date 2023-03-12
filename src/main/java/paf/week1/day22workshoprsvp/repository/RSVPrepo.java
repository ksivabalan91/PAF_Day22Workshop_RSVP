package paf.week1.day22workshoprsvp.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import paf.week1.day22workshoprsvp.model.RSVP;

@Repository
public class RSVPrepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String countSQL = "select count(*) from rsvp;";
    
    public int[] batchUpdate(List<RSVP> rsvps){
        return jdbcTemplate.batchUpdate(insertSQL, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException{
                ps.setString(1, rsvps.get(i).getFullName());                
                ps.setString(2, rsvps.get(i).getEmail());                
                ps.setString(3, rsvps.get(i).getPhone());                
                ps.setDate(4, rsvps.get(i).getConfirmationDate());                
                ps.setString(5, rsvps.get(i).getComments());                
            }
            public int getBatchSize(){
                return rsvps.size();
            }
            
        });
    }
    //! FIND ALL RSVP
    private final String selectAllSQL = "select * from rsvp;";
    public List<RSVP> getAll() {
        return jdbcTemplate.query(selectAllSQL, BeanPropertyRowMapper.newInstance(RSVP.class));
    }

    //! FIND RSVP BY NAME LIKE    
    private final String selectByNameSQL = "select * from rsvp where full_name like ?;";
    public List<RSVP> getByName(String q) {
        return jdbcTemplate.query(selectByNameSQL, BeanPropertyRowMapper.newInstance(RSVP.class),"%"+q+"%");
    }
    
    //! SELECT RSVP BY EMAIL(primary key)
    private final String selectByEmailSQL = "select * from rsvp where email=?;";
    public Optional<RSVP> getByEmail(String email){
        List<RSVP> results = jdbcTemplate.query(selectByEmailSQL, BeanPropertyRowMapper.newInstance(RSVP.class),email);
        if (results.isEmpty()){
            return Optional.empty();
        }else{
            return Optional.ofNullable(results.get(0));
        }
    }
    
    //! CREATE NEW RSVP
    private final String insertSQL = "insert into rsvp(full_name,email,phone,confirmation_date,comments) values (?,?,?,?,?);";
    public int createRSVP(RSVP rsvp) {
        int i =0;
        i = jdbcTemplate.update(insertSQL, rsvp.getFullName(),rsvp.getEmail(),rsvp.getPhone(),rsvp.getConfirmationDate(),rsvp.getComments());        
        return i;
    }
    
    //! UPDATE RSVP
    private final String updateSQL = "update rsvp set full_name = ?, email = ?, phone = ?,confirmation_date=?, comments=? where id=?;";
    public int updateRSVP(RSVP rsvp) {
        int i =0;
        i = jdbcTemplate.update(updateSQL, rsvp.getFullName(),rsvp.getEmail(),rsvp.getPhone(),rsvp.getConfirmationDate(),rsvp.getComments(),rsvp.getEmail());        
        return i;
    }
    public int getCount() {
        return jdbcTemplate.queryForObject(countSQL, Integer.class);
    }
    
}
