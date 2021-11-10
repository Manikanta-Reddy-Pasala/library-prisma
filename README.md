# library-prisma

##Task
Please create a library application.
This application should provide a REST API that satisfies the following requirements.

a) returns all users who have actually borrowed at least one book
b) returns all non-terminated users who have not currently borrowed anything
c) returns all users who have borrowed a book on a given date
d) returns all books borrowed by a given user in a given date range
e) returns all available (not borrowed) books

as input you will get three csv files containing all users, books and who borrowed what and when

##Used Tools
1) Maven
2) Springboot
3) InteliJ

## Swagger URI
http://localhost:8000/swagger-ui.html

## how to run
1) import application into intelij
2) navigate to "src/main/java/com.prisma.library/libraryApplication.java"
3) right click on "libraryApplication.java" file and click "RUN" 
4) navigate to swagger URL to check API definition

## how it works
1) All csv files are inserted into database tables using "DataLoader.java" class
2) I have used openCSV library to load and filter unwanted data from the csv files
3) Exposed API's to query inserted data
4) Also added Health check API 