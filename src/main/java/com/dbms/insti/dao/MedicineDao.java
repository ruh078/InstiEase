package com.dbms.insti.dao;

import java.util.List;

import com.dbms.insti.models.Medicine;

public interface MedicineDao {
	 	public List<Medicine> listAllMedicine();
	 	public List<Medicine> listMedicine_lessStock();
	    public void save(Medicine medicine);
	    public void delete(int medicine_id);
	    public Medicine getMedicinebyId(int medicine_id);
	    public void updateStock(int medicine_id, int new_stock);
	    public List<Medicine> listMedicinenotprescribed(int appointment_id);
}
