package com.dbms.insti.dao;

import java.util.HashMap;
import java.util.List;

import com.dbms.insti.models.Laundary_orders;

public interface LaundaryDao {
	public List<Laundary_orders>listOrdersofStudent(int roll_number, int washer_id, int status);
    public List<Laundary_orders>listAllOrdersofStudent(int roll_number, int status);
	public List<Laundary_orders>listOrdersofWasherman(int washer_id, int status);
    public void save(Laundary_orders order);
    public void delete(int order_id);
    public void edit(Laundary_orders order);
    public void editstatus(Laundary_orders order);
    public Laundary_orders getByOrderId(int order_id);
    public int duecharges(int roll_number, int washer_id);
    public List<List<Integer>> duechargesall(int washer_id);
    public int totalduecharges(int washer_id);

}
