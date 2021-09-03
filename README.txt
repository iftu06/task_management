# task_management
Automation to manage the task

Procedure to run the application

1. At first create the database using the command
    "CREATE DATABASE task_management;"

2.Next Run the command "mvn clean install".it will download all the required dependency.

3.Next Run the command mvn spirng-boot:run.It will create all the required table through ORM.

4.Then run the query listed in task_management.sql which will create the user and role for you.

5.Please Follow the file api_documentation.You must acquire the JWT token since all url are protected.
  You can acquire it by executing /login url(Instruction is in api_documentation)
  and then put it in Authorization Header and then execute subsequent GET,POST,DELETE Operation.

  Couple of User:

    userName:mainul,
    password:123,
    ROLE:USER

    userName:iftekhar,
    password:123
    ROLE:ADMIN

    userName:sabbir,
    password:123
    ROLE:USER


