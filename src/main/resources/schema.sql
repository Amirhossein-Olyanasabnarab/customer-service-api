create table customer(
    id BIGINT AUTO_INCREMENT  primary key,
    full_name varchar(100) not null ,
    phone_number varchar(20) not null ,
    email varchar(50),
    type varchar(10) not null
);

create table real_customer(
    id bigint primary key ,
    nationality varchar(20) not null ,
    age varchar(5) not null ,
    foreign key (id) references customer(id) on delete cascade
);

create table legal_customer(
    id bigint primary key ,
    company_name varchar(20) not null ,
    industry varchar(20),
    foreign key (id) references customer(id) on delete cascade
);