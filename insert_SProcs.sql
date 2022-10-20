SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Carson Batt>
-- Create date: <10/10/2022>
-- Description:	<Insert into Tables>
-- =============================================
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
@UserName varchar(40), @Password varchar(20)
As
BEGIN
	If (@UserName is NULL)
	Begin
		Raiserror('USERNAME CANNOT BE NULL', 15, 1);
		return -1;
	End
	If (@Password is NULL)
	Begin
		Raiserror('PASSWORD CANNOT BE NULL', 15, 1);
		return -1;
	End
END
Insert into [MOHAN-BONE10].dbo.UserTable 
Values(@UserName, @Password)
Go
