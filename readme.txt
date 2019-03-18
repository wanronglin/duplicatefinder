This is a Spring Boot Application that uses LevenshteinDistance algorithm to find the duplicated entries in either normal.csv or advanced.csv.  The output is both on the console and rest json response.

This program handles both normal and advanced.csv file.  The default is advanced.csv.  To change to normal.csv, update application.properties, then rebuild the project

Th stdout will print out the duplicates and nonduplicates when the application start. com.exercise.duplicatefinder.controller.ApplicationController is the one
handles it.

The rest url is http://localhost:8080/findduplicated to produce JSON output. com.exercise.duplicatefinder.controller.Webontroller is the one
handles it.

To build the project run mvn clean install

To run the app java -jar target/duplicatefinder-0.0.1-SNAPSHOT.jar
