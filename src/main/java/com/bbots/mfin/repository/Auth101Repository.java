package com.bbots.mfin.repository;
 
import java.sql.Date;
import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
 
import com.bbots.mfin.dto.Auth101;
import com.bbots.mfin.dto.Auth101Id;
 
@Repository
public class Auth101Repository {
 
    @Autowired
    private JdbcTemplate jdbcTemplate;
 
    private final RowMapper<Auth101> rowMapper = (rs, rowNum) -> {
        Auth101Id id = new Auth101Id(
            rs.getLong("orgcode"),
            rs.getString("programid")
        );
        Auth101 auth101 = new Auth101();
        auth101.setId(id);
        auth101.setApprovalReq(rs.getObject("approvalreq", Short.class));
        auth101.setPreApproveProc(rs.getObject("pre_approve_proc", Short.class));
        auth101.setPreExecMethod(rs.getString("pre_exec_method"));
        auth101.setPreProcessName(rs.getString("pre_processname"));
        auth101.setPostApproveProc(rs.getObject("post_approve_proc", Short.class));
        auth101.setPostExecMethod(rs.getString("post_exec_method"));
        auth101.setPostProcessName(rs.getString("post_processname"));
        auth101.setIsTranPgm(rs.getObject("istranpgm", Short.class));
        auth101.seteUser(rs.getString("euser"));
        Date edate = rs.getDate("edate");
        if (edate != null) {
            auth101.seteDate(edate.toLocalDate());
        }
        auth101.setaUser(rs.getString("auser"));
        Date adate = rs.getDate("adate");
        if (adate != null) {
            auth101.setaDate(adate.toLocalDate());
        }
        auth101.setcUser(rs.getString("cuser"));
        Date cdate = rs.getDate("cdate");
        if (cdate != null) {
            auth101.setcDate(cdate.toLocalDate());
        }
        return auth101;
    };
 
    public List<Auth101> findAll() {
        String sql = "SELECT * FROM loandev.auth101";
        return jdbcTemplate.query(sql, rowMapper);
    }

 
    public List<Auth101> findByIdOrgCode(Long orgCode) {
        String sql = "SELECT * FROM loandev.auth101 WHERE orgcode = ?";
        return jdbcTemplate.query(sql, rowMapper, orgCode);
    }
 
    public Optional<Auth101> findById(Auth101Id id) {
        String sql = "SELECT * FROM loandev.auth101 WHERE orgcode = ? AND programid = ?";
        List<Auth101> results = jdbcTemplate.query(sql, rowMapper, id.getOrgCode(), id.getProgramId());
        return results.stream().findFirst();
    }
 
    public boolean existsById(Auth101Id id) {
        String sql = "SELECT COUNT(*) FROM loandev.auth101 WHERE orgcode = ? AND programid = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id.getOrgCode(), id.getProgramId());
        return count != null && count > 0;
    }
 
    public Auth101 save(Auth101 auth101) {
        if (existsById(auth101.getId())) {
            // Update
            String sql = "UPDATE loandev.auth101 SET approvalreq = ?, pre_approve_proc = ?, pre_exec_method = ?, " +
                         "pre_processname = ?, post_approve_proc = ?, post_exec_method = ?, post_processname = ?, " +
                         "istranpgm = ?, cuser = ?, cdate = ? " +
                         "WHERE orgcode = ? AND programid = ?";
            jdbcTemplate.update(
                sql,
                auth101.getApprovalReq(),
                auth101.getPreApproveProc(),
                auth101.getPreExecMethod(),
                auth101.getPreProcessName(),
                auth101.getPostApproveProc(),
                auth101.getPostExecMethod(),
                auth101.getPostProcessName(),
                auth101.getIsTranPgm(),
                auth101.getcUser(),
                auth101.getcDate() != null ? Date.valueOf(auth101.getcDate()) : null,
                auth101.getId().getOrgCode(),
                auth101.getId().getProgramId()
            );
        } else {
            // Insert
            String sql = "INSERT INTO loandev.auth101 (orgcode, programid, approvalreq, pre_approve_proc, pre_exec_method, " +
                         "pre_processname, post_approve_proc, post_exec_method, post_processname, istranpgm, " +
                         "euser, edate, auser, adate, cuser, cdate) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(
                sql,
                auth101.getId().getOrgCode(),
                auth101.getId().getProgramId(),
                auth101.getApprovalReq(),
                auth101.getPreApproveProc(),
                auth101.getPreExecMethod(),
                auth101.getPreProcessName(),
                auth101.getPostApproveProc(),
                auth101.getPostExecMethod(),
                auth101.getPostProcessName(),
                auth101.getIsTranPgm(),
                auth101.geteUser(),
                auth101.geteDate() != null ? Date.valueOf(auth101.geteDate()) : null,
                auth101.getaUser(),
                auth101.getaDate() != null ? Date.valueOf(auth101.getaDate()) : null,
                auth101.getcUser(),
                auth101.getcDate() != null ? Date.valueOf(auth101.getcDate()) : null
            );
        }
        return auth101;
    }
 
    public void deleteById(Auth101Id id) {
        String sql = "DELETE FROM loandev.auth101 WHERE orgcode = ? AND programid = ?";
        jdbcTemplate.update(sql, id.getOrgCode(), id.getProgramId());
    }
}