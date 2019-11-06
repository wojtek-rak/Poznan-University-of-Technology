
CREATE TABLE Customer (
  Id  int identity(1,1),
  Name varchar(255)  NOT NULL,
  Address varchar(255)  NOT NULL,
  Phone varchar(255),
  PRIMARY KEY (Id)
) 


INSERT INTO Customer ( Name, Address, Phone) VALUES
('Maciej Nowak', 'Poznan; Wyczynowa 5; 34-653', '+48743612385'),
('Jacek Gawron', 'Poznan; Krakowski Nurt 15; 54-653', '48743653385'),
('Grzegorz Kaftan', 'Bromberg; Czaerstwa 3; 14-653','48734712385');

CREATE TABLE Manager (
  Id  int identity(1,1),
  Name varchar(255)  NOT NULL,
  Username varchar(255)  NOT NULL,
  Password varchar(255),
  PRIMARY KEY (Id)
) 


INSERT INTO Manager ( Name, Username, Manager) VALUES
('Jan Kowal', 'jkowa', '123123123'),
('Maciej Lewan', 'mlewa','123456789'),
('Krzysztof Kraf', 'kkra','123123123');


CREATE TABLE Cart (
  Id  int identity(1,1),
  Count int NOT NULL default 0,
  CustomerId int  NOT NULL,
  Confirmed bit,
  PRIMARY KEY (Id)
) 


INSERT INTO Cart ( Count, CustomerId, OrderId) VALUES
(2, 1, 1);



CREATE TABLE CartProduct (
  Id  int identity(1,1),
  EAN number(13,0) NOT NULL ,
  Count int NOT NULL default 0,
  ProductId int NOT NULL,
  CartId int NOT NULL,
  PRIMARY KEY (Id)
) 

INSERT INTO CartProduct ( EAN, Count, ProductId, CartId) VALUES
(2786345456752, 2, 1, 1),
(9733453456752, 1, 2, 1);

CREATE TABLE Product (
  Id  int identity(1,1),
  Name varchar(255) NOT NULL,
  EAN number(13,0) NOT NULL,
  Price number(10,2) NOT NULL,
  Vat number(2,0) NOT NULL,
  CategoryId int NOT NULL,
  PRIMARY KEY (Id)
) 

INSERT INTO Product ( Name, EAN, Price, Vat, CategoryId) VALUES
('Chpsy rukola', 2786345456752, 3.13, 5, 1),
('Wyborowa 80', 9733453456752, 67.13, 8, 1),
('Golarka philips', 6423453456752, 143.13, 23, 2);

CREATE TABLE Order (
  Id  int identity(1,1),
  Address varchar(255)  NOT NULL,
  CustomerId int  NOT NULL,
  CartId int  NOT NULL,
  PRIMARY KEY (Id)
) 

INSERT INTO Order ( Address, CartId) VALUES
('Poznan; Wyczynowa 5; 34-653', 1, 1);


CREATE TABLE WarehouseProduct (
  Id  int identity(1,1),
  ProductId int NOT NULL,
  Count int NOT NULL default 0,
  WarehouseCode int  NOT NULL,
  PRIMARY KEY (Id)
) 


INSERT INTO WarehouseProduct ( ProductId, Count, WarehouseCode) VALUES
(1, 42, 123),
(3, 12, 456),
(2, 3, 789);

CREATE TABLE Category (
  Id  int identity(1,1),
  Name varchar(255) NOT NULL,
  SupplierId int NOT NULL,
  PRIMARY KEY (Id)
) 


INSERT INTO Category ( Name, SupplierId) VALUES
('food', ),
('agd', );


CREATE TABLE Supplier (
  Id  int identity(1,1),
  Name varchar(255) NOT NULL,
  PRIMARY KEY (Id)
) 


INSERT INTO Supplier ( Name) VALUES
('Agro S.A.'),
('Magro Z.O.O.');




































