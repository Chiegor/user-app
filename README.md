# user-app

Application architecture:
CRUD, DAO-pattern

Stack:
JAVA, JDBC, SQL (MySQL, PostgreSQL), Maven, Java SLF4J (log);

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

User:
-userId
-userName

City:
-cityId
-cityName

UCL: // where users lived
-n
-userId
-cityId

UCW: // where users worked
-n
-userId
-cityId

User.userId == UCL.userId
City.cityId == UCL.cityId

User.userId == UCW.userId
City.cityId == UCW.cityId




