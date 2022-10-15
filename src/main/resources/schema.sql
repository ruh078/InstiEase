create table users (
	user_id int auto_increment not null,
	email_id varchar(255) not null,
	psw varchar(1000) not null,
	role int not null,
	name varchar(255) not null,
	contact varchar(12) not null,
	dob date not null,
	gender varchar(10) not null,
	primary key(user_id)
);

create table hostel (
	hostel_id int auto_increment not null,
	hostel_name varchar(255) not null,
	primary key(hostel_id)
);

create table student (
	roll_number int not null,
	room_number int not null,
	mess_refund int not null,
	bank_account_no varchar(255) not null,
	is_eligible_laundary int not null,
	due_wash_charges int not null,
	is_verified int not null,
	user_id int not null,
	hostel_id int not null,
	primary key (roll_number),
	foreign key (user_id) references users(user_id),
	foreign key (hostel_id) references hostel(hostel_id)
);

create table warden(
	warden_id int not null,
	hostel_id int not null,
	user_id int not null,
	primary key(warden_id),
	foreign key (user_id) references users(user_id),
	foreign key (hostel_id) references hostel(hostel_id)
);

create table mess_incharge(
	mess_id int not null,
	hostel_id int not null,
	user_id int not null,
	primary key(mess_id),
	foreign key (user_id) references users(user_id),
	foreign key (hostel_id) references hostel(hostel_id)
);

create table washerman(
	washer_id int not null,
	account_no varchar(30) not null,
	upi_id varchar(255) not null,
	cost_sheet_wash int not null,
	cost_sheet_iron int not null,
	cost_lower_wash int not null,
	cost_lower_iron int not null,
	cost_upper_wash int not null,
	cost_upper_iron int not null,
	total_money_earned int not null,
	hostel_id int not null,
	user_id int not null,
	primary key(washer_id),
	foreign key (user_id) references users(user_id),
	foreign key (hostel_id) references hostel(hostel_id)
);

create table complaints(
	complaint_id int auto_increment not null,
	student_roll_no int not null,
	description varchar(255) not null,
	type int not null,
	isPrivate int not null,
	status varchar(50) not null,
	primary key(complaint_id),
	foreign key (student_roll_no) references student(roll_number)
);

create table laundary_orders(
	order_id int auto_increment not null,
	ispaid int not null,
	cost int not null,
	order_date date not null,
	number_uppers int not null,
	number_sheets int not null,
	number_lowers int not null,
	to_wash int not null,
	to_iron int not null,
	status_of_orders varchar(50) not null,
	student_roll_no int not null,
	washer_id int not null,
	primary key(order_id),
	foreign key (student_roll_no) references student(roll_number),
	foreign key (washer_id) references washerman(washer_id)
);

create table cancel_mess(
	request_id int auto_increment not null,
	cancel_date date not null,
	is_breakfast int not null,
	is_lunch int not null,
	is_dinner int not null,
	student_roll_no int not null,
	primary key (request_id),
	foreign key (student_roll_no) references student(roll_number)
);

create table mess_charges(
	meal_type varchar(15) not null,
	cost int not null,
	primary key(meal_type)
);

create table day_menu(
	mess_id int not null,
	day_id int not null,
	breakfast varchar(255) not null,
	lunch varchar(255) not null,
	dinner varchar(255) not null,
	primary key(mess_id, day_id),
	foreign key(mess_id) references mess_incharge(mess_id)
);

create table appointment(
	appointment_id int auto_increment not null,
	student_roll_no int not null,
	appointment_date date not null,
	slot int not null,
	problem varchar(255),
	primary key(appointment_id),
	foreign key (student_roll_no) references student(roll_number)
);

create table medicine(
	medicine_id int auto_increment not null,
	medicine_name varchar(255) not null,
	quantity_in_stock varchar(255) not null,
	primary key(medicine_id)
);

create table prescription(
	appointment_id int not null,
	med_id int not null,
	morning int not null,
	night int not null,
	afternoon int not null,
	number_of_days int not null,
	total_quantity_to_purchase int not null,
	primary key(appointment_id, med_id),
	foreign key(appointment_id) references appointment(appointment_id),
	foreign key(med_id) references medicine(medicine_id)
);
