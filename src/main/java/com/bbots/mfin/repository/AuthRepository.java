package com.bbots.mfin.repository;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.Auth101Config;
import com.bbots.mfin.dto.AuthConfigDTO;
import com.bbots.mfin.dto.AuthDataBlock;
import com.bbots.mfin.dto.AuthLevelDTO;
import com.bbots.mfin.dto.AuthRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
 
@Repository
public class AuthRepository {
 
	@Autowired
	private JdbcTemplate jdbcTemplate;
 
	// --- AUTH101 Mappers ---
	private final RowMapper<Auth101Config> configMapper = (rs, rowNum) -> new Auth101Config(rs.getString("PROGRAMID"),
			rs.getString("PROGRAMID"), rs.getInt("APPROVALREQ") == 1, rs.getInt("PRE_APPROVE_PROC") == 1,
			rs.getString("PRE_EXEC_METHOD"), rs.getString("PRE_PROCESSNAME"), rs.getInt("POST_APPROVE_PROC") == 1,
			rs.getString("POST_EXEC_METHOD"), rs.getString("POST_PROCESSNAME"), rs.getInt("ISTRANPGM") == 1,
			0); // Levels are fetched separately
 
	// --- AUTH101 Methods ---
	public List<Auth101Config> getAllConfigs() {
		return jdbcTemplate.query("SELECT * FROM AUTH101", configMapper);
	}
 
	public Auth101Config getConfigById(String id) {
		return jdbcTemplate.queryForObject("SELECT * FROM AUTH101 WHERE PROGRAM_ID = ?", configMapper, id);
	}
 
	public void updateConfig(Auth101Config cfg) {
		String sql = "UPDATE AUTH101 SET APPROVAL_REQ = ?, PRE_APPROVE_PROC = ?, PRE_EXEC_METHOD = ?, "
				+ "PRE_PROCESS_NAME = ?, POST_APPROVE_PROC = ?, POST_EXEC_METHOD = ?, "
				+ "POST_PROCESS_NAME = ?, AUTH_LEVELS = ? WHERE PROGRAM_ID = ?";
		jdbcTemplate.update(sql, cfg.isApprovalReq(), cfg.isPreApproveProc(), cfg.getPreExecMethod(),
				cfg.getPreProcessName(), cfg.isPostApproveProc(), cfg.getPostExecMethod(), cfg.getPostProcessName(),
				cfg.getLevels(), cfg.getId());
	}
 
