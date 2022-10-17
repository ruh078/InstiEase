package com.dbms.insti.service;

import java.util.List;

import com.dbms.insti.models.Washerman;

public interface WashermanService {
    public List<Washerman> listAllWasherman();
    public void save(Washerman washerman);
}
