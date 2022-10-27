

Use master
GO
Drop Database [MOHAN-BONE10]
GO

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

CREATE TABLE Restaurant(
	RestId int IDENTITY(0,1) PRIMARY KEY,
	RestName varchar(63),
	RestAddress varchar(255))

CREATE TABLE Drink(
	DrinkID int IDENTITY(0,1) PRIMARY KEY,
	DrinkName varchar(63),
	DrinkBrand varchar(63),
	DrinkPrice Money)

CREATE TABLE Schedule(
	RestId int Foreign Key REFERENCES Restaurant(RestId)
		on delete cascade on update cascade,
	DayName varchar(15),OpenTime Time, CloseTime Time,
	Primary Key(RestId,DayName))

CREATE TABLE Ingredients(
	IngredientID int IDENTITY(0,1) PRIMARY KEY,
	IngreName varchar(63),
	IngreType varchar(255),
	Supplier varchar(255))

Create Table FoodItems(
	FoodItemsID int identity(0,1)Primary Key,
	Name varchar(63),
	Calories int)

CREATE TABLE Orders (
	RestId int FOREIGN KEY REFERENCES Restaurant(RestId)
		on delete cascade on update cascade,
	FoodItemsID int FOREIGN KEY REFERENCES FoodItems(FoodItemsID)
		on delete cascade on update cascade,
	DrinkID int FOREIGN KEY REFERENCES Drink(DrinkID)
		on delete cascade on update cascade,
	DateRecieved Date,
	Quantity int, 
	StorageType varchar(255), 
	PRIMARY KEY(RestId,FoodItemsID,DrinkID))

CREATE TABLE CanStoreWith(
	IngredientAID int FOREIGN KEY REFERENCES Ingredients(IngredientID),
	IngredientBID int FOREIGN KEY REFERENCES Ingredients(IngredientID),
	Primary Key(IngredientAID, IngredientBID))

Create Table IsIn(
	FoodID int FOREIGN KEY References FoodItems(FoodItemsID),
	IngredientID int FOREIGN KEY References Ingredients(IngredientID),
	Primary Key(FoodID,IngredientID))

Create Table Sells(
	RestId int FOREIGN KEY References Restaurant(RestId)
		on delete cascade on update cascade,
	FoodID int FOREIGN KEY References FoodItems(FoodItemsID)
		on delete cascade on update cascade,
	Mealtype char(1),
	Primary Key(RestId,FoodID))

Create Table UserTable(
	UserName varchar(31) Primary Key,
	PasswordHash varchar(63),
	PasswordSalt varchar(31))

Create Table WorksFor(
	Username varchar(31) FOREIGN KEY References UserTable(Username)
		on delete cascade on update cascade,
	RestId int FOREIGN KEY References Restaurant(RestId)
		on delete cascade on update cascade,
	Permission varchar(31),
	Primary Key(Username,RestId));
	go





CREATE PROCEDURE [dbo].[insert_into_ingre] 
	(@IngreName	varchar(63),
	@IngreType	varchar(255))
AS
BEGIN
	IF( @IngreName IS NULL)
		raiserror('INVALID NULL PARAMETER(S)', 15, 1);

	PRINT('encountered error');
END
INSERT INTO [MOHAN-BONE10].dbo.Ingredients
(IngreName, IngreType)
VALUES (@IngreName, @IngreType)
GO

-- ================================================================================================

CREATE PROCEDURE [dbo].[insert_into_foodItem] 
	(@Name varchar(63),
	@Calories int)
AS
BEGIN
	IF(@Calories < 0)
		raiserror('@Calories CANNOT BE NEGATIVE', 15, 1);
	IF(@Name IS NULL)
		raiserror('INVALID NULL PARAMETER(S)', 15, 1);
	PRINT('encountered error');
END
INSERT INTO [MOHAN-BONE10].dbo.FoodItems
(Name, Calories)
VALUES (@Name, @Calories)
GO

-- ================================================================================================

CREATE PROCEDURE [dbo].[insert_into_canStoreWith] 
	(@IngredientAID int,
	 @IngredientBID int)
AS
BEGIN
	IF(@IngredientAID IS NULL OR @IngredientBID IS NULL)
		raiserror('INVALID NULL PARAMETER(S)', 15, 1);
	IF(Not Exists(SELECT * FROM [MOHAN-BONE10].dbo.Ingredients WHERE IngredientID = @IngredientAID))
		raiserror('INVALID PARAMETER @IngredientAID DOES NOT EXIST', 15, 1);
	IF(Not Exists(SELECT * FROM [MOHAN-BONE10].dbo.Ingredients WHERE IngredientID = @IngredientBID))
		raiserror('INVALID PARAMETER @IngredientBID DOES NOT EXIST', 15, 1);
	IF(Exists(SELECT * FROM [MOHAN-BONE10].dbo.CanStoreWith WHERE IngredientAID = @IngredientAID AND IngredientBID = @IngredientBID))
		raiserror('RELATION ACROSS INGREDIENTS ALREADY EXISTS', 15, 1);
