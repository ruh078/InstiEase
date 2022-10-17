package com.dbms.insti.service;

import java.util.List;

import com.dbms.insti.models.Mess_incharge;

public interface MessService {
	public List<Mess_incharge> listAllMesses();
    public void save(Mess_incharge mess);
}
