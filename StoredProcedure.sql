Use [MOHAN-BONE10]
go
Create PROC dbo.InserteIsIn
@FoodID int, @IngrediantID int
As

/*
If not EXISTS(Select * From FoodItems Where FoodItemID=@FoodID)
Begin
	Print('No food with the given ID exists');
	return -1;
End
If not EXISTS(Select * From Ingredients Where IngredientsID=@IngrediantID)
Begin
	Print('No ingrediant with the given ID exists');
	return -1;
End
*/

Insert into IsIn Values(@FoodID,@IngrediantID)
Go

Create PROC dbo.InserteSells
@RestrauntName varchar(63), @FoodID int,@Mealtype char(1)
As

/*
If not EXISTS(Select * From Restaurant Where RestName=@RestrauntName)
Begin
	Print('No restraunt with the given name exists');
	return -1;
End
If not EXISTS(Select * From FoodItems Where FoodItemID=@FoodID)
Begin
	Print('No food with the given ID exists');
	return -1;
End
*/

Insert into Sells Values(@RestrauntName, @FoodID,@Mealtype)
Go

Create PROC dbo.InserteWorksFor
@UserName varchar(40), @RestName varchar(63), @RestLocation varchar(255), @Permissions varchar(20)
As
Insert into WorksFor Values(@UserName, @RestName, @RestLocation, @Permissions)
Go

Create PROC dbo.InserteUserTable
@UserName varchar(40), @Password varchar(20)
As
Insert into UserTable Values(@UserName, @Password)
Go