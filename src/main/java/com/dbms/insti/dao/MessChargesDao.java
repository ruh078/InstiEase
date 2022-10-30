package com.dbms.insti.dao;

import java.util.List;

import com.dbms.insti.models.Mess_charges;

public interface MessChargesDao {
    public List<Mess_charges> listAllMesscharges();
	public Mess_charges getcharge(String s);
    public void edit(Mess_charges mess_charges);
}