END
INSERT INTO [MOHAN-BONE10].dbo.CanStoreWith
(IngredientAID, IngredientBID)
VALUES ( @IngredientAID, @IngredientBID)
GO

-- ================================================================================================

Create PROC dbo.InserteIsIn(
@FoodID int, @IngredientID int
)
As
BEGIN
	If (not EXISTS(Select * From [MOHAN-BONE10].dbo.FoodItems Where FoodItemsID=@FoodID))
	Begin
		Raiserror('No food with the given ID exists', 15, 1);
		return -1;
	End
	If (not EXISTS(Select * From [MOHAN-BONE10].dbo.Ingredients Where IngredientID=@IngredientID))
	Begin
		Raiserror('No ingrediant with the given ID exists', 15, 1);
		return -1;
	END
End
Insert into [MOHAN-BONE10].dbo.IsIn
Values(@FoodID,@IngredientID)
Go

-- ================================================================================================

Create PROC dbo.InserteSells
(@RestID int, @FoodID int,@Mealtype char(1)
)As
If not EXISTS(Select * From [MOHAN-BONE10].dbo.Restaurant Where RestId=@RestID)
Begin
	Raiserror('No restraunt with the given name exists', 15, 1);
	return -1;
End
If not EXISTS(Select * From [MOHAN-BONE10].dbo.FoodItems Where FoodItemsID=@FoodID)
Begin
	Raiserror('No food with the given ID exists', 15, 1);
	return -1;
End
Insert into [MOHAN-BONE10].dbo.Sells 
Values(@RestID, @FoodID,@Mealtype)
Go

-- ================================================================================================

Create PROC dbo.InserteUserTable
@UserName varchar(40), @PasswordHash varchar(63), @PasswordSalt varchar(31)
As
BEGIN
	If (@UserName is NULL)
	Begin
		Raiserror('USERNAME CANNOT BE NULL', 15, 1);
		return -1;
	End
	If (@PasswordHash is NULL or @PasswordSalt is NULL)
	Begin
		Raiserror('PASSWORD CANNOT BE NULL', 15, 1);
		return -1;
	End
END
Insert into [MOHAN-BONE10].dbo.UserTable 
Values(@UserName, @PasswordHash, @PasswordSalt)
Go

-- ================================================================================================

CREATE procedure insertRest @name varchar(63),@addr varchar(255) AS
	if((@name is not null and @addr is not null))
		Insert into Restaurant(RestName,RestAddress) VALUES (@name,@addr);
	else
	begin
		print 'cannot insert null arguments into restaruant'
		return 10
	end
	go

-- ================================================================================================

CREATE procedure insertDrink @name varchar(63) = null,@brand varchar(255) = null,@price money =null as
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

-- ================================================================================================

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

-- ================================================================================================

CREATE procedure insertOrder @restId int,@IngrediantID int,@drinkId int,@date date,@quant int, @Type varchar(255)
as
if(Select RestAddress from Restaurant where RestId=@restId) is null
begin
print 'ERROR Restraut does not exsists'
return 10
end
if(Select Name From FoodItems where FoodItemsID=@IngrediantID) is Null
begin 
print 'ERROR INGREDIANT NO EXISITS'
return 10
end
if(Select DrinkName from Drink where DrinkId=@drinkId)is null
begin 
print 'ERROR DRINK NO EXISTS'
return 10
end
if(@quant<=0)
begin
print 'ERROR cannot be negative quantity'
return 12
end
if(@date is not null and @quant is not null and @Type is not null)
begin
Insert into Orders VALUES (@restId,@IngrediantID,@drinkId,@date,@quant,@Type)
end
else
begin
print 'ERROR CANNOT INSERT NULL VALUES'
return 9
end
go

-- ================================================================================================

CREATE PROCEDURE [dbo].[update_ingre] 
	(@IngreID int,
	@IngreName varchar(63),
	@IngreType varchar(255))
AS
BEGIN
	IF(Not Exists(SELECT * FROM [MOHAN-BONE10].dbo.Ingredients WHERE IngredientID = @IngreID))
		raiserror('INVALID PARAMETER @IngreID', 15, 1);
	PRINT('encountered error');
