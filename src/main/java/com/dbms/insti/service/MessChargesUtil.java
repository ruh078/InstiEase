package com.dbms.insti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.MessChargesDao;
import com.dbms.insti.models.Mess_charges;

@Service
public class MessChargesUtil implements MessChargesService{
    
    @Autowired
    MessChargesDao messchargesdao;
    
    @Override
    public Mess_charges getcharge(String s) {
        return messchargesdao.getcharge(s);
    }
    
    @Override
    public List<Mess_charges> listAllMesscharges(){
        return messchargesdao.listAllMesscharges();
    }
    
    @Override
    public void edit(Mess_charges mess_charges) {
        messchargesdao.edit(mess_charges);
        return;
    }
}
