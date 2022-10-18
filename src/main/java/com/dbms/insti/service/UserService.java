package com.dbms.insti.service;
import java.util.List;


import com.dbms.insti.models.Users;
public interface UserService {
	public int save(Users user);
    public Users findByEmail(String email_id);
    public boolean userExists(String email_id);
    public Users findByUserId(int user_id);
    public List<Users> listAllUsers();
    public List<Users>FindByRole(int role);
}
