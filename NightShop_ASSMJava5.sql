create database NightShop_ASSJava5
go

create table account(
	id int primary key Identity not null,
	fullname nvarchar(100) not null,
	username varchar(50) not null,
	password varchar(50) not null,
	email varchar(100) not null,
	dia_chi nvarchar(200) null, 
	sdt varchar(15) null,
	images varchar(1000) null,
	is_active bit not null default 1,
	is_admin bit not null default 1
)

create table color(
	id int primary key Identity not null,
	color_name nvarchar(50) not null,
	is_active bit not null default 1
)



create table size(
	id int primary key Identity not null,
	size_name nvarchar(50) not null,
	is_active bit not null default 1
)



create table category(
	id int primary key Identity not null,
	categorye_name nvarchar(50) not null,
	is_active bit not null default 1
)
ALTER TABLE category ADD images varchar(1000) 


create table Discount (
	id int primary key Identity not null,
	name_discount varchar(50) not null,	
	start_day date not null,
	end_date date not null,
	percent_discount int not null,
	discount_conditions int not null,
	is_active bit not null default 1
)


CREATE TABLE product (
    id int primary key identity not null,
    product_name nvarchar(250) not null,
    import_price money not null,
    price money not null,
    quantity int not null,
    description nvarchar(1000) not null,
    is_active bit not null,
    iddanhmmuc int,
    foreign key (idDanhMuc) references category(id)
);


create table product_size(
	id int primary key Identity not null,
	idProduct int foreign key references product(id),
	idSize int foreign key references size(id)
)


create table product_color(
	id int primary key Identity not null,
	idProduct int foreign key references product(id),
	idColor int foreign key references color(id)
)


create table cart(
	id int primary key Identity not null,
	idAccount int foreign key references account(id) not null,
	idProduct int foreign key references product(id) not null,
	idColor int foreign key references color(id) not null,
	idSize int foreign key references size(id) not null,
	quantity int not null
)


create table favorite(
	id int primary key Identity not null,
	idAccount int foreign key references account(id),
	idProduct int foreign key references product(id),
	idColor int foreign key references color(id),
	idSize int foreign key references size(id)
)

create table bill(
	id UNIQUEIDENTIFIER primary key  DEFAULT NEWID() not  null,
	recipient_name nvarchar(100) not null,
	sdt_recipient varchar(20) not null,
	email varchar(150) not null,
	addres nvarchar(500) not null,
	date_created date not null,
	delivery_date date null,
	received_date date null,
	discount_amount money not null,
	shipping_fee money not null,
	total_money money not null,
	into_money money not null,
	is_active int not null,
	id_account int foreign key references account(id),
	id_discount int foreign key references discount(id)
)

create table detailed_invoice(
		id UNIQUEIDENTIFIER primary key  DEFAULT NEWID() not  null,
		quatity int not null,
		unit_price money not null,
		total_money money not null,
		is_active int not null,
		id_bill UNIQUEIDENTIFIER foreign key references bill(id),
		id_product int foreign key references product(id),
		id_color int foreign key references color(id),
		id_size int foreign key references size(id)
	)
		
