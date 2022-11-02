package com.dbms.insti.service;

import java.util.List;

import com.dbms.insti.models.Mess_incharge;

public interface MessService {
	public List<Mess_incharge> listAllMesses();
    public void save(Mess_incharge mess);
    public Mess_incharge findbyhostelid(int hostel_id);
    public Mess_incharge findbyuserid(int user_id);
    public int delete(int mess_id);
}
