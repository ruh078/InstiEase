package com.dbms.insti.service;

import java.util.List;

import com.dbms.insti.models.Mess_charges;

public interface MessChargesService {
    public Mess_charges getcharge(String s);
    public void edit(Mess_charges mess_charges);
    List<Mess_charges> listAllMesscharges();
}
