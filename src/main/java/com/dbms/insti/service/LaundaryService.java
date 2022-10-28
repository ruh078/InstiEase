package com.dbms.insti.service;

import java.util.List;

import com.dbms.insti.models.Laundary_orders;

public interface LaundaryService {
	public List<Laundary_orders>listOrdersofStudent(int roll_number, int washer_id, int status);
    public List<Laundary_orders>listAllOrdersofStudent(int roll_number, int status);
    public void save(Laundary_orders order);

}
