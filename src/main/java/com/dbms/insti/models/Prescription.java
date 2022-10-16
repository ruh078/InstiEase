package com.dbms.insti.models;

public class Prescription {
    private int appointment_id ;
    private int med_id;
    private int morning ;
    private int night ;
    private int afternoon ;
    private int number_of_days ;
    private int total_quantity_to_purchase ;
    public int getAppointment_id() {
        return appointment_id;
    }
    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }
    public int getMed_id() {
        return med_id;
    }
    public void setMed_id(int med_id) {
        this.med_id = med_id;
    }
    public int getMorning() {
        return morning;
    }
    public void setMorning(int morning) {
        this.morning = morning;
    }
    public int getNight() {
        return night;
    }
    public void setNight(int night) {
        this.night = night;
    }
    public int getAfternoon() {
        return afternoon;
    }
    public void setAfternoon(int afternoon) {
        this.afternoon = afternoon;
    }
    public int getNumber_of_days() {
        return number_of_days;
    }
    public void setNumber_of_days(int number_of_days) {
        this.number_of_days = number_of_days;
    }
    public int getTotal_quantity_to_purchase() {
        return total_quantity_to_purchase;
    }
    public void setTotal_quantity_to_purchase(int total_quantity_to_purchase) {
        this.total_quantity_to_purchase = total_quantity_to_purchase;
    }
}
