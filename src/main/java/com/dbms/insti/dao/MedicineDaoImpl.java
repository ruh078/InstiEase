package com.dbms.insti.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.insti.models.Medicine;
@Repository
public class MedicineDaoImpl implements MedicineDao{
	@Autowired
    JdbcTemplate template;
    private RowMapper<Medicine> medicineRowMapper = new RowMapper<Medicine>() {
        @Override
        public Medicine mapRow(ResultSet rs, int rowNum) throws SQLException {

            Medicine medicine = new Medicine();
            medicine.setMedicine_id(rs.getInt("medicine_id"));
            medicine.setMedicine_name(rs.getString("medicine_name"));
            medicine.setQuantity_in_stock(rs.getInt("quantity_in_stock"));
            return medicine;
        }
    };
	@Override
	public List<Medicine> listAllMedicine() {
		String sql = "select * from medicine";
        List<Medicine> medicines = template.query(sql, medicineRowMapper);
        return medicines;
	}

	@Override
	public void save(Medicine medicine) {
		String sql = "insert into medicine(medicine_name, quantity_in_stock) values(?, ?)";
        template.update(sql, medicine.getMedicine_name(), medicine.getQuantity_in_stock());
	}

	@Override
	public Medicine getMedicinebyId(int medicine_id) {
		String sql = "select * from medicine where medicine_id=?";
        try {
            Medicine medicine = template.queryForObject(sql, medicineRowMapper, medicine_id);
            return medicine;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
       
	}

	@Override
	public void updateStock(int medicine_id, int new_stock) {
		String sql = "update medicine set quantity_in_stock=? where medicine_id=?";
		template.update(sql, new_stock, medicine_id);
		
	}

	@Override
	public List<Medicine> listMedicine_lessStock() {
		String sql = "select * from medicine where quantity_in_stock<10";
        List<Medicine> medicines = template.query(sql, medicineRowMapper);
        return medicines;	
	}

	@Override
	public List<Medicine> listMedicinenotprescribed(int appointment_id) {
		String sql = "select * from medicine where medicine_id not in (select med_id from prescription where appointment_id=?)";
		List<Medicine> medicines = template.query(sql, medicineRowMapper, appointment_id);
        return medicines;	
	}
}
