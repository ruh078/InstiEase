package com.dbms.insti.service;

import java.util.List;

import com.dbms.insti.models.Laundary_orders;

public interface LaundaryService {
	public List<Laundary_orders>listOrdersofStudent(int roll_number, int washer_id, int status);
    public List<Laundary_orders>listAllOrdersofStudent(int roll_number, int status);
	public List<Laundary_orders>listOrdersofWasherman(int washer_id, int status);
    public void save(Laundary_orders order);
    public int delete(int order_id);
    public int edit(Laundary_orders order);
    public void editstatus(Laundary_orders order);
    public int duecharges(int roll_number, int washer_id);
    public Laundary_orders getByOrderId(int order_id);
    public List<List<Integer>> duechargesall(int washer_id);
    public int totalduecharges(int washer_id);
    public void cleardues(int roll_number, int washer_id);
}
