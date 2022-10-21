package com.dbms.insti.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.insti.models.Appointment;



@Repository
public class AppointmentDaoImpl implements AppointmentDao{
	@Autowired
    JdbcTemplate template;
    private RowMapper<Appointment> appointmentRowMapper = new RowMapper<Appointment>() {
        @Override
        public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {

            Appointment appointment = new Appointment();
            appointment.setAppointment_id(rs.getInt("appointment_id"));
            appointment.setStudent_roll_no(rs.getInt("student_roll_no"));
            appointment.setSlot(rs.getInt("slot"));
            appointment.setProblem(rs.getString("problem"));
            appointment.setAppointment_date(rs.getDate("appointment_date"));
            return appointment;
        }
    };
	@Override
	public List<Appointment> listCurrentAppointments() {
		Date date = new Date();
		String sql = "select * from appointment where appointment_date>=?";
        List<Appointment> appointments = template.query(sql, appointmentRowMapper, date);
        return appointments;
	}

	@Override
	public List<Appointment> listPreviousAppointments() {
		Date date = new Date();
		String sql = "select * from appointment where appointment_date<?";
        List<Appointment> appointments = template.query(sql, appointmentRowMapper, date);
        return appointments;
	}

	@Override
	public List<Appointment> listAppointmentsStudent(int student_roll_no) {
		String sql = "select * from appointment where student_roll_no=?";
        List<Appointment> appointments = template.query(sql, appointmentRowMapper, student_roll_no);
        return appointments;
	}

	@Override
	public void save(Appointment appointment) {
		String sql = "insert into appointment(student_roll_no, appointment_date, slot, problem) values(?, ?, ?, ?)";
        template.update(sql, appointment.getStudent_roll_no(), appointment.getAppointment_date(), appointment.getSlot(), appointment.getProblem());
	}

	@Override
	public Appointment getAppointmentbyId(int appointment_id) {
		String query = "select * from appointment where appointment_id=?";
        try {
            Appointment appointment = template.queryForObject(query, appointmentRowMapper, appointment_id);
            return appointment;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	@Override
	public void updateAppointmentDesc(int appointment_id, String desc) {
		String sql = "update appointment set problem=? where appointment_id=?";
		template.update(sql, desc, appointment_id);
	}

}
