package com.dbms.insti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.HostelDao;
import com.dbms.insti.models.Hostel;

@Service
public class HostelServiceUtil implements HostelService {
    @Autowired
    HostelDao hosteldao;
    @Override
    public List<Hostel> listAllHostels() {
        return hosteldao.listAllHostels();
    }

    @Override
    public void save(Hostel hostel) {
        hosteldao.save(hostel);
    }

}
