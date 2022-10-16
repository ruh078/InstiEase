package com.dbms.insti.models;

public class Student {
    private int roll_number;
    private int room_number;
    private int mess_refund ;
    private String bank_account_no ;
    private int is_eligible_laundary ;
    private int due_wash_charges ;
    private int is_verified ;
    private int user_id ;
    private int hostel_id ;
    public int getRoll_number() {
        return roll_number;
    }
    public void setRoll_number(int roll_number) {
        this.roll_number = roll_number;
    }
    public int getRoom_number() {
        return room_number;
    }
    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }
    public int getMess_refund() {
        return mess_refund;
    }
    public void setMess_refund(int mess_refund) {
        this.mess_refund = mess_refund;
    }
    public String getBank_account_no() {
        return bank_account_no;
    }
    public void setBank_account_no(String bank_account_no) {
        this.bank_account_no = bank_account_no;
    }
    public int getIs_eligible_laundary() {
        return is_eligible_laundary;
    }
    public void setIs_eligible_laundary(int is_eligible_laundary) {
        this.is_eligible_laundary = is_eligible_laundary;
    }
    public int getDue_wash_charges() {
        return due_wash_charges;
    }
    public void setDue_wash_charges(int due_wash_charges) {
        this.due_wash_charges = due_wash_charges;
    }
    public int getIs_verified() {
        return is_verified;
    }
    public void setIs_verified(int is_verified) {
        this.is_verified = is_verified;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public int getHostel_id() {
        return hostel_id;
    }
    public void setHostel_id(int hostel_id) {
        this.hostel_id = hostel_id;
    }
}
