***Accenture project Technical Exercise***

**Description:**

Back-end Gradle spring boot project for assignment contains these APIs:- 

1- API to create a feature which is disabled by default. (allowed only by Admin users)

2- API to switch on a feature for a user. (allowed only by Admin users)

3- API to get all the enabled features (a mix of all the globally enabled ones and the ones enabled just for my user).

**Technologies and Frameworks:**

Java 11, Spring boot 2.6.1, MYSQL 5.7, JUNIT test,lombok, Gradle 7.3.3

##Installation: ##

###Pre-install: ###

insure java 11, Gradle and MYSQL are installed.

###Build steps: ###

1- Create MYSQL schema and name it Feature with username = admin and password=123 and make sure to change the password for production environment.

2- Dir into project folder.

3- execute these command: 

    gradle build
    gradle run

4- for adding feature:

​           URL: serverIP:serverPort/api//addFeature

​          Type of method: POST

​          Request Body:  JSON {"name":"adding"}

​		  Request Header: username that has ADMIN Role

​          Response: HTTP-Status 200

5- for enable the feature to user:

​          URL: serverIP:serverPort/api//enableFeature/{userName}/{featureName}

​          Type of method: POST

​          Request Body:  JSON {"username":"mohamed","featureName":"adding"}

​		  Request Header: username that has ADMIN Role

​          Response: HTTP-Status 200

6- for disable feature from user:

​         URL: serverIP:serverPort/api//disableFeature/{userName}/{featureName}

​          Type of method: DELETE

​          Request Body:  JSON {"username":"mohamed","featureName":"adding"}

​		  Request Header: username that has ADMIN Role

​          Response: HTTP-Status 200

7- for get all enabled feature:

​         URL: serverIP:serverPort/api//getAllUserFeature/{userName}

​          Type of method: GET

​          Request Body:  JSON {"username":"mohamed"}

​		  Request Header: username that has any Role

​          Response: HTTP-Status 200

​		  Response Body: JSON { "userName": "mohamed",

​                                                      "allEnabledFeatures": [

​                                                                    "saving",

​                                                                     "adding"

​                                                      ],

​                                                      "allUsersEnabledFeatures": [

​                                                                     "saving"

​                                                     ]

​                                                }

## Security:

We are using Spring security to disable or enable Feature for the Users. 

## Maintainers:

@elshabrawy

## License:

Unlicense © Mohamed Elshabrawy

