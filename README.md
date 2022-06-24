# user-app

Application architecture:
CRUD, DAO-pattern

Stack:
JAVA, JDBC, SQL (MySQL, PostgreSQL), Maven, Java SLF4J (log);

User-app allows to store various information about users.

1. The application must allow CRUD operation, each user is unique

2. each user can have 0..* the city where he worked
3. each user can have 1..* cities where he lived

4. it should be possible to see all users who worked in city X
5. it should be possible to see all users who lived in city X


Commands:

1 - get all user from user's list
2 - get all city from city's list
3 - get all city where user lived
4 - get all city where user worked
5 - create new user and add it to the users list. Return user's ID
6 - Get user id by user name
7 - update - update user by user name or user id
8 - delete - delete user by user name or user id
9 - get all user by city lived
10 - get all user by city worked
h - help - get list of command

SQL

SCHEMAS

users:
-user_id
-user_name

cities:
-city_id
-city_name

users_cities_lived
-n
-user_id
-city_id

users_cities_worked
-n
-user_id
-city_id





