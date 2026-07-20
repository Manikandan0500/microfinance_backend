package com.bbots.mfin.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.Auth101;
import com.bbots.mfin.dto.AuthQ001;

@Repository
public class AuthQ001Repository {
	

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    private final RowMapper<AuthQ001> rowMapper = (rs, rowNum) -> {

        AuthQ001 AuthQ001 = new AuthQ001();
        
        AuthQ001.seteUser(rs.getString("euser"));
        AuthQ001.setOrgcode(rs.getLong("orgcode"));
        AuthQ001.setProgramid(rs.getString("programid"));
        AuthQ001.setAuthsl(rs.getLong("authsl"));
        AuthQ001.setDisplay_remarks(rs.getString("display_remarks"));     
        
        Date edate = rs.getDate("effdate");
        if (edate != null) {
            AuthQ001.setEffdate(edate.toLocalDate());
        }
        return AuthQ001;
    };
    
    
    public List<AuthQ001> findByIdOrgCode(Long orgCode) {
        String sql = "SELECT * FROM loandev.auth001 WHERE orgcode = ?";
        return jdbcTemplate.query(sql, rowMapper, orgCode);
    }

}
