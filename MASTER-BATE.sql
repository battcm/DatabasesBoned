

Use master
GO
Drop Database [MOHAN-BONE10]
GO

DROP DATABASE [MOHAN-BONE10]
CREATE DATABASE [MOHAN-BONE10]
    ON (NAME = N'MOHAN-BONE10', FILENAME = N'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\MOHAN-BONE10.mdf', SIZE = 20MB, FILEGROWTH = 15%)
LOG ON (NAME = N'MOHAN-BONE10_log', FILENAME = N'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\MOHAN-BONE10_log.ldf', SIZE = 10MB, FILEGROWTH = 10%)
GO
use [MOHAN-BONE10]
go
CREATE USER yinh1 FROM LOGIN yinh1; 

exec sp_addrolemember 'db_owner', 'yinh1'; 

GO
CREATE USER [SodaBaseUserolingejj] FOR LOGIN [SodaBaseUserolingejj] WITH DEFAULT_SCHEMA=[dbo]
exec sp_addrolemember 'db_owner', 'SodaBaseUserolingejj'; 
GO
CREATE USER battcm FROM LOGIN battcm; 

exec sp_addrolemember 'db_owner', 'battcm'; 

GO
CREATE USER olingejj FROM LOGIN olingejj
exec sp_addrolemember 'db_owner', 'olingejj'; 

GO

CREATE TABLE Restaurant(RestId int IDENTITY(0,1),RestName varchar(63),RestAddress varchar(255),PRIMARY KEY(RestId));
CREATE TABLE Drink(DrinkID int IDENTITY(0,1) PRIMARY KEY,DrinkName varchar(63),DrinkBrand varchar(63),DrinkPrice Money);
CREATE TABLE Schedule(RestId int Foreign Key REFERENCES Restaurant(RestId),
DayName varchar(15),OpenTime Time, CloseTime Time,
Primary Key(RestId,DayName)
);
CREATE TABLE Ingredients(IngredientID int IDENTITY(0,1) PRIMARY KEY,IngreName varchar(63),IngreType varchar(255));
CREATE TABLE Orders (RestId int FOREIGN KEY REFERENCES Restaurant(RestId),
IngrediantID int FOREIGN KEY REFERENCES Ingredients(IngredientID),
DrinkID int FOREIGN KEY REFERENCES Drink(DrinkID),
Supplier varchar(255),DateRecieved Date,Quantity int, StorageType varchar(255), PRIMARY KEY(RestId,IngrediantID,DrinkID));
CREATE TABLE CanStoreWith(IngredientAID int FOREIGN KEY REFERENCES Ingredients(IngredientID),IngredientBID int FOREIGN KEY REFERENCES Ingredients(IngredientID));
Create Table FoodItems(
	FoodItemsID int identity(0,1)Primary Key,
	Name varchar(63),
	Calories int)

Create Table IsIn(
	FoodID int References FoodItems(FoodItemsID),
	IngrediantID int References Ingredients(IngredientID),
	Primary Key(FoodID,IngrediantID))

Create Table Sells(
	RestId int References Restaurant(RestId),
	FoodID int References FoodItems(FoodItemsID),
	Mealtype char(1),
	Primary Key(RestId,FoodID))

