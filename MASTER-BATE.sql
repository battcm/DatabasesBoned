drop database [MOHAN-BONE10]
CREATE DATABASE [MOHAN-BONE10]
    ON (NAME = N'MOHAN-BONE10', FILENAME = N'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\MOHAN-BONE10.mdf', SIZE = 20MB, FILEGROWTH = 15%)
LOG ON (NAME = N'MOHAN-BONE10_log', FILENAME = N'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\MOHAN-BONE10_log.ldf', SIZE = 10MB, FILEGROWTH = 10%)
GO
use [MOHAN-BONE10]
go
CREATE USER yinh1 FROM LOGIN yinh1; 

exec sp_addrolemember 'db_owner', 'yinh1'; 

GO
CREATE USER battcm FROM LOGIN battcm; 

exec sp_addrolemember 'db_owner', 'battcm'; 

GO
CREATE USER olingejj FROM LOGIN olingejj
exec sp_addrolemember 'db_owner', 'olingejj'; 

GO

CREATE TABLE Restaurant(RestName varchar(63),RestAddress varchar(255) Unique,PRIMARY KEY(RestName));
CREATE TABLE Drink(ID int IDENTITY(0,1) PRIMARY KEY,DrinkName varchar(63),DrinkBrand varchar(63),DrinkPrice Money);
CREATE TABLE Schedule(RestName varchar(63) Foreign Key REFERENCES Restaurant(RestName),
DayName varchar(15),OpenTime Time, CloseTime Time,
Primary Key(RestName,DayName)
);
CREATE TABLE Ingredients(ID int IDENTITY(0,1) PRIMARY KEY,IngreName varchar(63),IngreType varchar(255));
CREATE TABLE Orders (RestName varchar(63) FOREIGN KEY REFERENCES Restaurant(RestName),
IngrediantID int FOREIGN KEY REFERENCES Ingredients(ID),
DrinkID int FOREIGN KEY REFERENCES Drink(ID),
Supplier varchar(255),DateRecieved Date,
Quantity int, StorageType varchar(255), PRIMARY KEY(RestName,IngrediantID,DrinkID));
CREATE TABLE CanStoreWith(IngredientAID int FOREIGN KEY REFERENCES Ingredients(ID),IngredientBID int FOREIGN KEY REFERENCES Ingredients(ID));
Create Table FoodItems(
	ID int identity(0,1)Primary Key,
	Name varchar(63),
	Calories int)

Create Table IsIn(
	FoodID int References FoodItems(ID),
	IngrediantID int References Ingredients(ID),
	Primary Key(FoodID,IngrediantID))

Create Table Sells(
	RestrauntName varchar(63) References Restaurant(RestName),
	FoodID int References FoodItems(ID),
	Mealtype char(1),
	Primary Key(RestrauntName,FoodID))

Create Table UserTable(
	UserName varchar(40) Primary Key,
	Password varchar(20))

Create Table WorksFor(
	Username varchar(40) References UserTable(Username) ,
	RestName varchar(63) References Restaurant(RestName) ,
	RestLocation varchar(255) References Restaurant(RestAddress),
	Permission varchar(20),
	Primary Key(Username,RestName))