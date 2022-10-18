package com.dbms.insti.dao;

import java.util.List;

import com.dbms.insti.models.Mess_incharge;

public interface Mess_inchargeDao {
	public List<Mess_incharge> listAllMesses();
    public void save(Mess_incharge mess);
    public Mess_incharge findbyhostelid(int hostel_id);
}