Create Table UserTable(
	UserName varchar(40) Primary Key,
	PasswordHash varchar(20),
	PasswordSalt varchar(20)

Create Table WorksFor(
	Username varchar(40) References UserTable(Username) ,
	RestId int References Restaurant(RestId) ,
	RestLocation varchar(255) References Restaurant(RestAddress),
	Permission varchar(20),
	Primary Key(Username,RestId));
	go
CREATE procedure insertRest @name varchar(63),@addr varchar(255)
as
if((@name is not null and @addr is not null))
Insert into Restaurant(RestName,RestAddress) VALUES (@name,@addr);
else
begin
print 'cannot insert null arguments into restaruant'
return 10
end
go
CREATE procedure updateRest @name varchar(63)=null,@addr varchar(255)=null,@id int
as 
if( (Select RestAddress from Restaurant where RestId=@id) is not null)
begin 
if(@name is null)
begin
set @name= (Select RestName 
			From Restaurant 
			where RestId=@id);
end
if(@addr is null)
begin 
set @addr=(Select RestAddress from Restaurant where RestId=@id);
end
Update Restaurant set RestName=@name,RestAddress=@addr where RestId=@id;
end
else
begin
print 'Error updating restauraunt: id does not exsists'
return 11
end

go
Create procedure deleteRest @id int
as
if(Select RestAddress from Restaurant where RestId=@id) is NULL
begin
print 'Error deleting restraunt whose name does not exsist'
return 10
end
else
begin
DELETE FROM Restaurant where RestId=@id;
end
go

CREATE procedure insertDrink @name varchar(63) = null,@brand varchar(255) = null,@price money =null
as
if(@name is null or @brand is null or @price is null)
begin
print 'Error not enough arguments to insert into Drink'
return 11
end
if(@price<0)
begin
print 'Error cannot be negative price'
return 12
end
else
begin
Insert into Drink(DrinkName,DrinkBrand,DrinkPrice) VALUES (@name,@brand,@price);
end
go
CREATE procedure updateDrink @id int=null,@name varchar(63)=null,@brand varchar(255)=null,@price money=null
as 
if (Select DrinkID from Drink where DrinkID =@id) is null
begin
PRINT 'Error need valid ID to update drink'
return 10
end
if(@name is null)
begin
set @name = (Select DrinkName from Drink where DrinkID=@id);
end
if(@brand is null)
begin
set @brand=(Select DrinkBrand from Drink where DrinkID =@id);
end
if(@price<0)
begin
print 'ERROR price cannot be negative'
return 12
end
if(@price is null)
begin
set @price= (Select DrinkPrice from Drink where DrinkID=@id);
end
Update Drink set DrinkName=@name,DrinkPrice=@price,DrinkBrand=@brand where DrinkID=@id;
go
Create procedure deleteDrink @id int
as
if(Select DrinkName from Drink where DrinkID=@id) is NULL
begin
print 'Error deleting drink whose id does not exsist'
return 10
end
else
begin
DELETE FROM Drink where DrinkID=@id;
end
go

CREATE procedure insertSchedule @restId int=null,@dayname varchar(15)=null,@Open time=null, @Close time=null
as
if (Select RestAddress from Restaurant where RestId=@restId)is NULL
begin 
PRINT 'ERROR RESTRAUNT NO EXSIST'
return 10
end
if @dayname not in ('Sunday','Monday','Tuesday','Wenesday','Thursday','Friday','Saturday') or @Open is null or @Close is null or @Open>@Close
begin
PRINT 'ERROR MALFORMED INPUT'
return 10
end
Insert into Schedule(RestId,DayName,OpenTime,CloseTime) VALUES (@restId,@dayname,@Open,@Close);
go
CREATE procedure updateSchedule @RestId int,@dayname varchar(15),@Open time=null, @Close time=null
as 
if (Select OpenTime FROM Schedule WHERE RestId=@RestId and DayName=@dayname ) is NULL
begin
PRINT 'ERROR REST OR Day DOES NOT EXSISTS'
return 10
end
if(@Open is null)
begin
set @Open=(Select OpenTime from Schedule where RestId=@RestId and DayName=@dayname);
end
if(@Close is null)
begin
set @Close =(Select CloseTime from Schedule where RestId=@RestId and DayName=@dayname);
end
UPDATE Schedule set OpenTime=@Open,CloseTime=@Close where RestId=@RestId and DayName=@dayname;
go
Create procedure deleteSchedule @RestId int,@DayName varchar(10)
as
if(Select OpenTime from Schedule where RestId=@RestId and DayName=@DayName) is NULL
begin
print 'Error deleting Schedule whose name does not exsist'
return 10
end
else
begin
DELETE FROM Schedule where RestId=@RestId and DayName=@DayName;
end
go

CREATE procedure insertOrder @restId int,@IngrediantID int,@drinkId int,@supp varchar(255),@date date,@quant int, @Type varchar(255)
as
if(Select RestAddress from Restaurant where RestId=@restId) is null
begin
print 'ERROR Restraut does not exsists'
return 10
end
if(Select IngreName From Ingredients where IngredientID=@IngrediantID) is Null
begin 
print 'ERROR INGREDIANT NO EXISITS'
return 10
end
if(Select DrinkName from Drink where DrinkId=@drinkId)is null
begin 
print 'ERROR DRINK NO EXISTS'
return 10
end
if(@supp is not null and @date is not null and @quant is not null and @Type is not null)
begin
Insert into Orders VALUES (@restId,@IngrediantID,@drinkId,@supp,@date,@quant,@Type)
end
else
begin
print 'ERROR CANNOT INSERT NULL VALUES'
return 9
end
go
CREATE procedure updateOrder @restId int,@IngrediantID int,@drinkId int,@supp varchar(255)=null,@date date=null,@quant int=null, @Type varchar(255)=null
as 
if(Select DateRecieved from Orders where RestId=@restId and IngrediantID=@IngrediantID and DrinkID=@drinkId) is null
begin
print 'ERROR ORDER DOES NOT EXSISTS'
return 10
end
if(@supp is null)
begin
set @supp = (Select Supplier from Orders where RestId=@restId and IngrediantID=@IngrediantID and DrinkID=@drinkId);
end
if(@date is null)
begin
set @date = (Select DateRecieved from Orders where RestId=@restId and IngrediantID=@IngrediantID and DrinkID=@drinkId);
end
if(@quant is null)
begin
set @quant = (Select Quantity from Orders where RestId=@restId and IngrediantID=@IngrediantID and DrinkID=@drinkId);
end
if(@Type is null)
begin
set @Type = (Select StorageType from Orders where RestId=@restId and IngrediantID=@IngrediantID and DrinkID=@drinkId);
end
Update Orders set Supplier=@supp,DateRecieved=@date,Quantity=@quant,StorageType=@Type where RestId=@restId and IngrediantID=@IngrediantID and DrinkID=@drinkId
go
Create procedure deleteOrder @restId int,@Ingred int, @drink int
as
if(Select DateRecieved from Orders where RestId=@restId and IngrediantID=@Ingred and DrinkID=@drink) is null
begin
DELETE FROM Orders where RestId=@restId and IngrediantID=@Ingred and DrinkID=@drink;
end
else
begin 
print 'ERROR ORDER DOES NOT EXSIST TO DELETE'
return 10
end
go

