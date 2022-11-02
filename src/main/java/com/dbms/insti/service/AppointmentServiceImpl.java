package com.dbms.insti.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.AppointmentDao;
import com.dbms.insti.dao.PrescriptionDao;
import com.dbms.insti.models.Appointment;
@Service
public class AppointmentServiceImpl implements AppointmentService{
	@Autowired
	AppointmentDao appointmentdao;
	@Autowired 
	PrescriptionDao prescriptiondao;
	
	@Override
	public List<Appointment> listCurrentAppointments() {
		return appointmentdao.listCurrentAppointments();
	}

	@Override
	public List<Appointment> listPreviousAppointments() {
		return appointmentdao.listPreviousAppointments();
	}

	@Override
	public List<Appointment> listAppointmentsStudent(int student_roll_no) {
		return appointmentdao.listAppointmentsStudent(student_roll_no);
	}

	@Override
	public int save(Appointment appointment) {
		LocalTime time = LocalTime.now();
		LocalTime noon = LocalTime.NOON;
		LocalDate date = LocalDate.now();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  	    Date today = new Date();
 		   Date todayWithZeroTime=new Date();
 		   try {
 			   todayWithZeroTime = formatter.parse(formatter.format(today));
 		   } catch (ParseException e) {
 			e.printStackTrace();
 		   }
 		 if(appointment.getAppointment_date().compareTo(todayWithZeroTime)<0)
 			 return 0;
 
		if(appointment.getAppointment_date().compareTo(todayWithZeroTime)!=0 || appointment.getSlot()==2 || noon.compareTo(time)>0) {
			appointmentdao.save(appointment);
			return 1;
		}
		return 0;

	}

	@Override
	public int edit(Appointment appointment) {
		LocalTime time = LocalTime.now();
		LocalTime noon = LocalTime.NOON;
		LocalDate date = LocalDate.now();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  	    Date today = new Date();
 		   Date todayWithZeroTime=new Date();
 		   try {
 			   todayWithZeroTime = formatter.parse(formatter.format(today));
 		   } catch (ParseException e) {
 			e.printStackTrace();
 		   }
 		if(appointment.getAppointment_date().compareTo(todayWithZeroTime)<0)
 			 return 0;
 		
 		if(!prescriptiondao.getPrescriptionsOfAppointment(appointment.getAppointment_id()).isEmpty())
 			return 0;
 
		if(appointment.getAppointment_date().compareTo(todayWithZeroTime)!=0 || appointment.getSlot()==2 || noon.compareTo(time)>0) {
			appointmentdao.edit(appointment);
			return 1;
		}
		return 0;
	}
	
	@Override
	public Appointment getAppointmentbyId(int appointment_id) {
		return appointmentdao.getAppointmentbyId(appointment_id);
	}

	@Override
	public void updateAppointmentDesc(int appointment_id, String desc) {
		appointmentdao.updateAppointmentDesc(appointment_id, desc);
		
	}

	@Override
	public List<Appointment> listCurrentAppointmentsStudent(int roll_number) {
		return appointmentdao.listCurrentAppointmentsStudent(roll_number);
	}

	@Override
	public List<Appointment> listPreviousAppointmentsStudent(int roll_number) {
		return appointmentdao.listPreviousAppointmentsStudent(roll_number);
	}

	@Override
	public int delete(int appointment_id) {
		Appointment appointment = appointmentdao.getAppointmentbyId(appointment_id);
		LocalTime time = LocalTime.now();
		LocalTime noon = LocalTime.NOON;
		LocalDate date = LocalDate.now();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  	    Date today = new Date();
 		   Date todayWithZeroTime=new Date();
 		   try {
 			   todayWithZeroTime = formatter.parse(formatter.format(today));
 		   } catch (ParseException e) {
 			e.printStackTrace();
 		   }
		if(appointment.getAppointment_date().compareTo(todayWithZeroTime)<0)
			 return 0;
		if(!prescriptiondao.getPrescriptionsOfAppointment(appointment.getAppointment_id()).isEmpty())
 			return 0;
		
		if(appointment.getAppointment_date().compareTo(todayWithZeroTime)!=0 || appointment.getSlot()==2 || noon.compareTo(time)>0) {
			appointmentdao.delete(appointment_id);
			return 1;
		}
		
		return 0;
	}

	

}
