insert into Customers values('c001','김동길','서울',10.00);
insert into Customers values('c002','백남호','대전',12.00);
insert into Customers values('c003','신상훈','대전',8.00);
insert into Customers values('c004','이재성','서울',8.00);
insert into Customers values('c006','이재성','충주',0.00);

insert into Agents values('a01','김수현','부산',6);
insert into Agents values('a02','김동기','광주',6);
insert into Agents values('a03','이학수','경주',7);
insert into Agents values('a04','박동호','부산',6);
insert into Agents values('a05','김광현','서울',5);
insert into Agents values('a06','신동주','대전',5);

insert into products values('p01','가위','대전',111400,500);
insert into products values('p02','치약','광주',203000,50);
insert into products values('p03','치솔','서울',150600,1000);
insert into products values('p04','볼펜','서울',125300,1000);
insert into products values('p05','연필','대전',221400,1000);
insert into products values('p06','홀더','대전',123100,2000);
insert into products values('p07','싸인펜','광주',100500,1000);


insert into orders values('1011','1월','c001','a01','p01',1000,450000);
insert into orders values('1012','1월','c001','a01','p01',1000,450000);
insert into orders values('1019','2월','c001','a02','p02',400,180000);
insert into orders values('1017','2월','c001','a06','p03',600,540000);
insert into orders values('1018','2월','c001','a03','p04',600,540000);
insert into orders values('1023','3월','c001','a04','p05',500,450000);
insert into orders values('1022','3월','c001','a05','p06',400,720000);
insert into orders values('1025','4월','c001','a05','p07',800,720000);
insert into orders values('1013','1월','c002','a03','p03',1000,880000);
insert into orders values('1026','5월','c002','a05','p03',800,704000);
insert into orders values('1015','1월','c003','a03','p05',1200,1104000);
insert into orders values('1014','1월','c003','a03','p05',1200,1104000);
insert into orders values('1021','1월','c004','a06','p01',1000,460000);
insert into orders values('1016','1월','c006','a01','p01',1000,500000);
insert into orders values('1020','2월','c006','a03','p07',800,600000);
insert into orders values('1024','3월','c006','a06','p01',800,400000);
insert into orders values('1027','5월','c006','a01','p07',1000,1000000);

