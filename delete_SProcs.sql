SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Carson Batt>
-- Create date: <10/10/2022>
-- Description:	<Delete FoodItem>
-- =============================================
CREATE PROCEDURE [dbo].[delete_ingre] 
	(@IngreID int,
	@IngreName varchar(63) = NULL,
	@IngreType varchar(255) = NULL)
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
	(@FoodItemID int,
	@Name varchar(63),
	@Calories int)
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
If (not EXISTS(Select * From [MOHAN-BONE10].dbo.IsIn Where FoodID=@FoodID and IngrediantID=@IngredientID))
Begin
	Raiserror('No row fits provided keys',15, 1)
	return -1;
End
Delete [MOHAN-BONE10].dbo.IsIn 
Where FoodID=@FoodID and IngrediantID=@IngredientID
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