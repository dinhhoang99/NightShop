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

select * from account
insert into Account(fullname,username,password,email) values
(N'Hoàng','hoang123','123','dinhhoang123@gmail.com')	
select * from Account
update account set email = 'abc@gmail.com' where id = 3

create table color(
	id int primary key Identity not null,
	color_name nvarchar(50) not null,
	is_active bit not null default 1
)

insert into color(color_name) values
(N'Đen'),(N'Trắng')
select * from color

create table size(
	id int primary key Identity not null,
	size_name nvarchar(50) not null,
	is_active bit not null default 1
)

insert into size(size_name) values
('S'),('M'),('L'),('XL')
select * from size
delete size where id = 6

create table category(
	id int primary key Identity not null,
	categorye_name nvarchar(50) not null,
	is_active bit not null default 1
)
ALTER TABLE category ADD images varchar(1000) 

select * from category
insert into category(categorye_name) values
(N'Thời Trang Nam')

create table Discount (
	id int primary key Identity not null,
	name_discount varchar(50) not null,	
	start_day date not null,
	end_date date not null,
	percent_discount int not null,
	discount_conditions int not null,
	is_active bit not null default 1
)
delete Discount where id = 6
insert into Discount(name_discount,start_day,end_date,percent_discount,discount_conditions,is_active) values
('VC50','2023-01-20','2023-09-23',500,10,1)
('VC0','2023-01-20','2023-09-23',10,0,1)
select * from Discount

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
ALTER TABLE product ADD images varchar(1000) not null 
select * from product
update product set quantity = 10 where id = 4
delete from product where description = 'abc'
ALTER TABLE product
RENAME COLUMN idDanhMuc TO id_category;

DELETE FROM product_color WHERE id_product = '8';
DELETE FROM product_size WHERE id_product = '8';
DELETE FROM product WHERE id = '8';


create table product_size(
	id int primary key Identity not null,
	idProduct int foreign key references product(id),
	idSize int foreign key references size(id)
)
select * from product_color
select * from product_size
delete from product_size where id_product = 3

create table product_color(
	id int primary key Identity not null,
	idProduct int foreign key references product(id),
	idColor int foreign key references color(id)
)
ALTER TABLE product_size ADD is_active bit 
select * from product_color
select * from product_size

update product_size set is_active = 1
create table cart(
	id int primary key Identity not null,
	idAccount int foreign key references account(id) not null,
	idProduct int foreign key references product(id) not null,
	idColor int foreign key references color(id) not null,
	idSize int foreign key references size(id) not null,
	quantity int not null
)

insert into cart(idAccount,idProduct,idColor,idSize,quantity) values
(1,4,1,2,2)

select * from cart

drop table cart
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
ALTER TABLE bill
ADD quantity int  null

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
		
	select * from bill
	select * from detailed_invoice
		
update bill set is_active = 1 where id = 'DBAFC81C-A57F-4B21-B2BA-611A13B23ED8'