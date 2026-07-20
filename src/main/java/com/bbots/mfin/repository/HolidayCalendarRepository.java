package com.bbots.mfin.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.HolidayCalendarDTO;

@Repository
public class HolidayCalendarRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<HolidayCalendarDTO> rowMapper = (rs, rowNum) -> {

        HolidayCalendarDTO holiday = new HolidayCalendarDTO();

        holiday.setOrgcode(rs.getLong("orgcode"));
        holiday.setBranch_code(rs.getString("branch_code"));
        holiday.setHoliday_date(rs.getDate("holiday_date") == null ? null : rs.getDate("holiday_date").toString());
        holiday.setHoliday_name(rs.getString("holiday_name"));
        holiday.setHoliday_type(rs.getString("holiday_type"));
        holiday.setDue_date_shift_rule(rs.getString("due_date_shift_rule"));
        holiday.setCalendar_status(rs.getString("calendar_status"));

        holiday.setEuser(rs.getString("euser"));
        holiday.setEdate(rs.getDate("edate") == null ? null : rs.getDate("edate").toString());
        holiday.setAuser(rs.getString("auser"));
        holiday.setAdate(rs.getDate("adate") == null ? null : rs.getDate("adate").toString());
        holiday.setCuser(rs.getString("cuser"));
        holiday.setCdate(rs.getDate("cdate") == null ? null : rs.getDate("cdate").toString());

        return holiday;
    };

    public List<HolidayCalendarDTO> findAll() {

        String sql = "SELECT * FROM loandev.CAL001";

        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<HolidayCalendarDTO> findByIdOrgCode(Long orgCode) {

        String sql = "SELECT * FROM loandev.CAL001 WHERE orgcode=?";

        return jdbcTemplate.query(sql, rowMapper, orgCode);
    }

    public Optional<HolidayCalendarDTO> findById(Long orgCode,
                                                 String branchCode,
                                                 String holidayDate) {

        String sql = "SELECT * FROM loandev.CAL001 "
                   + "WHERE orgcode=? "
                   + "AND branch_code=? "
                   + "AND holiday_date=?";

        List<HolidayCalendarDTO> results =
                jdbcTemplate.query(
                        sql,
                        rowMapper,
                        orgCode,
                        branchCode,
                        Date.valueOf(holidayDate));

        return results.stream().findFirst();
    }

    public boolean existsById(Long orgCode,
                              String branchCode,
                              String holidayDate) {

        String sql = "SELECT COUNT(*) FROM loandev.CAL001 "
                   + "WHERE orgcode=? "
                   + "AND branch_code=? "
                   + "AND holiday_date=?";

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                orgCode,
                branchCode,
                Date.valueOf(holidayDate));

        return count != null && count > 0;
    }
    
    
    public HolidayCalendarDTO save(HolidayCalendarDTO holiday) {

        if (existsById(
                holiday.getOrgcode(),
                holiday.getBranch_code(),
                holiday.getHoliday_date())) {

            String sql = "UPDATE loandev.CAL001 SET "
                    + "holiday_name=?, "
                    + "holiday_type=?, "
                    + "due_date_shift_rule=?, "
                    + "calendar_status=?, "
                    + "euser=?, "
                    + "edate=?, "
                    + "auser=?, "
                    + "adate=?, "
                    + "cuser=?, "
                    + "cdate=? "
                    + "WHERE orgcode=? "
                    + "AND branch_code=? "
                    + "AND holiday_date=?";

            jdbcTemplate.update(
                    sql,
                    holiday.getHoliday_name(),
                    holiday.getHoliday_type(),
                    holiday.getDue_date_shift_rule(),
                    holiday.getCalendar_status(),
                    holiday.getEuser(),
                    holiday.getEdate() != null ? Date.valueOf(holiday.getEdate()) : null,
                    holiday.getAuser(),
                    holiday.getAdate() != null ? Date.valueOf(holiday.getAdate()) : null,
                    holiday.getCuser(),
                    holiday.getCdate() != null ? Date.valueOf(holiday.getCdate()) : null,
                    holiday.getOrgcode(),
                    holiday.getBranch_code(),
                    Date.valueOf(holiday.getHoliday_date()));

        } else {

            String sql = "INSERT INTO loandev.CAL001 ("
                    + "orgcode,"
                    + "branch_code,"
                    + "holiday_date,"
                    + "holiday_name,"
                    + "holiday_type,"
                    + "due_date_shift_rule,"
                    + "calendar_status,"
                    + "euser,"
                    + "edate,"
                    + "auser,"
                    + "adate,"
                    + "cuser,"
                    + "cdate"
                    + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

            jdbcTemplate.update(
                    sql,
                    holiday.getOrgcode(),
                    holiday.getBranch_code(),
                    Date.valueOf(holiday.getHoliday_date()),
                    holiday.getHoliday_name(),
                    holiday.getHoliday_type(),
                    holiday.getDue_date_shift_rule(),
                    holiday.getCalendar_status(),
                    holiday.getEuser(),
                    holiday.getEdate() != null ? Date.valueOf(holiday.getEdate()) : null,
                    holiday.getAuser(),
                    holiday.getAdate() != null ? Date.valueOf(holiday.getAdate()) : null,
                    holiday.getCuser(),
                    holiday.getCdate() != null ? Date.valueOf(holiday.getCdate()) : null);
        }

        return holiday;
    }

}