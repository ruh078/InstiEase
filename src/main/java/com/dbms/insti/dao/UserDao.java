package com.dbms.insti.dao;
import java.util.List;

import com.dbms.insti.models.Users;
public interface UserDao {
	public Users findByEmail(String email_id);
    public boolean userExists(String email_id);
    public void save(Users user);
    public Users findByUserId(int user_id);
    public List<Users>ListAllUsers();
}
