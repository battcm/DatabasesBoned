Use [MOHAN-BONE10]
go
Create PROC dbo.InserteIsIn
@FoodID int, @IngrediantID int
As
If not EXISTS(Select * From FoodItems Where FoodItemID=@FoodID)
Begin
	RaiseError('No food with the given ID exists');
	return -1;
End
If not EXISTS(Select * From Ingredients Where IngredientsID=@IngrediantID)
Begin
	RaiseError('No ingrediant with the given ID exists');
	return -1;
End
Insert into IsIn Values(@FoodID,@IngrediantID)
Go

--No need to add an update proc as all attributes are key

Create PROC dbo.DeleteIsIn
@FoodID int, @IngrediantID int
As
If not EXISTS(Select * From IsIn Where FoodID=@FoodID and IngredientID=@IngrediantID)
Begin
	RaiseError('No row fits provided keys')
	return -1;
End
Delete From IsIn Where FoodID=@FoodID and IngredientID=@IngrediantID
Go

------------------------------------------------------------------------------

Create PROC dbo.InserteSells
@RestrauntName varchar(63), @FoodID int,@Mealtype char(1)
As
If not EXISTS(Select * From Restaurant Where RestName=@RestrauntName)
Begin
	RaiseError('No restraunt with the given name exists');
	return -1;
End
If not EXISTS(Select * From FoodItems Where FoodItemID=@FoodID)
Begin
	RaiseError('No food with the given ID exists');
	return -1;
End
Insert into Sells Values(@RestrauntName, @FoodID,@Mealtype)
Go

Create PROC dbo.UpdateSells
@RestrauntName varchar(63), @FoodID int,@Mealtype char(1)
As
If not EXISTS(Select * From Sells Where RestName=@RestrauntName and FoodID=@FoodID)
Begin
	RaiseError('No row with the given keys exists');
	return -1;
End
Update Sells
Select Mealtype=@Mealtype
Where RestName=@RestrauntName and FoodID=@FoodID
Go

Create PROC dbo.DeleteSells
@RestrauntName varchar(63), @FoodID int,@Mealtype char(1)
As
If not EXISTS(Select * From Sells Where RestName=@RestrauntName and FoodID=@FoodID)
Begin
	RaiseError('No row with the given keys exists');
	return -1;
End
Delete From Sells Where RestName=@RestrauntName and FoodID=@FoodID
Go

------------------------------------------------------------------------------

Create PROC dbo.InserteWorksFor
@UserName varchar(40), @RestName varchar(63), @RestLocation varchar(255), @Permissions varchar(20)
As
Insert into WorksFor Values(@UserName, @RestName, @RestLocation, @Permissions)
Go

Create PROC dbo.UpdateWorksFor
@Username varchar(40), @RestName varchar(63), @RestLocation varchar(255), @Permission varchar(20)
As
If not EXISTS(Select * From WorksFor Where Username=@Username and RestName=@RestrauntName)
Begin
	RaiseError('No row with the given keys exists');
	return -1;
End
Update WorksFor
Select RestLocation=@RestLocation, Permission=@Permission
Where Username=@Username and RestName=@RestrauntName
Go

Create PROC dbo.DeleteWorksFor
@Username varchar(40), @RestName varchar(63), @RestLocation varchar(255), @Permission varchar(20)
As
If not EXISTS(Select * From WorksFor Where Username=@Username and RestName=@RestrauntName)
Begin
	RaiseError('No row with the given keys exists');
	return -1;
End
Delete From WorksFor Where Username=@Username and RestName=@RestrauntName
Go

------------------------------------------------------------------------------

Create PROC dbo.InserteUserTable
@UserName varchar(40), @Password varchar(20)
As
Insert into UserTable Values(@UserName, @Password)
Go

Create PROC dbo.UpdateUserTable
@UserName varchar(40), @Password varchar(20)
As
If not EXISTS(Select * From UserTable Where Username=@Username)
Begin
	RaiseError('No row with the given keys exists');
	return -1;
End
Update UserTable
Select Password=@Password
Where Username=@Username
Go

Create PROC dbo.DeleteUserTable
@UserName varchar(40), @Password varchar(20)
As
If not EXISTS(Select * From UserTable Where Username=@Username)
Begin
	RaiseError('No row with the given keys exists');
	return -1;
End
Delete From UserTable Where Username=@Username
Go