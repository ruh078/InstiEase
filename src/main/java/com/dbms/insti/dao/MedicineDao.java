package com.dbms.insti.dao;

import java.util.List;

import com.dbms.insti.models.Medicine;

public interface MedicineDao {
	 	public List<Medicine> listAllMedicine();
	    public void save(Medicine medicine);
	    public Medicine getMedicinebyId(int medicine_id);
}
