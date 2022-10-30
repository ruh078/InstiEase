package com.dbms.insti.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.insti.models.Laundary_orders;
@Repository
public class LaundaryDaoImpl implements LaundaryDao {
	
	@Autowired
    JdbcTemplate template;
    private RowMapper<Laundary_orders> laundaryRowMapper = new RowMapper<Laundary_orders>() {
        @Override
        public Laundary_orders mapRow(ResultSet rs, int rowNum) throws SQLException {

        	Laundary_orders order = new Laundary_orders();
            order.setOrder_id(rs.getInt("order_id"));
            order.setCost(rs.getInt("cost"));
            order.setNumber_lowers(rs.getInt("number_lowers"));
            order.setNumber_uppers(rs.getInt("number_uppers"));
            order.setNumber_sheets(rs.getInt("number_sheets"));
            order.setOrder_date(rs.getDate("order_date"));
            order.setTo_wash(rs.getInt("to_wash"));
            order.setTo_iron(rs.getInt("to_iron"));
            order.setStatus_of_orders(rs.getString("status_of_orders"));
            order.setStudent_roll_no(rs.getInt("student_roll_no"));
            order.setWasher_id(rs.getInt("washer_id"));
            return order;
        }
    };

	@Override
	public List<Laundary_orders> listOrdersofStudent(int roll_number, int washer_id, int status) {
		String sql = "";
		if(status==0)
			sql="select * from laundary_orders where student_roll_no=? and washer_id=? order by order_date desc";
		else if(status==1)
			sql="select * from laundary_orders where student_roll_no=? and washer_id=? and status_of_orders='Unwashed' order by order_date desc";
		else if(status==2)
			sql="select * from laundary_orders where student_roll_no=? and washer_id=? and status_of_orders='Washed and Unpaid' order by order_date desc";
		else
			sql="select * from laundary_orders where student_roll_no=? and washer_id=? and status_of_orders='Washed and Paid' order by order_date desc";
		List<Laundary_orders> orders = template.query(sql, laundaryRowMapper, roll_number, washer_id);
        return orders;
		
	}

	@Override
	public List<Laundary_orders> listAllOrdersofStudent(int roll_number, int status) {
		String sql = "";
		if(status==0)
			sql="select * from laundary_orders where student_roll_no=? order by order_date desc";
		else if(status==1)
			sql="select * from laundary_orders where student_roll_no=? and status_of_orders='Unwashed' order by order_date desc";
		else if(status==2)
			sql="select * from laundary_orders where student_roll_no=? and status_of_orders='Washed and Unpaid' order by order_date desc";
		else
			sql="select * from laundary_orders where student_roll_no=? and status_of_orders='Washed and Paid' order by order_date desc";
		List<Laundary_orders> orders = template.query(sql, laundaryRowMapper, roll_number);
        return orders;
	}

	@Override
	public void save(Laundary_orders order) {
		String sql = "insert into laundary_orders(cost, order_date, number_uppers, number_lowers, number_sheets, to_wash, to_iron, status_of_orders, student_roll_no, washer_id) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		template.update(sql, order.getCost(), order.getOrder_date(), order.getNumber_uppers(), order.getNumber_lowers(), order.getNumber_sheets(), order.getTo_wash(), order.getTo_iron(), "Unwashed", order.getStudent_roll_no(), order.getWasher_id());
	}

	@Override
	public void delete(int order_id) {
		String sql = "delete from laundary_orders where order_id=?";
		template.update(sql, order_id);
	}

	@Override
	public Laundary_orders getByOrderId(int order_id) {
		String sql = "select * from laundary_orders where order_id=?";
		return template.queryForObject(sql, laundaryRowMapper, order_id);
	}

	@Override
	public void edit(Laundary_orders order) {
		String sql = "update laundary_orders set cost=?, number_uppers=?, number_lowers=?, number_sheets=?, to_wash=?, to_iron=? where order_id=?";
		template.update(sql, order.getCost(), order.getNumber_uppers(), order.getNumber_lowers(), order.getNumber_sheets(), order.getTo_wash(), order.getTo_iron(), order.getOrder_id());

	}

	@Override
	public int duecharges(int roll_number, int washer_id) {
		String sql = "select sum(cost) from laundary_orders where washer_id=? and student_roll_no=? and status_of_orders='Washed and Unpaid'";
		try {
			return template.queryForObject(sql,Integer.class, washer_id, roll_number);
		}
		catch(Exception e) {
			return 0;
		}
	}

	@Override
	public List<Laundary_orders> listOrdersofWasherman(int washer_id, int status) {
		String sql = "";
		if(status==0)
			sql="select * from laundary_orders where washer_id=? order by order_date desc";
		else if(status==1)
			sql="select * from laundary_orders where washer_id=? and status_of_orders='Unwashed' order by order_date desc";
		else if(status==2)
			sql="select * from laundary_orders where washer_id=? and status_of_orders='Washed and Unpaid' order by order_date desc";
		else
			sql="select * from laundary_orders where washer_id=? and status_of_orders='Washed and Paid' order by order_date desc";
		List<Laundary_orders> orders = template.query(sql, laundaryRowMapper, washer_id);
        return orders;
	}

	@Override
	public void editstatus(Laundary_orders order) {
		String sql = "update laundary_orders set status_of_orders=? where order_id=?";
		template.update(sql,  order.getStatus_of_orders(), order.getOrder_id());
	}

	@Override
	public List<List<Integer>> duechargesall(int washer_id) {
		
		String sql = "select student_roll_no, sum(cost) as sum from laundary_orders where washer_id=? and status_of_orders='Washed and Unpaid' group by student_roll_no";
		List<List<Integer>> l = template.query(sql, (rs, rowNum) -> {
		    List<Integer> results = new ArrayList();
		    results.add(rs.getInt("student_roll_no"));
		    results.add(rs.getInt("sum"));
		    return results;
		}, washer_id
		);
		return l;
	}

	@Override
	public int totalduecharges(int washer_id) {
		String sql = "select sum(cost) from laundary_orders where washer_id=? and status_of_orders='Washed and Unpaid'";
		try {
			return template.queryForObject(sql,Integer.class, washer_id);
		}
		catch(Exception e) {
			return 0;
		}
	}

	
}