	// --- AUTH001/002 Methods ---
	public List<AuthRecord> getQueue(Long orgCode) {
		String sql = "SELECT a.* FROM AUTH001 a " +
				"WHERE a.ORGCODE = ? AND (" +
				"(a.FLUSER = '0') " +
				"OR (a.SLUSER = '0' AND EXISTS (SELECT 1 FROM AUTH102 c WHERE c.PROGRAMID = a.PROGRAMID AND c.LEVELS >= 2)) "
				+
				"OR (a.TLUSER = '0' AND EXISTS (SELECT 1 FROM AUTH102 c WHERE c.PROGRAMID = a.PROGRAMID AND c.LEVELS >= 3)) "
				+
				") ORDER BY a.EDATE DESC";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			AuthRecord record = new AuthRecord();
			record.setOrgCode(rs.getLong("ORGCODE"));
			record.setProgramId(rs.getString("PROGRAMID"));
			record.setAuthSl(rs.getLong("AUTHSL"));
			record.setDisplayRemarks(rs.getString("DISPLAY_REMARKS"));
			record.setFlUser(rs.getString("FLUSER"));
			record.setFlUserDate(rs.getTimestamp("FLDATE"));
			record.setSlUser(rs.getString("SLUSER"));
			record.setSlUserDate(rs.getTimestamp("SLDATE"));
			record.setTlUser(rs.getString("TLUSER"));
			record.setTlUserDate(rs.getTimestamp("TLDATE"));
			record.setEntryUser(rs.getString("EUSER"));
			record.setEntryDate(rs.getTimestamp("EDATE"));
			record.setAuthLock(rs.getInt("AUTHLOCK"));
			record.setCorrectionReq(rs.getInt("CORRECTIONREQ") == 1);
			record.setCorrectionDlts(rs.getString("CORRECTIONDLTS"));
			// Fetch blocks
			record.setDataBlocks(getDataBlocks(record.getAuthSl()));
			return record;
		}, orgCode);
	}
 
	public List<AuthRecord> getQueueByUser(String userId) {
		String sql = "SELECT * FROM AUTH001 WHERE EUSER = ? ORDER BY EDATE DESC";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			AuthRecord record = new AuthRecord();
			record.setOrgCode(rs.getLong("ORGCODE"));
			record.setProgramId(rs.getString("PROGRAMID"));
			record.setAuthSl(rs.getLong("AUTHSL"));
			record.setDisplayRemarks(rs.getString("DISPLAY_REMARKS"));
			record.setFlUser(rs.getString("FLUSER"));
			record.setFlUserDate(rs.getTimestamp("FLDATE"));
			record.setSlUser(rs.getString("SLUSER"));
			record.setSlUserDate(rs.getTimestamp("SLDATE"));
			record.setTlUser(rs.getString("TLUSER"));
			record.setTlUserDate(rs.getTimestamp("TLDATE"));
			record.setEntryUser(rs.getString("EUSER"));
			record.setEntryDate(rs.getTimestamp("EDATE"));
			record.setAuthLock(rs.getInt("AUTHLOCK"));
			record.setCorrectionReq(rs.getInt("CORRECTIONREQ") == 1);
			record.setCorrectionDlts(rs.getString("CORRECTIONDLTS"));
			// Fetch blocks
			record.setDataBlocks(getDataBlocks(record.getAuthSl()));
			return record;
		}, userId);
	}
 
	public String getProgramId(Long authSl) {
		String sql = "SELECT PROGRAMID FROM AUTH001 WHERE AUTHSL = ?";
		return jdbcTemplate.queryForObject(sql, String.class, authSl);
	}
 
	public List<AuthDataBlock> getDataBlocks(Long authSl) {
		return jdbcTemplate.query("SELECT * FROM AUTH002 WHERE AUTHSL = ?", (rs, rowNum) -> {
			AuthDataBlock block = new AuthDataBlock();
			block.setOrgCode(rs.getLong("ORGCODE"));
			block.setEffDate(rs.getDate("EFFDATE"));
			block.setProgramId(rs.getString("PROGRAMID"));
			block.setPrimaryKey(rs.getString("PRIMARYKEY"));
			block.setAuthSl(rs.getLong("AUTHSL"));
			block.setRecSl(rs.getInt("RECSL"));
			block.setTableName(rs.getString("TABLENAME"));
			block.setDataBlock(rs.getString("DATABLOCK"));
			return block;
		}, authSl);
	}
 
	public void insertAuthRequest(AuthRecord record) {
		if (record.getAuthSl() != null && record.getAuthSl() > 0) {
			// Update existing record (Resubmission)
			String sqlUpdate = "UPDATE AUTH001 SET CORRECTIONREQ = 0, CORRECTIONDLTS = NULL, "
					+ "FLUSER = '0', SLUSER = '0', TLUSER = '0', "
					+ "DISPLAY_REMARKS = ?, EDATE = CURRENT_TIMESTAMP WHERE AUTHSL = ?";
			jdbcTemplate.update(sqlUpdate, record.getDisplayRemarks(), record.getAuthSl());
 
			// Re-insert data blocks
			jdbcTemplate.update("DELETE FROM AUTH002 WHERE AUTHSL = ?", record.getAuthSl());
			if (record.getDataBlocks() != null) {
				String sql002 = "INSERT INTO AUTH002 (ORGCODE, EFFDATE, PROGRAMID, PRIMARYKEY, AUTHSL, RECSL, TABLENAME, DATABLOCK) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				for (AuthDataBlock block : record.getDataBlocks()) {
					jdbcTemplate.update(sql002, block.getOrgCode(), block.getEffDate(), block.getProgramId(),
							block.getPrimaryKey(), record.getAuthSl(), block.getRecSl(), block.getTableName(),
							block.getDataBlock());
				}
			}
			return;
		}
 
		String sql001 = "INSERT INTO AUTH001 (ORGCODE, PROGRAMID, DISPLAY_REMARKS, "
				+ "FLUSER, FLDATE, SLUSER, SLDATE, TLUSER, TLDATE, EUSER, EDATE) "
				+ "VALUES (?, ?, ?, '0', NULL, '0', NULL, '0', NULL, ?, CURRENT_TIMESTAMP) " + "RETURNING AUTHSL";
 
		Long authSl = jdbcTemplate.queryForObject(sql001, Long.class, record.getOrgCode(), record.getProgramId(),
				record.getDisplayRemarks(), record.getEntryUser());
 
		if (record.getDataBlocks() != null && authSl != null) {
			String sql002 = "INSERT INTO AUTH002 (ORGCODE, EFFDATE, PROGRAMID, PRIMARYKEY, AUTHSL, RECSL, TABLENAME, DATABLOCK) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			for (AuthDataBlock block : record.getDataBlocks()) {
				jdbcTemplate.update(sql002, block.getOrgCode(), block.getEffDate(), block.getProgramId(),
						block.getPrimaryKey(), authSl, block.getRecSl(), block.getTableName(), block.getDataBlock());
			}
		}
	}
 
	public void lockRecord(Long authSl) {
		try {
			System.out.println("🔒 Attempting to lock record: " + authSl);
			// Unlock all others
			int unlockedCount = jdbcTemplate.update("UPDATE AUTH001 SET AUTHLOCK = 0 WHERE AUTHSL != ?", authSl);
			// Lock this one
			int lockedCount = jdbcTemplate.update("UPDATE AUTH001 SET AUTHLOCK = 1 WHERE AUTHSL = ?", authSl);
			System.out.println("✅ Unlocked: " + unlockedCount + " records, Locked: " + lockedCount + " records.");
		} catch (Exception e) {
			System.err.println("❌ Error locking record " + authSl + ": " + e.getMessage());
			e.printStackTrace();
		}
	}
 
	public void processAuth(Long authSl, int level, String userId, int status, String remarks) {
		String sql = "CALL pr_process_approval(?, ?, ?, ?,?)";
		jdbcTemplate.update(sql, authSl, level, SecurityContextHolder.getContext().getAuthentication().getName(),
				status, remarks);
	}
 
	// --- AUTHCTL Configuration Methods ---
	public List<AuthConfigDTO> getAllAuthConfigs() {
		String sql101 = "SELECT * FROM AUTH101";
		return jdbcTemplate.query(sql101, (rs, rowNum) -> {
			AuthConfigDTO dto = new AuthConfigDTO();
			dto.setOrgCode(rs.getLong("ORGCODE"));
			dto.setProgramId(rs.getString("PROGRAMID"));
			dto.setApprovalReq(rs.getInt("APPROVALREQ"));
			dto.setPreApproveProc(rs.getInt("PRE_APPROVE_PROC"));
			dto.setPreExecMethod(rs.getString("PRE_EXEC_METHOD"));
			dto.setPreProcessName(rs.getString("PRE_PROCESSNAME"));
			dto.setPostApproveProc(rs.getInt("POST_APPROVE_PROC"));
			dto.setPostExecMethod(rs.getString("POST_EXEC_METHOD"));
			dto.setPostProcessName(rs.getString("POST_PROCESSNAME"));
			dto.setIsTranPgm(rs.getInt("ISTRANPGM"));
 
			String sql102 = "SELECT * FROM AUTH102 WHERE PROGRAMID = ?";
			List<AuthLevelDTO> levels = jdbcTemplate.query(sql102, (rs2, rowNum2) -> {
				AuthLevelDTO lvl = new AuthLevelDTO();
				lvl.setPermissiontype(rs2.getString("PERMISSIONTYPE"));
				lvl.setLevel(rs2.getInt("LEVELS"));
				lvl.setRolecd(rs2.getString("ROLECD"));
				lvl.setUserid(rs2.getString("USERID"));
				return lvl;
			}, dto.getProgramId());
			dto.setAuthLevels(levels);
			return dto;
		});
	}
 
	public void createAuthConfig(AuthConfigDTO dto) {
		String currentUser = "SYSTEM";
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		}
		System.out.println("📝 Creating Auth Config for: " + dto.getProgramId() + " (User: " + currentUser + ")");
 
		// Delete existing configurations for this program
		jdbcTemplate.update("DELETE FROM AUTH102 WHERE PROGRAMID = ?", dto.getProgramId());
		jdbcTemplate.update("DELETE FROM AUTH101 WHERE PROGRAMID = ?", dto.getProgramId());
 
		String sql101 = "INSERT INTO AUTH101 (ORGCODE, PROGRAMID, APPROVALREQ, PRE_APPROVE_PROC, PRE_EXEC_METHOD, "
				+ "PRE_PROCESSNAME, POST_APPROVE_PROC, POST_EXEC_METHOD, POST_PROCESSNAME, ISTRANPGM, EUSER, EDATE) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
 
		System.out.println("🚀 Saving Config -> ApprReq: " + dto.getApprovalReq() +
				", PreApp: " + dto.getPreApproveProc() +
				", PostApp: " + dto.getPostApproveProc() +
				", isTran: " + dto.getIsTranPgm() +
				" (User: " + currentUser + ")");
		jdbcTemplate.update(sql101, dto.getOrgCode(), dto.getProgramId(), dto.getApprovalReq(), dto.getPreApproveProc(),
				dto.getPreExecMethod(), dto.getPreProcessName(), dto.getPostApproveProc(), dto.getPostExecMethod(),
				dto.getPostProcessName(), dto.getIsTranPgm(), currentUser);
 
		if (dto.getAuthLevels() != null) {
			String sql102 = "INSERT INTO AUTH102 (ORGCODE, PROGRAMID, PERMISSIONTYPE, LEVELS, ROLECD, USERID, EUSER, EDATE) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
			System.out.println("🚀 Inserting into AUTH102 (" + dto.getAuthLevels().size() + " levels).");
			for (AuthLevelDTO level : dto.getAuthLevels()) {
				jdbcTemplate.update(sql102, dto.getOrgCode(), dto.getProgramId(), level.getPermissiontype(),
						level.getLevel(), level.getRolecd(), level.getUserid(), currentUser);
			}
		}
		System.out.println("✅ Auth Config creation complete.");
	}
 
	public void updateAuthConfig(AuthConfigDTO dto) {
 
		String currentUser = "SYSTEM";
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		}
 
		// 1️⃣ Update AUTH101
		String update101 = "UPDATE AUTH101 SET APPROVALREQ = ?, PRE_APPROVE_PROC = ?, PRE_EXEC_METHOD = ?, " +
				"PRE_PROCESSNAME = ?, POST_APPROVE_PROC = ?, POST_EXEC_METHOD = ?, POST_PROCESSNAME = ?, " +
				"ISTRANPGM = ?, EUSER = ?, EDATE = CURRENT_TIMESTAMP " +
				"WHERE PROGRAMID = ?";
 
		jdbcTemplate.update(update101,
				dto.getApprovalReq(),
				dto.getPreApproveProc(),
				dto.getPreExecMethod(),
				dto.getPreProcessName(),
				dto.getPostApproveProc(),
				dto.getPostExecMethod(),
				dto.getPostProcessName(),
				dto.getIsTranPgm(),
				currentUser,
				dto.getProgramId());
 
		// 2️⃣ Delete old AUTH102
		jdbcTemplate.update("DELETE FROM AUTH102 WHERE PROGRAMID = ?", dto.getProgramId());
 
		// 3️⃣ Insert new AUTH102
		if (dto.getAuthLevels() != null) {
			String insert102 = "INSERT INTO AUTH102 (ORGCODE, PROGRAMID, PERMISSIONTYPE, LEVELS, ROLECD, USERID, EUSER, EDATE) "
					+
					"VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
 
			for (AuthLevelDTO level : dto.getAuthLevels()) {
				jdbcTemplate.update(insert102,
						dto.getOrgCode(),
						dto.getProgramId(),
						level.getPermissiontype(),
						level.getLevel(),
						level.getRolecd(),
						level.getUserid(),
						currentUser);
			}
		}
 
		System.out.println("✅ Auth Config Updated Successfully");
	}
 
	public void deleteAuthConfig(String programId) {
 
		// 1️⃣ Delete AUTH102 (child)
		jdbcTemplate.update("DELETE FROM AUTH102 WHERE PROGRAMID = ?", programId);
 
		// 2️⃣ Delete AUTH101 (parent)
		jdbcTemplate.update("DELETE FROM AUTH101 WHERE PROGRAMID = ?", programId);
 
		System.out.println("🗑️ Auth Config Deleted Successfully for Program: " + programId);
	}
}