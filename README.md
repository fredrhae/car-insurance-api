# API for a fictional Car insurance api
This is a personal project to test and validate some ideas to make async APIs using spring boot.

## Requirements:
- Have Java 1.8 installed and configured on system path
- Have some instance of Postgres installed and listening on port 5432
- Have the database with name "car_insurance_api" created on Postgres
- Configure in application.properties file the user and password to connect on database created.

### To execute application:

- Execute the jar binary passing the folder from config file:

```
java -jar car-insurance-api-0.0.1-SNAPSHOT.jar  --spring.config.location=/path/to/application.properties
```

## Important to mention:

* The parameters to connect on database can be changed on the application.properties file. The url, password and username
can be changed. Besides that, even the type of database can change. If you have the knowledge, can change it to mysql, or
other relational database.

* The port where application will listen can be configured too, it's just change parameter _server.port_ to the desired port.

## The calls supported by API

### Submit a quote

To submit a quote to be calculated, is expected a *POST* on the endpoint ```/api/v1/quote/``` with entities _Customer_ and 
_Vehicle_. Where the parameters for each one is displayed below:

#### Customer

- ssn: String to store the _SSN_, there is no pattern for the text. But the string submitted, will be the same used to search for, be
aware to maitain the pattern.
- name: String to save name.
- gender: This one can have two values, _'male'_ or _'female'_. Anything different from that, will throw Bad request.
- date_of_birth: String with date of birth following the US pattern separated by hyphen. If the pattern is not followed, a bad request 
will be throwed.
- address: Free string to store addres.
- email: Free string to store email, no verification is made.
- phone_number: Free string to store phone number, no verification is made.

#### Vehicle

- type: Type of vehicle that will be requested a quote. Only 4 types are supported, _'car'_, _'motorcycle'_, _'truck'_ or _'other'_. 
Anything different from that, will throw Bad request.
- manufacturing_year: String to store the manufacturing year of the car. No verification of pattern is made.
- model: String to store the model of the car. Currently, no verification is made. It can be improved.
- make: String to store the maker of the vehicle, no verification is made.

Below, we have an example of the json expected to request and initialize a quote processing.

```javascript
{
  "customer":{
    "ssn":"1234-222-123",
    "name":"John Doe",
    "gender":"male",
    "date_of_birth":"1987-10-10",
    "address":"Fools street, 100, Gotham, New Jersey",
    "email":"john.doe@darkknight.com",
    "phone_number":"0800 228626"
  },
  "vehicle":{
    "type":"car",
    "manufacturing_year":"2000",
    "model":"batmobile",
    "make":"custom"
  }
}
```

After submit a quote successfully, you will receive status 201 (CREATED) and the number of your quote. Save it to 
get status from the submitted quote in the future.

### Get status from quote

To get status from quote, you can perform a *GET* on the endpoint ```/api/v1/quote/status/{number}``` passing the number
of quote received on the *POST*. After submit it, you will receive the status from a quote with following structure:

- number: Number of the quote received on the submit request.
- status: Status from the quote, which can be _'OPEN'_, _'PROCESSING'_ or _'DONE'_. The first means that quote was just saved. 
Processing means that quote is being calculated and Done that the quote process finalized and the final price is ready to be consulted.
- price: The final price from the quote calculated from the parameters sent from the initial submission.

Here is an example of response from the status:

```javascript
{
    "number": 5,
    "status": "DONE",
    "price": 1800
}

```

### Get information from quote

To get information from quote, you can perform a *GET* on the endpoint ```/api/v1/quote/information/{number}``` passing the number
of quote received on the *POST*. After submit it, you will receive the same json structure submitted to request a quote. Example:

```javascript
{
    "customer": {
        "ssn": "1234-333-123",
        "name": "Max Caballera",
        "gender": "male",
        "date_of_birth": "1970-10-10",
        "address": "Fools street, 100, Gotham, New Jersey",
        "email": "john.doe@darkknight.com",
        "phone_number": "0800 228626"
    },
    "vehicle": {
        "type": "motorcycle",
        "manufacturing_year": "2012",
        "model": "cb300",
        "make": "honda"
    }
}
```

## Base price table

To calculate base price for insurance, the table *base_price* is used. In order to feed the application with data to improve
the calculus of insurance, the following query can be used to insert data on this table. The more data of vehicles, the better.
An example of query to insert a row on this table is below:

```sql
﻿insert into base_price (bapr_base_price, bapr_make, bapr_model, bapr_vehicle_type, bapr_manufacturing_year) values(1200.00, 'honda', 'cb1000', 'MOTORCYCLE', 2012);
```

This way, a new base price will be used to calculate insurance for this type of vehicle, year, make and model.

It is possile to add base prices without all fields, the calculus will try find using all of them, and then using less field until only the
type of vehicle is used to found a base price.



