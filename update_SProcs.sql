SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Carson Batt>
-- Create date: <10/10/2022>
-- Description:	<Update Tables>
-- =============================================
CREATE PROCEDURE [dbo].[insert_into_ingre] 
	(@IngreID int			Not NULL,
	@IngreName varchar(63)	Not NULL,
	@IngreType varchar(255))
AS
BEGIN
	IF(Not Exists(SELECT * FROM [MOHAN-BONE10].dbo.Ingredients WHERE IngredientID = @IngreID))
		raiserror('INVALID PARAMETER @IngreID', 15, 1);
	PRINT('encountered error');
END
UPDATE [MOHAN-BONE10].dbo.Ingredients
SET IngredientID = @IngreID, IngreName = @IngreName, IngreType = @IngreType
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
SET FoodItemsID = @FoodItemID, Name = @Name, Calories = @Calories
GO

-- ================================================================================================

CREATE PROCEDURE [dbo].[update_canStoreWith] 
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
UPDATE [MOHAN-BONE10].dbo.CanStoreWith
SET IngredientAID = @IngredientAID, IngredientBID = @IngredientBID
GO

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
@UserName varchar(40), @Password varchar(20)
As
If not EXISTS(Select * From [MOHAN-BONE10].dbo.UserTable Where Username=@Username)
Begin
	Raiserror('No row with the given keys exists', 15, 1);
	return -1;
End
Update [MOHAN-BONE10].dbo.UserTable
SET Username=@Username, Password=@Password
Go