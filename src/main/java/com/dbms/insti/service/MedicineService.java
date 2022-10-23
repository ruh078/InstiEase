package com.dbms.insti.service;

import java.util.List;


import com.dbms.insti.models.Medicine;


public interface MedicineService {
    public List<Medicine> listAllMedicine();
 	public List<Medicine> listMedicine_lessStock();
 	public List<Medicine> listMedicinenotprescribed(int appointment_id);
    public void save(Medicine medicine);
    public Medicine getMedicinebyId(int medicine_id);
    public void updateStock(int medicine_id, int new_stock);
}
