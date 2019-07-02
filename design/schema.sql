drop table batch_job_customer;

create table batch_job_customer (
JOB_INSTANCE_ID bigint(20),
ORGANIZATION_ID varchar(100) NOT NULL,
primary key(JOB_INSTANCE_ID, ORGANIZATION_ID),
foreign key(JOB_INSTANCE_ID) references batch_job_instance(JOB_INSTANCE_ID),
foreign key(ORGANIZATION_ID) references organization(o_id)
);

create table organization (
    o_id varchar(100),
    name char(60) NOT NULL,
    email_address char(60) NOT NULL,
    contact_no char(30),
    primary key(o_id)
);

create table person (
    p_key mediumint,
    first_name char(20) NOT NULL,
    last_name char(40) NOT NULL,
    primary key(p_key)
);

create table customer (
    c_id mediumint, 
    employed_by varchar(100) NOT NULL,
    primary key(c_id),
    foreign key(c_id) references person(p_key),
    foreign key(employed_by) references organization(o_id)
);

create table employee (
    e_id mediumint,
    email_address char(60),
    birth_date Date NOT NULL,
    primary key(e_id),
    foreign key(e_id) references person(p_key)
);

create table account (
    a_id mediumint,
    password char(32),
    salt char(8),
    created datetime,
    acces_level varchar(20),
    owner mediumint,
    primary key(a_id),
    foreign key(owner) references person(p_key),
    unique(owner)
);

insert into organization
values  (12345, 'Under Armour', 'fake@armour.com', 75642),
        (21844, 'Adidas', 'fake@adidas.com', 12345),
        (22724, 'University of Twente','fake@utwente.com', 33333),
        (43092, 'Team23', 'team23@business.com', 99999),
        (88888, 'River Island', 'river@island.com', 89479),
        (90909, 'G-Star Raw', 'g_star@raw.com', 45678);


-- Jobs for Adidas
insert into batch_job_customer 
values  (4898794, 21844),
        (4691250, 21844),
	    (4691251, 21844),
	    (4691252, 21844),
        (4691253, 21844),
        (4691254, 21844),
        (4691255, 21844);

-- Jobs for University of Twente
insert into batch_job_customer 
values  (4764384, 22724),
        (4691652, 22724),
	    (4691653, 22724),
	    (4691654, 22724),
        (4691655, 22724),
        (4691656, 22724),
        (4691657, 22724),
        (4691658, 22724);

-- Job for Team23
insert into batch_job_customer 
values (4747245, 43092);


-- Jobs for Under Armour
insert into batch_job_customer 
values  (4898812, 12345),
        (4691748, 12345),
	    (4691749, 12345),
	    (4691750, 12345),
        (4691751, 12345),
        (4691752, 12345),
        (4691753, 12345),
        (4691754, 12345);

-- Jobs for River Island
insert into batch_job_customer
values  (5019459, 88888),
		(5019456, 88888),
        (5019439, 88888),
        (5019440, 88888),
        (5019174, 88888),
        (4796326, 88888),
        (4792083, 88888),
        (4792057, 88888),
        (4747242, 88888);


-- Jobs for G-Star Raw
insert into batch_job_customer
values  (5019197, 90909),
		(5019188, 90909),
        (5019439, 90909),
        (5019200, 90909),
        (5019465, 90909),
        (5019166, 90909),
        (4792054, 90909),
        (4792063, 90909),
        (4751502, 90909);

insert into person
values  (118824, 'Julian', 'Navarro'),
        (123456, 'Adarsh', 'Dont Know'),
        (419284, 'Abdullah', 'Qazi'),
        (999999, 'Luis', 'F. Pires'),
        (312034, 'Vishva', 'SR'),
        (101010, 'Lionel', 'Messi'),
        (832167, 'Peter', 'Pan'),
        (302178, 'Roberto', 'Carlos');


insert into employee
values  (118824, 'julian@fake.com', '1997-02-22'),
        (419284, 'abdulla@fake.com', '2000-10-20');



insert into customer
values (123456, 12345), -- Adarsh - Under Armour 
        (999999, 22724), -- Luis -  UT  
        (312034, 43092), -- Visva SR- Team23  
        (101010, 21844), -- Lionel M. - Adidas
        (302178, 88888), -- Roberto C. - River Island
        (832167, 90909); -- Peter Pan - G-Star Raw
      

insert into account
values  (1, 'password1', 'salt', '2019-06-03', 'technician', 118824),
        (2, 'password2', 'salt', '2019-06-03', 'support', 419284),
        (3, 'password3', 'salt', '2019-06-03', 'customer', 999999),
        (4, 'password4', 'salt', '2019-06-03', 'customer', 312034),
        (5, 'password5', 'salt', '2019-06-03', 'customer', 101010),
        (6, 'password6', 'salt', '2019-06-03', 'customer', 123456),
        (7, 'password7', 'salt', '2019-06-28', 'customer', 302178),
        (8, 'password8', 'salt', '2019-06-28', 'customer', 832167);




