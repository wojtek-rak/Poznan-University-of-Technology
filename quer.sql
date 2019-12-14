
CREATE TABLE Customer (
  Id  int identity(1,1),
  Name varchar(255)  NOT NULL,
  Address varchar(255)  NOT NULL,
  Phone varchar(255),
  PRIMARY KEY (Id)
);


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
);


INSERT INTO Manager ( Name, Username, Password) VALUES
('Jan Kowal', 'jkowa', '123123123'),
('Maciej Lewan', 'mlewa','123456789'),
('Krzysztof Kraf', 'kkra','123123123');


CREATE TABLE Cart (
  Id  int identity(1,1),
  Count int NOT NULL default 0,
  CustomerId int  NOT NULL,
  Confirmed bit,
  PRIMARY KEY (Id)
);

ALTER TABLE Cart
   ADD CONSTRAINT FK_Cart_Customer FOREIGN KEY (CustomerId)
      REFERENCES Customer (Id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
;



INSERT INTO Cart ( Count, CustomerId, Confirmed) VALUES
(2, 1, 1);


CREATE TABLE Supplier (
  Id  int identity(1,1),
  Name varchar(255) NOT NULL,
  PRIMARY KEY (Id)
) 


INSERT INTO Supplier ( Name) VALUES
('Agro S.A.'),
('Magro Z.O.O.');

CREATE TABLE Category (
  Id  int identity(1,1),
  Name varchar(255) NOT NULL,
  SupplierId int NOT NULL,
  PRIMARY KEY (Id)
) 

ALTER TABLE Category
   ADD CONSTRAINT FK_Category_Supplier FOREIGN KEY (SupplierId)
      REFERENCES Supplier (Id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
;

INSERT INTO Category ( Name, SupplierId) VALUES
('food', 1),
('agd', 2);


CREATE TABLE Product (
  Id  int identity(1,1),
  Name varchar(255) NOT NULL,
  EAN numeric(13,0) NOT NULL,
  Price numeric(10,2) NOT NULL,
  Vat numeric(2,0) NOT NULL,
  CategoryId int NOT NULL,
  PRIMARY KEY (Id)
);

ALTER TABLE Product
   ADD CONSTRAINT FK_Product_Category FOREIGN KEY (CategoryId)
      REFERENCES Category (Id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
;

INSERT INTO Product ( Name, EAN, Price, Vat, CategoryId) VALUES
('Chpsy rukola', 2786345456752, 3.13, 5, 1),
('Wyborowa 80', 9733453456752, 67.13, 8, 1),
('Golarka philips', 6423453456752, 143.13, 23, 2);


CREATE TABLE CartProduct (
  Id  int identity(1,1),
  Count int NOT NULL default 0,
  ProductId int NOT NULL,
  CartId int NOT NULL,
  PRIMARY KEY (Id)
);

ALTER TABLE CartProduct
   ADD CONSTRAINT FK_CartProduct_Product FOREIGN KEY (ProductId)
      REFERENCES Product (Id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
;

ALTER TABLE CartProduct
   ADD CONSTRAINT FK_CartProduct_Cart FOREIGN KEY (CartId)
      REFERENCES Cart (Id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
;

INSERT INTO CartProduct ( Count, ProductId, CartId) VALUES
(2, 1, 1),
(1, 2, 1);



CREATE TABLE ShopOrder(
  Id  int identity(1,1),
  Address varchar(255)  NOT NULL,
  CustomerId int  NOT NULL,
  ManagerId int  NOT NULL,
  CartId int  NOT NULL,
  PRIMARY KEY (Id)
);

ALTER TABLE ShopOrder
   ADD CONSTRAINT FK_ShopOrder_Customer FOREIGN KEY (CustomerId)
      REFERENCES Customer (Id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
;

ALTER TABLE ShopOrder
   ADD CONSTRAINT FK_ShopOrder_Manager FOREIGN KEY (ManagerId)
      REFERENCES Manager (Id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
;

ALTER TABLE ShopOrder
   ADD CONSTRAINT FK_ShopOrder_Cart FOREIGN KEY (CartId)
      REFERENCES Cart (Id)
;
ALTER TABLE ShopOrder
    ADD CONSTRAINT UNQ_O_CART_ID UNIQUE (CartId);

INSERT INTO ShopOrder ( Address, CartId, CustomerId, ManagerId) VALUES
('Poznan; Wyczynowa 5; 34-653', 1, 1, 1);


CREATE TABLE WarehouseProduct (
  Id  int identity(1,1),
  ProductId int NOT NULL,
  Count int NOT NULL default 0,
  WarehouseCode int  NOT NULL,
  PRIMARY KEY (Id)
);

ALTER TABLE WarehouseProduct
   ADD CONSTRAINT FK_WarehouseProduct_Product FOREIGN KEY (ProductId)
      REFERENCES Product (Id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
;
ALTER TABLE WarehouseProduct
    ADD CONSTRAINT UNQ_WP_PRODUCT_ID UNIQUE (ProductId);



INSERT INTO WarehouseProduct ( ProductId, Count, WarehouseCode) VALUES
(1, 42, 123),
(3, 12, 456),
(2, 3, 789);



CREATE TABLE Sale (
  Id  int identity(1,1),
  ProductId int NOT NULL,
  PercentDiscount int NOT NULL,
  PRIMARY KEY (Id)
) 

INSERT INTO Sale ( ProductId, PercentDiscount) VALUES
(1, 15);

ALTER TABLE Sale
   ADD CONSTRAINT FK_Sale_Product FOREIGN KEY (ProductId)
      REFERENCES Product (Id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
;


