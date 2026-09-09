create table Customers(
cid char(10) primary key,
cname char(10),
city char(10),
discnt decimal(4,2)
);

create table Agents(
aid char(10) primary key,
aname char(10),
acity char(10),
apercent number
);

create table Products(
pid char(10) primary key,
pname char(10),
pcity char(10),
quantity number,
price number
);

create table orders(
 ordno char(10) primary key,
 month char(10),
 cid char(10),
 aid char(10),
 pid char(10),
 qty number,
 wons number,
 /*제약*/
 constraint fk_cid foreign key(cid)
 references customers(cid),
  constraint fk_aid foreign key(aid)
 references agents(aid),
  constraint fk_pid foreign key(pid)
 references products(pid)
);