END
UPDATE [MOHAN-BONE10].dbo.Ingredients
SET IngreName = @IngreName, IngreType = @IngreType
WHERE IngredientID = @IngreID
GO

-- ================================================================================================

CREATE PROCEDURE [dbo].[update_foodItem] 
	(@FoodItemID int,
	@Name varchar(63),
	@Calories int)
AS
BEGIN
	IF(Not Exists(SELECT * FROM [MOHAN-BONE10].dbo.FoodItems WHERE FoodItemsID = @FoodItemID))
		raiserror('INVALID PARAMETER @FoodItemID', 15, 1);
	PRINT('encountered error');
END
UPDATE [MOHAN-BONE10].dbo.FoodItems
SET Name = @Name, Calories = @Calories
WHERE FoodItemsID = @FoodItemID
GO

-- ================================================================================================

--CREATE PROCEDURE [dbo].[update_canStoreWith] 
--	(@IngredientAID int,
--	@IngredientBID int)
--AS
--BEGIN
--	IF(Not Exists(SELECT * FROM [MOHAN-BONE10].dbo.Ingredients WHERE IngredientID = @IngredientAID))
--		raiserror('INVALID PARAMETER @IngredientAID DOES NOT EXIST', 15, 1);
--	IF(Not Exists(SELECT * FROM [MOHAN-BONE10].dbo.Ingredients WHERE IngredientID = @IngredientBID))
--		raiserror('INVALID PARAMETER @IngredientBID DOES NOT EXIST', 15, 1);
--	IF(Not Exists(SELECT * FROM [MOHAN-BONE10].dbo.CanStoreWith WHERE IngredientAID = @IngredientAID AND IngredientBID = @IngredientBID))
--		raiserror('RELATION ACROSS INGREDIENTS DOES NOT EXIST', 15, 1);
--	PRINT('encountered error');
--END
--UPDATE [MOHAN-BONE10].dbo.CanStoreWith
--SET IngredientAID = @IngredientAID, IngredientBID = @IngredientBID
--GO

-- ================================================================================================

Create PROC dbo.UpdateSells
(@RestID int, 
@FoodID int,
@Mealtype char(1)
)As
If (not EXISTS(Select * From [MOHAN-BONE10].dbo.Sells Where RestID=@RestID and FoodID=@FoodID))
Begin
	Raiserror('No row with the given keys exists', 15, 1);
	return -1;
End
Update [MOHAN-BONE10].dbo.Sells
SET RestID=@RestID, FoodID=@FoodID
Go

-- ================================================================================================

Create PROC dbo.UpdateUserTable
@UserName varchar(40), @PasswordHash varchar(63), @PasswordSalt varchar(31)
As
If not EXISTS(Select * From [MOHAN-BONE10].dbo.UserTable Where Username=@Username)
Begin
	Raiserror('No row with the given keys exists', 15, 1);
	return -1;
End
Update [MOHAN-BONE10].dbo.UserTable
SET Username=@Username, PasswordHash = @PasswordHash, PasswordSalt = @PasswordSalt
Go

-- ================================================================================================

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
-- ================================================================================================

CREATE procedure updateDrink @id int=null,@name varchar(63)=null,@brand varchar(255)=null,@price money=null
as 
if (Select DrinkID from Drink where DrinkID =@id) is null
begin
PRINT 'Error need valid ID to update drink'
return 10
end
if(@name is null)
set @name = (Select DrinkName from Drink where DrinkID=@id);

if(@brand is null)
set @brand=(Select DrinkBrand from Drink where DrinkID =@id);

if(@price<0)
begin
print 'ERROR price cannot be negative'
return 12
end
if(@price is null)
set @price= (Select DrinkPrice from Drink where DrinkID=@id);

Update Drink 
set DrinkName=@name,DrinkPrice=@price,DrinkBrand=@brand 
where DrinkID=@id;
go

-- ================================================================================================

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

-- ================================================================================================

CREATE procedure updateOrder @restId int,@IngrediantID int,@drinkId int,@date date=null,@quant int=null, @Type varchar(255)=null
as 
if(Select DateRecieved from Orders where RestId=@restId and FoodItemsID=@IngrediantID and DrinkID=@drinkId) is null
begin
print 'ERROR ORDER DOES NOT EXSISTS'
return 10
end
if(@date is null)
begin
set @date = (Select DateRecieved from Orders where RestId=@restId and FoodItemsID=@IngrediantID and DrinkID=@drinkId);
end
if(@quant is null or @quant<=0)
begin
set @quant = (Select Quantity from Orders where RestId=@restId and FoodItemsID=@IngrediantID and DrinkID=@drinkId);
end
if(@Type is null)
begin
set @Type = (Select StorageType from Orders where RestId=@restId and FoodItemsID=@IngrediantID and DrinkID=@drinkId);
end
Update Orders set DateRecieved=@date,Quantity=@quant,StorageType=@Type where RestId=@restId and FoodItemsID=@IngrediantID and DrinkID=@drinkId
go

