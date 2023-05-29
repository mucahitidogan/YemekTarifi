# <u> yemektarifi.com </u>

---
##   <u>Description  </u>

    This is a backend application built with Java-Spring Boot. Users can register in the system
    and receive an activation email. They can activate their accounts using the activation code
    and then log in to the system. Users have the ability to change their passwords or request 
    a password reset if they forget it. In such cases, a new password is sent to their email 
    address, allowing them to log in with the new password.

    Only users with the ADMIN role can add, update, or delete food recipes. Users, once logged in,
    can comment on recipes shared by admins, rate them, add them to their favorites, remove them 
    from favorites, and perform operations like searching and filtering. Users also have the ability
    to update or delete their comments and ratings.

    If a newly added recipe matches the categories of the recipes added to a user's favorites, an email
    is sent to notify the user about the new recipe, indicating that it might be of interest to them.

---

##   <u>Used Technologies and Programming Languages:</u>

###  Programming Language: Java, Spring Boot
###  Database: PostgreSQL, MongoDB
###  Queue technology: RabbitMq
###  Http Request Between Services: OpenFeign
###  JSON convertor: Jackson2JsonMessageConverter
###  Caching: Redis
###  Logging: SLF4J (used on RabbitMq consumer and producer classes)
###  Mail: Google Mail Service
###  Other technologies: Mapstruct, JSON Web Tokens, Lombok, Swagger API, Spring Config

---

# <u>Services</u>

