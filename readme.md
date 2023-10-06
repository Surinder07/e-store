## DOCKER
how to run image using docker files 
its already added as Dockerfile 

make sure docker is running 

1. docker build -t e-store-docker .
2. check with -- docker images 
3. docker container run -p 8085:8080 a9736e791f18

======
Branch rules 
Master --> protected 
staging ---> protected 


develop-s --->  
develop  --- merge your branch anytime to test your changes 


branch off staging

staging

JA-20 parent branch would be staging 
JA-20 would be merged to staging with a pull request

Test local first -- success 
merge to Develop -- success 
PR to be merged into staging ---

final staging merged to production 

## HOW TO SET UP MySQL for your local environment

1 --> Download the MySql on your machine from below link:
https://downloads.mysql.com/archives/community/

2 --> Follow instruction and complete the installation.

3 --> Set up username and password for your local.
(we have used root as a username and java@123 as password).

4 --> go to intelliJ and select Databases from right side panel than
click on + sign and select mysql database. download latest driver if needed.
enter your username, and password. Test the connection. You should be good to use the Database.
