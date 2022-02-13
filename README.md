# README

###Discription

> This is a sample ATM machine project developed using spring boot for API access and spring security for session management.

###Build
***For convenience there are built versions of both of the projects in the zip file.***

To manually build the application first build both of the projects using maven:

`$ cd bank-service`

And then:

`$ mvn clean install`

And the same for atm-service:

`$ cd atm-service`

And then:

`$ mvn clean install`

###Run

There is a docker-compose.yml in the project to run containers:

`$ docker-compose up`

The atm-service is accessible through the address:

`$ http://localhost`

Similarly the bank-service is accessible through the address:

`$ http://localhost:81`

###Test

There are 2 postman collections included in each project which could be used fot testing purposes.

####Notes

To test the project first issue the ***register***  API of the bank-service. After registration the ATM will alow operations on the account.

The session is active until either of the ***eject*** or ***withdraw*** APIs of the ATM service are called.# EGS_TASK