* [AUTH SERVICE](http://localhost:8080/swagger-ui/index.html#/ "AUTH SERVICE")
* [COMMENT SERVICE](http://localhost:8081/swagger-ui/index.html#/ "COMMENT SERVICE")
    * COMMENT SERVICE
    * POINT SERVICE
* CONFIG-SERVER SERVICE
* MAIL SERVICE
* [RECIPE SERVICE](http://localhost:8083/swagger-ui/index.html#/ "RECIPE SERVICE")
          * RECIPE SERVICE
          * CATEGORY SERVICE
* [USER PROFILE SERVICE](http://localhost:8084/swagger-ui/index.html#/ "USER PROFILE SERVICE")

---

##   <u>API URLs</u>

###  <u> AUTH SERVICE</u>

    The Auth Service is the service that enables users to register and log into the system. 
    It provides functionality for user registration and authentication, allowing users to create 
    an account and securely authenticate themselves to access the system.
    After the registration process, users will receive tokens to perform actions on the website
    based on their roles and use API URLs. The tokens serve as authentication credentials that allow
    users to access protected resources and perform authorized actions based on their assigned roles. 
    These tokens are typically in the form of JSON Web Tokens (JWT) and contain encoded information 
    about the user's identity and permissions.

---
> Swagger URL : http://localhost:8080/swagger-ui/index.html#/
---

### AUTH_REGISTER
>  api/v1/auth/register

![Auth Register](Screenshots/AuthService/Auth/Register.PNG)

### AUTH_ACTIVATE_STATUS
>  api/v1/auth/activate-status


![Auth Activate-Status](Screenshots/AuthService/Auth/Activate-Status.PNG)

### AUTH_LOGIN
>  api/v1/auth/login

![Auth Login](Screenshots/AuthService/Auth/Login.PNG)

### AUTH_FORGOT_PASSWORD
>  api/v1/auth/forgot-password

![Auth Forgot-Password](Screenshots/AuthService/Auth/Forgot-Password.PNG)

### AUTH_FIND-ALL
>  api/v1/auth/find-all

![Auth Find-All](Screenshots/AuthService/Auth/Find-All.PNG)

---

# <u>USER PROFILE SERVICE</u>

    User Service is the place where users can update their user profile information, deactivate or delete 
    their accounts, and add and remove recipes they like to their favorite lists. It also allows users to 
    receive notifications about newly added recipes in the same categories as their favorite recipes.

---
> Swagger URL : http://localhost:9080/swagger-ui/index.html#/

### USER_PROFILE_UPDATE
>  api/v1/user-profile/update/{token}

![User_Profile Update](Screenshots/UserProfileService/UserProfile/UserProfile-Update.PNG)

### USER_PROFILE_CHANGE-PASSWORD
> api/v1/user-profile/change-password

![User_Profile Change_Password](Screenshots/UserProfileService/UserProfile/UserProfile-Change-Password.PNG)

### USER_PROFILE_DELETE
> api/v1/user-profile/delete/{token}

![User_Profile Delete](Screenshots/UserProfileService/UserProfile/UserProfile-Delete.PNG)

### USER_PROFILE_FIND-ALL
> api/v1/user-profile/find-all

![User_Profile Find-All](Screenshots/UserProfileService/UserProfile/UserProfile-Find-All.PNG)

### USER_PROFILE_ADD-FAVORITE-RECIPE
> api/v1/user-profile/add-favorite-recipe/{token}/{recipeId}

![User_Profile Add Favorite Recipe](Screenshots/UserProfileService/UserProfile/UserProfile-Add-Recipe-Favorite.PNG)

### USER_PROFILE_REMOVE-FAVORITE-RECIPE
> api/v1/user-profile/remove-favorite-recipe/{token}/{recipeId}

![User_Profile Remove Favorite Recipe](Screenshots/UserProfileService/UserProfile/UserProfile-Remove-Recipe-Favorite.PNG)

---

# <u>RECIPE SERVICE</u>

    Recipe Service is a service where users can search for recipes. They can perform pre-defined searches 
    based on recipe names, categories, and food names. Additionally, administrators with the "ADMIN" role 
    can create, update, and delete new categories and recipe forms.

---
> Swagger URL : http://localhost:9070/swagger-ui/index.html#/
---

#  <u>CATEGORY</u>


### CATEGORY_CREATE
> api/v1/category/create/{token}

![Category Create](Screenshots/RecipeService/Category/Category-Create.PNG)

### CATEGORY_UPDATE
> api/v1/category/update/{token}

![Category Update](Screenshots/RecipeService/Category/Category-Update.PNG)

### CATEGORY_FIND_ALL
> api/v1/category/find-all

![Category Find-All](Screenshots/RecipeService/Category/Category-Find-All.PNG)

---

##  <u>RECIPE</u>

### RECIPE_CREATE
> api/v1/recipe/create/{token}

![Recipe Create](Screenshots/RecipeService/Recipe/Recipe-Create.png)

### RECIPE_UPDATE
> api/v1/recipe/update/{token}

![Recipe Update](Screenshots/RecipeService/Recipe/Recipe-Update.png)

### RECIPE_FIND_RECIPE_BY_INGREDIENT
> api/v1/recipe/find-recipe-by-ingredient

![Recipe_Find_With_Ingredient](Screenshots/RecipeService/Recipe/Recipe-Find-Recipe-By-Ingredient-Name.PNG)

### RECIPE_FIND_RECIPE_BY_RECIPE_NAME
> api/v1/recipe/find-recipe-by-recipe-name

![Recipe_Find_With_Recipe_Name](Screenshots/RecipeService/Recipe/Recipe-Find-Recipe-By-RecipeName.PNG)

### RECIPE_FIND_RECIPE_BY_CATEGORY
> api/v1/recipe/find-recipe-by-category

![Recipe_Find_With_Category](Screenshots/RecipeService/Recipe/Recipe-Find-Recipe-By-Category.PNG)

### RECIPE_SORT_RECIPE_BY_CALORIE
> api/v1/recipe/sort-recipe-by-calorie

![Recipe_Sort_By_Calorie](Screenshots/RecipeService/Recipe/Recipe-Sort-Recipe-By-Calorie.PNG)

### RECIPE_FIND_RECIPE_BY_FILTER
> api/v1/recipe/find-recipe-by-filter

![Recipe_Find_By_Filter](Screenshots/RecipeService/Recipe/Recipe-Find-Recipe-By-Filter.PNG)

### RECIPE_FIND_ALL_WITH_CACHE
> api/v1/recipe/find-all-with-cache

![Recipe_Find-All_With_Cache](Screenshots/RecipeService/Recipe/Recipe-Find-All-With-Cache.PNG)

### RECIPE_FIND_ALL
> api/v1/recipe/find-all

![Recipe_Find-Al](Screenshots/RecipeService/Recipe/Recipe-Find-All.PNG)

### RECIPE_DELETE
> api/v1/recipe/delete-by-id/{token}/{recipeId}

![Recipe_Delete](Screenshots/RecipeService/Recipe/Recipe-Delete-By-Id.PNG)

# <u>COMMENT SERVICE</u>

    Comment Service is a service where user interactions such as commenting on a recipe and rating 
    it can be performed. Users can provide their opinions and feedback on recipes, which are based 
    on their personal experiences and preferences.

##  <u>COMMENT</u>

### COMMENT_CREATE
> api/v1/comment/create/{token}

![Comment Create](Screenshots/CommentService/Comment/Comment-Create.PNG)

### COMMENT_UPDATE
> api/v1/comment/update-comment/{token}

![Comment Update](Screenshots/CommentService/Comment/Comment-Update.PNG)

### COMMENT_DELETE
> api/v1/comment/delete-comment/{token}

![Comment Delete](Screenshots/CommentService/Comment/Comment-Delete-Comment.PNG)

### COMMENT_FIND_ALL
> api/v1/comment/find-all

![Comment Find All](Screenshots/CommentService/Comment/Comment-Find-All.PNG)

##  <u>POINT</u>

### POINT_CREATE
> api/v1/point/create/{token}

![Point Add](Screenshots/CommentService/Point/Point-Create.PNG)

### POINT_FIND_ALL
> api/v1/point/find-all/{token}

![Point Find All](Screenshots/CommentService/Point/Point-Find-All.PNG)

### POINT_DELETE
> api/v1/point/delete-by-id/{token}

![Point Remove](Screenshots/CommentService/Point/Point-Delete-By-Id.PNG)
