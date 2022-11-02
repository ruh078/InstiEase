package com.dbms.insti.dao;
import java.util.List;

import com.dbms.insti.models.Users;
public interface UserDao {
	public Users findByEmail(String email_id);
    public boolean userExists(String email_id);
    public void save(Users user);
    public void edit(Users user);
    public void delete(int user_id);
    public Users findByUserId(int user_id);
    public List<Users>ListAllUsers();
    public List<Users>FindByRole(int role);
    public void changepassword(int user_id, String newpsw);
}
