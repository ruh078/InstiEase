package com.dbms.insti.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.insti.models.Users;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class UserDaoImpl implements UserDao{
	@Autowired
	JdbcTemplate template;
	private RowMapper<Users> userRowMapper = new RowMapper<Users>() {
        @Override
        public Users mapRow(ResultSet rs, int rowNum) throws SQLException {

            Users user = new Users();
            user.setUser_id(rs.getInt("user_id"));
            user.setEmail_id(rs.getString("email_id"));
            user.setPsw(rs.getString("psw"));
            user.setRole(rs.getInt("role"));
            user.setName(rs.getString("name"));
            user.setContact(rs.getString("contact"));
            user.setDob(rs.getDate("dob"));
            user.setGender(rs.getString("gender"));
            return user;
        }
    };
	@Override
	public Users findByEmail(String email_id) {
		String query = "select * from users where email_id=?";
        try {
            Users user = template.queryForObject(query, userRowMapper, email_id);
            return user;
        } catch (EmptyResultDataAccessException e) {
        	return null;
        }
	}

	@Override
	public boolean userExists(String email_id) {
		try
		{
				String query = "select * from users where email_id=?";
				Users user = template.queryForObject(query, userRowMapper, email_id);
				return true;
				 
		}
			
		catch (EmptyResultDataAccessException e) {
	        return false;
	    }
	}

	@Override
	public void save(Users user) {
		String sql = "INSERT INTO users (email_id, psw, role, name, contact, dob, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, user.getEmail_id(), user.getPsw(), user.getRole(), user.getName(), user.getContact(), user.getDob(), user.getGender());
		
	}
	
	@Override
	public void edit(Users user) {
	    String sql = "UPDATE users SET name=?, contact=?, dob=?, gender=? where user_id=?";
	    template.update(sql, user.getName(), user.getContact(), user.getDob(), user.getGender(), user.getUser_id());
	}

	@Override
	public Users findByUserId(int user_id) {
		String query = "select * from users where user_id=?";
        try {
            Users user = template.queryForObject(query, userRowMapper, user_id);
            return user;
        } catch (EmptyResultDataAccessException e) {
        	return null;
        }
	}

    @Override
    public List<Users> ListAllUsers() {
        // TODO Auto-generated method stub
        String sql = "select * from Users";
        List<Users>users=template.query(sql,userRowMapper);
        return users;
    }

	@Override
	public List<Users> FindByRole(int role) {
		String query = "select * from users where role=?";
        try {
            List<Users> user = template.query(query, userRowMapper, role);
            return user;
        } catch (EmptyResultDataAccessException e) {
        	return null;
        }
	}

	@Override
	public void delete(int user_id) {
		// TODO Auto-generated method stub
		String query = "delete from users where user_id=?";
		template.update(query, user_id);
		
	}

	@Override
	public void changepassword(int user_id, String newpsw) {
		String query  = "UPDATE users SET psw=? where user_id=?";
		template.update(query, newpsw, user_id);
	}
	
}
