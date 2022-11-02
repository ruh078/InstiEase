package com.dbms.insti.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.insti.models.Prescription;
@Repository
public class PrescriptionDaoImpl implements PrescriptionDao{
	@Autowired
    JdbcTemplate template;
    private RowMapper<Prescription> prescriptionRowMapper = new RowMapper<Prescription>() {
        @Override
        public Prescription mapRow(ResultSet rs, int rowNum) throws SQLException {

            Prescription prescription = new Prescription();
            prescription.setAppointment_id(rs.getInt("appointment_id"));
            prescription.setMed_id(rs.getInt("med_id"));
            prescription.setMorning(rs.getInt("morning"));
            prescription.setAfternoon(rs.getInt("afternoon"));
            prescription.setNight(rs.getInt("night"));
            prescription.setNumber_of_days(rs.getInt("number_of_days"));
            prescription.setTotal_quantity_to_purchase(rs.getInt("total_quantity_to_purchase"));
            return prescription;
        }
    };
	
	
	@Override
	public void save(Prescription prescription) {
		String sql = "insert into prescription(appointment_id, med_id, morning, afternoon, night, number_of_days, total_quantity_to_purchase) values(?, ?, ?, ?, ?, ?, ?)";
		template.update(sql, prescription.getAppointment_id(), prescription.getMed_id(), prescription.getMorning(), prescription.getAfternoon(), prescription.getNight(), prescription.getNumber_of_days(), prescription.getTotal_quantity_to_purchase());
	}


	@Override
	public List<Prescription> getPrescriptionsOfAppointment(int appointment_id) {
		String sql = "select * from prescription where appointment_id=?";
		List<Prescription>prescription = template.query(sql, prescriptionRowMapper, appointment_id);
		return prescription;
	}


	@Override
	public Prescription findPrescription(int appointment_id, int med_id) {
		String sql = "select * from prescription where appointment_id=? and med_id=?";
		try {
			return template.queryForObject(sql, prescriptionRowMapper, appointment_id, med_id);
		} catch (EmptyResultDataAccessException e) {
            return null;
        }
	}


	@Override
	public void update(Prescription prescription) {
		String sql = "update prescription set morning=?, afternoon=?, night=?, number_of_days=?, total_quantity_to_purchase=? where appointment_id=? and med_id=?";
		template.update(sql, prescription.getMorning(), prescription.getAfternoon(), prescription.getNight(), prescription.getNumber_of_days(), prescription.getTotal_quantity_to_purchase(), prescription.getAppointment_id(), prescription.getMed_id());
	}


	@Override
	public int delete(int appointment_id, int med_id) {
		int f=0;
		String sql = "delete from prescription where appointment_id=? and med_id=?";
		try {
			template.update(sql, appointment_id, med_id);
			f= 1;
		}
		catch(Exception e){
			f=0;
		}
		return f;
	}


	@Override
	public List<Prescription> getPrescriptionsOfMedicine(int medicine_id) {
		String sql = "select * from prescription where med_id=?";
		List<Prescription>prescription = template.query(sql, prescriptionRowMapper, medicine_id);
		return prescription;
	}

}