-- ================================================================================================


CREATE PROCEDURE [dbo].[delete_ingre] 
	(@IngreID int)
AS
BEGIN
	IF(Not Exists(SELECT * FROM [MOHAN-BONE10].dbo.Ingredients WHERE IngredientID = @IngreID))

		raiserror('INVALID PARAMETER @IngreID', 15, 1);
	PRINT('encountered error');
END
DELETE [MOHAN-BONE10].dbo.Ingredients 
WHERE IngredientID = @IngreID
GO

-- ================================================================================================

CREATE PROCEDURE [dbo].[delete_foodItem] 
	(@FoodItemID int)
AS
BEGIN
	IF(Not Exists(SELECT * FROM [MOHAN-BONE10].dbo.FoodItems WHERE FoodItemsID = @FoodItemID))
		raiserror('INVALID PARAMETER @FoodItemID', 15, 1);
	PRINT('encountered error');
END
Delete [MOHAN-BONE10].dbo.FoodItems
WHERE FoodItemsID = @FoodItemID
GO

-- ================================================================================================

CREATE PROCEDURE [dbo].[delete_canStoreWith] 
	(@IngredientAID int,
	@IngredientBID int)
AS
BEGIN
	IF(Not Exists(SELECT * FROM [MOHAN-BONE10].dbo.Ingredients WHERE IngredientID = @IngredientAID))
		raiserror('INVALID PARAMETER @IngredientAID DOES NOT EXIST', 15, 1);
	IF(Not Exists(SELECT * FROM [MOHAN-BONE10].dbo.Ingredients WHERE IngredientID = @IngredientBID))
		raiserror('INVALID PARAMETER @IngredientBID DOES NOT EXIST', 15, 1);
	IF(Not Exists(SELECT * FROM [MOHAN-BONE10].dbo.CanStoreWith WHERE IngredientAID = @IngredientAID AND IngredientBID = @IngredientBID))
		raiserror('RELATION ACROSS INGREDIENTS DOES NOT EXIST', 15, 1);
	PRINT('encountered error');
END
DELETE [MOHAN-BONE10].dbo.CanStoreWith
WHERE IngredientAID = @IngredientAID AND IngredientBID = @IngredientBID
GO

-- ================================================================================================

Create PROC DeleteIsIn
(@FoodID int, @IngredientID int
)As
If (not EXISTS(Select * From [MOHAN-BONE10].dbo.IsIn Where FoodID=@FoodID and IngredientID=@IngredientID))
Begin
	Raiserror('No row fits provided keys',15, 1)
	return -1;
End
Delete [MOHAN-BONE10].dbo.IsIn 
Where FoodID=@FoodID and IngredientID=@IngredientID
Go

-- ================================================================================================

Create PROC dbo.DeleteSells
(@RestID int, 
@FoodID int
)As
If (not EXISTS(Select * From [MOHAN-BONE10].dbo.Sells Where RestID=@RestID and FoodID=@FoodID))
Begin
	Raiserror('No row with the given keys exists',15, 1);
	return -1;
End
Delete [MOHAN-BONE10].dbo.Sells 
Where RestID=@RestID and FoodID=@FoodID
Go

-- ================================================================================================

Create PROC dbo.DeleteUserTable
@UserName varchar(40), @Password varchar(20)
As
If (not EXISTS(Select * From [MOHAN-BONE10].dbo.UserTable Where Username=@Username))
Begin
	Raiserror('No row with the given keys exists', 15, 1);
	return -1;
End
Delete [MOHAN-BONE10].dbo.UserTable 
Where Username=@Username
Go

-- ================================================================================================

Create procedure deleteRest 
@id int 
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

-- ================================================================================================

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

-- ================================================================================================

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

-- ================================================================================================

Create procedure deleteOrder @restId int,@Ingred int, @drink int
as
if(Select DateRecieved from Orders where RestId=@restId and FoodItemsID=@Ingred and DrinkID=@drink) is null
begin
DELETE FROM Orders where RestId=@restId and FoodItemsID=@Ingred and DrinkID=@drink;
end
else
begin 
print 'ERROR ORDER DOES NOT EXSIST TO DELETE'
return 10
end
go

-- ================================================================================================
 