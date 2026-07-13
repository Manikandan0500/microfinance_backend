package com.bbots.mfin.repository;

import com.bbots.mfin.dto.Module;
import com.bbots.mfin.dto.SubModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModuleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Module> moduleMapper = (rs, rowNum) -> new Module(
            rs.getLong("ORGCODE"),
            rs.getInt("MODULE_ID"),
            rs.getString("MODULENAME"),
            rs.getInt("SUB_MODULE"),
            null, // subModuleId (not in MODULE001)
            null, // subModuleName (not in MODULE001)
            null, // subModules (not in MODULE001)
            rs.getInt("status"),
            rs.getTimestamp("EDATE"),
            rs.getString("EUSER"),
            rs.getTimestamp("CDATE"),
            rs.getString("CUSER"),
            rs.getTimestamp("ADATE"),
            rs.getString("AUSER")
    );

    private final RowMapper<SubModule> subModuleMapper = (rs, rowNum) -> new SubModule(
            rs.getLong("ORGCODE"),
            rs.getInt("MODULE_ID"),
            rs.getInt("SUB_MODULEID"),
            rs.getString("SUB_MODULENAME"),
            rs.getInt("status"),
            rs.getTimestamp("EDATE"),
            rs.getString("EUSER"),
            rs.getTimestamp("CDATE"),
            rs.getString("CUSER"),
            rs.getTimestamp("ADATE"),
            rs.getString("AUSER")
    );

    // --- Module Methods ---
    public List<Module> findAllModules() {
        return jdbcTemplate.query("SELECT * FROM MODULE001", moduleMapper);
    }

    public Module findModuleById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM MODULE001 WHERE MODULE_ID = ?", moduleMapper, id);
    }

    public void saveModule(Module m) {
        String sql = "INSERT INTO MODULE001 (ORGCODE, MODULE_ID, MODULENAME, SUB_MODULE, status, EUSER) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, m.getOrgcode(), m.getModuleId(), m.getModuleName(), m.getSubModuleRequired(), m.getStatus(), m.getEUser());
    }

    public void updateModule(Module m) {
        String sql = "UPDATE MODULE001 SET MODULENAME=?, SUB_MODULE=?, status=?, CUSER=?, CDATE=CURRENT_TIMESTAMP WHERE MODULE_ID=? AND ORGCODE=?";
        jdbcTemplate.update(sql, m.getModuleName(), m.getSubModuleRequired(), m.getStatus(), m.getCUser(), m.getModuleId(), m.getOrgcode());
    }

    public void deleteModule(Integer id) {
        jdbcTemplate.update("DELETE FROM MODULE002 WHERE MODULE_ID = ?", id);
        jdbcTemplate.update("DELETE FROM MODULE001 WHERE MODULE_ID = ?", id);
    }

    // --- Sub-Module Methods ---
    public List<SubModule> findSubsByModuleId(Integer moduleId) {
        return jdbcTemplate.query("SELECT * FROM MODULE002 WHERE MODULE_ID = ?", subModuleMapper, moduleId);
    }

    public void saveSubModule(SubModule sm) {
        String sql = "INSERT INTO MODULE002 (ORGCODE, MODULE_ID, SUB_MODULEID, SUB_MODULENAME, status, EUSER) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, sm.getOrgcode(), sm.getModuleId(), sm.getSubModuleId(), sm.getSubModuleName(), sm.getStatus(), sm.getEUser());
    }

    public void updateSubModule(SubModule sm) {
        String sql = "UPDATE MODULE002 SET SUB_MODULENAME=?, status=?, CUSER=?, CDATE=CURRENT_TIMESTAMP WHERE MODULE_ID=? AND SUB_MODULEID=? AND ORGCODE=?";
        jdbcTemplate.update(sql, sm.getSubModuleName(), sm.getStatus(), sm.getCUser(), sm.getModuleId(), sm.getSubModuleId(), sm.getOrgcode());
    }

    public void deleteSubModule(Integer moduleId, Integer subModuleId) {
        jdbcTemplate.update("DELETE FROM MODULE002 WHERE MODULE_ID = ? AND SUB_MODULEID = ?", moduleId, subModuleId);
    }
}
