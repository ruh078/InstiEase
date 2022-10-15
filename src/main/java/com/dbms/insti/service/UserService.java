package com.dbms.insti.service;
import com.dbms.insti.models.Users;
public interface UserService {
	public void save(Users user);
    public Users findByEmail(String email_id);
    public boolean userExists(String email_id);
    public Users findByUserId(int user_id);
}
