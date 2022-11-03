package com.dbms.insti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.HostelDao;
import com.dbms.insti.dao.Mess_inchargeDao;
import com.dbms.insti.dao.StudentDao;
import com.dbms.insti.dao.WardenDao;
import com.dbms.insti.dao.WashermanDao;
import com.dbms.insti.models.Hostel;
import com.dbms.insti.models.Mess_incharge;
import com.dbms.insti.models.Student;
import com.dbms.insti.models.Warden;
import com.dbms.insti.models.Washerman;

@Service
public class HostelServiceUtil implements HostelService {
    @Autowired
    HostelDao hosteldao;
    @Autowired
    StudentDao studentdao;
    @Autowired
    WashermanDao washermandao;
    @Autowired
    WardenDao wardendao;
    @Autowired
    Mess_inchargeDao messdao;
    
    @Override
    public List<Hostel> listAllHostels() {
        return hosteldao.listAllHostels();
    }

    @Override
    public void save(Hostel hostel) {
        hosteldao.save(hostel);
    }

    @Override
    public Hostel getHostelbyId(int hostel_id) {
        return hosteldao.getHostelbyId(hostel_id);
    }

	@Override
	public int delete(int hostel_id) {
		List<Student>students = studentdao.listAllStudentsofHostel(hostel_id);
		List<Washerman>washers = washermandao.listAllWashermanofHostel(hostel_id);
		Warden warden = wardendao.findbyHostelId(hostel_id);
		Mess_incharge mess = messdao.findbyhostelid(hostel_id);
		if(students.isEmpty() && washers.isEmpty() && warden==null && mess==null) {
			hosteldao.delete(hostel_id);
			return 1;
		}
		return 0;
	}

}
