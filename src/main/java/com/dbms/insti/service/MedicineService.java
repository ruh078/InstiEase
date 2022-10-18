package com.dbms.insti.service;

import java.util.List;


import com.dbms.insti.models.Medicine;


public interface MedicineService {
    public List<Medicine> listAllMedicine();
    public void save(Medicine medicine);
    public Medicine getMedicinebyId(int medicine_id);
}
