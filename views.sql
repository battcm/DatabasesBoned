Use [MOHAN-BONE10]
GO

CREATE VIEW ingre_in_food
AS
SELECT dbo.Ingredients.IngredientID, dbo.Ingredients.IngreName, dbo.Ingredients.IngreType, dbo.FoodItems.FoodItemsID, dbo.FoodItems.Name, dbo.FoodItems.Calories
FROM     dbo.FoodItems INNER JOIN
                  dbo.IsIn ON dbo.FoodItems.FoodItemsID = dbo.IsIn.FoodID INNER JOIN
                  dbo.Ingredients ON dbo.IsIn.IngrediantID = dbo.Ingredients.IngredientID
GO

-- ================================================================================================

CREATE VIEW rest_sells_food
AS
SELECT dbo.Restaurant.RestId, dbo.Restaurant.RestName, dbo.Restaurant.RestAddress, dbo.Sells.Mealtype, dbo.FoodItems.FoodItemsID, dbo.FoodItems.Name, dbo.FoodItems.Calories
FROM     dbo.Restaurant INNER JOIN
                  dbo.Sells ON dbo.Restaurant.RestId = dbo.Sells.RestId INNER JOIN
                  dbo.FoodItems ON dbo.Sells.FoodID = dbo.FoodItems.FoodItemsID
GO
-- ================================================================================================

CREATE VIEW ingre_canStoreWith_ingre
AS
SELECT dbo.CanStoreWith.IngredientAID, dbo.CanStoreWith.IngredientBID, dbo.Ingredients.IngreName, dbo.Ingredients.IngreType, Ingredients_1.IngreName AS Expr1, Ingredients_1.IngreType AS Expr2
FROM     dbo.CanStoreWith INNER JOIN
                  dbo.Ingredients ON dbo.CanStoreWith.IngredientAID = dbo.Ingredients.IngredientID INNER JOIN
                  dbo.Ingredients AS Ingredients_1 ON dbo.CanStoreWith.IngredientBID = Ingredients_1.IngredientID
GO