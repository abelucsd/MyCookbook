# My Cookbook
## Project Description
I will build an application which fetches content using a recipe API and displays it to the user to select. The application displays the content depending on user choices on cuisine, allergies, and type of meat. The user will also have access to a favorite recipe list and a todo list. 
	The purpose of the application is to serve users who want to cook dishes of different cuisines, but also gives them the ability to choose constraints on the recipes. Once the user has made some of the recipes, the user should have the ability to make a personal cookbook from the recipes made.
	The application will have an API layer, a view model which handles live data such as user criteria selection, a recycler view which handles the recipe lists, activities for general application elements, and a database to store user data.
Interface
                                 
### API
https://spoonacular.com/food-api

### Third Party Libraries
Glide: Required to display photos
Retrofit: Used to help query with an API.
SQLiteDatabase: Stores user’s added recipes.
SQLiteOpenHelper: Abstracts all database functionality.
## Specifications
Recipes should be listed based on the criteria the user selected.
User should be able to click on an icon which transfers the recipe to a todo list.
User should be able to have a cookbook of recipes which is filtered by criteria selection.
User should be able to click on an icon which transfers the recipe from the todo list to user’s cookbook. 
The user should also be able to delete recipes from both todo list and cookbook.
## UI/UX
I wanted to build a food application which needs to be light-hearted and captivating to its audience. From studies, it is shown that the yellow color grabs the attention into food. I applied this color as the background for the recipes to get people attracted to them. This theme caused me to design lightgreen buttons with white text to keep the UI consistent.

## Back End and Logic
The main logic and functionality of the application is selecting criteria based on user needs. After a user states the needs, the application would query the api using these variables. Furthermore, this logic allows the application to be extensible. Developers could add more needs as filters to use as API queries. More filters could be allergic ingredients and being a vegan.
Database logic requires the user to select and favorite recipes from the search page. Each favorite recipe would be stored in the user’s database using SQLite. Then the ‘My Recipes’ page queries the favorite recipes into a row adapter to display to the user. The user could then de-favorite each recipe to remove it from the database.

## Interesting Thoughts
The project gave me a deeper understanding of the libraries I used as well as view models. Prior to this project, I was unclear about the Retrofit library. I did not know the differences and usage of @Path and @Query. I also had to design the main Glide functionality based on its documentation. I had a successful time following the documentation.
I found view models to follow the black box principle in software development. The fragments, activities, and classes connected to the view model look neat and tidy; however, the view model itself could get convoluted. However, because all the functions getters and setters are in this class, it allows seamless development.
Building the Project
Once downloaded, the project creates a database in the mobile device. The project itself could build and run as is; however, the user needs to get an API key from Spoonaculer to use their API service. At the moment, I have hardcoded the API key in the code under RecipeAPI.
