# BrickBuster

## How to execute :rocket:
Its recommend to use *backup-brickbuster.backup* to create the database for the API with some registers. The API is capable of creating the database by itself, but in only creates the admin user

After that is necessary to access the following file

![Folder](https://i.imgur.com/JM3ycBd.png)

Inside that file you need to replace the database connection fields to boot the application correctly
Seguido de eso se debe abrir el documento y modificar los campos respectivos para la conexion:

- spring.datasource.url=jdbc:postgresql://(IP):(port)/(database_name)
- spring.datasource.username=(user)
- spring.datasource.password=(password)

![Information](https://i.imgur.com/6aSae4X.png)

## Admin credentials
You can access to all endpoints using the following admin. Some endpoints need a token to work so its necessary to login and put a header with Authorization following format *Bearer ${token}*
- email:    admintest@gmail.com
- user:     admintest
- password: admintest


## Heroku URL
*https://brickbuster.herokuapp.com*
### Login
*/api/v1/auth*
- login: /login (POST) 
- register: /register (POST)
- logout: /logout (POST)
### Movies

#### Admin users
*/api/v1/admin/movies*
-  addMovie: (POST)
-  updateMovie: /{code} (PUT)
-  getAll: (GET)
-  getByName: /search (GET)
-  deleteOne: (DELETE)

#### Signed in users
*/api/v1/common/movies*
- rentMovie: /{code}/rent (POST)
- returnMovie: /return/{code} (PUT)
- purchaseMovie: /{code}/purchase (POST)
- likeMovie: /{code}/like (PUT)


#### Public
*/api/v1/public/movies*
- getAllAvailable: /all (GET)
- getByName: /search (GET)