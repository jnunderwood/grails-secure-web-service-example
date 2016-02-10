# Grails Secure Web Service Example

## Description

This is a simple example application that provides a REST API for listing
and searching for employees in a database. The project has the following
characterisitcs:

- Grails: uses the [Grails][1] v3.1 framework
- Asynchronous: uses Grails [asynchronous][2] features
- RESTful: uses Grails "rest-api" profile to create a [web service][3]
- Secure: uses Grails Spring Security [Core][4] v3 and [Rest][5] v2 plugins

## Object Model

```
Employee: id, username, firstName, lastName
User: id, username, password
Role: id, authority
UserRole: user, role
```

The User, Role, and UserRole objects are used for securing the application. The
Employee object is the data we are interested in querying.

The intent for this web service is to be consumed by another Grails web
application, not a person using a web browser. Thus, the User object actually
represents an application, not a person.

## Asynchronous Service

There are two services, `EmployeeService` (synchronous) and
`AsyncEmployeeService` (asynchronous), which are used by primary controller.
The synchronous service provides a simple interface to database queries. These
include `count(), list(), findByField(), findByName()`. The asynchronous service
automatically mirrors the synchronous version by using the `@DelegateAsync`
transformation.

## Restful Controller

The primary controller, `EmployeeController`, extends `RestfulController` as per
the grails documentation for web services. It provides an asynchronous interface
to the employee service by using `.onComplete` and `.onError` features of Grails
Promises.

## Security

This application is secured via Grails Security Rest plugin. There is not much
to do. Configuration can be found in `grails-app\conf\application.groovy`.
Additionally, the `@Secured` annotation is used on the controller methods, which
is standard for Grails Spring Security Core.

While this application does not use encrypted JWT, if and when it does, the JWT
secret will be used. It is currently set to the default value in `application.yml`
and should be overridden for your own project. See the Grails Security Rest
plugin documentation for more details.

## Usage

Run grails normally to start the app: `grails run-app`. Use `curl`, for example,
to test the API:

```
# login and get authorization token
$ curl -s -H "Content-Type: application/json" -d '{"username": "admin", "password": "admin"}' http://localhost:8080/api/login

# response
{"username":"admin","roles":["ROLE_ADMIN"],"token_type":"Bearer","access_token":"eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTUDBcL2JRQlJcL0RvbW9RS0tBMUVvZFlBRTI1RWhsek1TXC9Wa1VtVkUyelVBbDBzUlwvbTRIem4zcDBoV1ZBbUdCaEFiWkdRK2hYNEpyRDBBeUE2ZEdYdXlqdERjTm9GY1pQOTd1ZmZ2K2VMVzZnWURYT3habHdZUHhWWnpLVnZVczFsYkRETU5MY2RQek9vSTdRNTRuME9iTklFN285WEFpK0FFbzhzakFjN2JJOVZCWk54ZGEyMWc2R3R0VFc4VlRwK1lOelNMTUY5cFhmOVIrNVFhZnhIb0tEMlRrc3d1QTVqTEF4VkptMWR5ZVYyeWpWRzZ6QmF6QUlWN3JyUnE1QnVVRnJPaE9tSERxSmtMWUZSQU1Nc3M5dUtWRGthQ3lcL3Z6V2FXaTJvRGJTMkFGeWt6aHR6OWw2UmhuWFYzNzJ4S1N2QVZEcURjVGowNjFOMk1nXC9xT3gxOVVRbEJxcnFTWmJzcEVSWHlMTzNIaTcwNTgrM1h5czlzc0FWQW5zMDlcL1U4emZMRUQzY3VQdlpGNjBGMXA0M1dlOWdOWGFLYmtaSzVnXC9hM1RLMStjZnY1XC9kSG4wWklHV0hlUGY4ZlV6UFB6VFhXVlJKeWpTenFtOUhSTHRmZHM5RXZ2QTBlVzhMSGJcL0JrMVFnXC9WSFNZdlFvVVJCVDNMSldvdGUzaGFGUGE4SHk1dnpTNm9lNmU2MndLT0dTVkVmeTBHNWJmcUJvVjhkXC9UcTlPcG02SVlRVXFlMHhrU0oyUEZxQjZsclJRSDE2Y1RRelwvK0gyY0orajl6WGZLODF4bEVRTUFBQT09Iiwic3ViIjoiYWRtaW4iLCJyb2xlcyI6WyJST0xFX0FETUlOIl0sImV4cCI6MTQ1NTAzODQ2NSwiaWF0IjoxNDU1MDM0ODY1fQ.2_Jz9wTYdFWH_ZUngJgNc4hj90_6oYl328ciGd2Ppcs","expires_in":3600,"refresh_token":"eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTUDBcL2JRQlJcL0RvbW9RS0tBMUVvZFlBRTI1RWhsek1TXC9Wa1VtVkUyelVBbDBzUlwvbTRIem4zcDBoV1ZBbUdCaEFiWkdRK2hYNEpyRDBBeUE2ZEdYdXlqdERjTm9GY1pQOTd1ZmZ2K2VMVzZnWURYT3habHdZUHhWWnpLVnZVczFsYkRETU5MY2RQek9vSTdRNTRuME9iTklFN285WEFpK0FFbzhzakFjN2JJOVZCWk54ZGEyMWc2R3R0VFc4VlRwK1lOelNMTUY5cFhmOVIrNVFhZnhIb0tEMlRrc3d1QTVqTEF4VkptMWR5ZVYyeWpWRzZ6QmF6QUlWN3JyUnE1QnVVRnJPaE9tSERxSmtMWUZSQU1Nc3M5dUtWRGthQ3lcL3Z6V2FXaTJvRGJTMkFGeWt6aHR6OWw2UmhuWFYzNzJ4S1N2QVZEcURjVGowNjFOMk1nXC9xT3gxOVVRbEJxcnFTWmJzcEVSWHlMTzNIaTcwNTgrM1h5czlzc0FWQW5zMDlcL1U4emZMRUQzY3VQdlpGNjBGMXA0M1dlOWdOWGFLYmtaSzVnXC9hM1RLMStjZnY1XC9kSG4wWklHV0hlUGY4ZlV6UFB6VFhXVlJKeWpTenFtOUhSTHRmZHM5RXZ2QTBlVzhMSGJcL0JrMVFnXC9WSFNZdlFvVVJCVDNMSldvdGUzaGFGUGE4SHk1dnpTNm9lNmU2MndLT0dTVkVmeTBHNWJmcUJvVjhkXC9UcTlPcG02SVlRVXFlMHhrU0oyUEZxQjZsclJRSDE2Y1RRelwvK0gyY0orajl6WGZLODF4bEVRTUFBQT09Iiwic3ViIjoiYWRtaW4iLCJyb2xlcyI6WyJST0xFX0FETUlOIl0sImlhdCI6MTQ1NTAzNDg2NX0.XLaIsy1dffb_6-5wRFWbBp8x7yonoD4EpKM42KzdFhw"}

# use the returned token to find employees
$ curl -s -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTUDBcL2JRQlJcL0RvbW9RS0tBMUVvZFlBRTI1RWhsek1TXC9Wa1VtVkUyelVBbDBzUlwvbTRIem4zcDBoV1ZBbUdCaEFiWkdRK2hYNEpyRDBBeUE2ZEdYdXlqdERjTm9GY1pQOTd1ZmZ2K2VMVzZnWURYT3habHdZUHhWWnpLVnZVczFsYkRETU5MY2RQek9vSTdRNTRuME9iTklFN285WEFpK0FFbzhzakFjN2JJOVZCWk54ZGEyMWc2R3R0VFc4VlRwK1lOelNMTUY5cFhmOVIrNVFhZnhIb0tEMlRrc3d1QTVqTEF4VkptMWR5ZVYyeWpWRzZ6QmF6QUlWN3JyUnE1QnVVRnJPaE9tSERxSmtMWUZSQU1Nc3M5dUtWRGthQ3lcL3Z6V2FXaTJvRGJTMkFGeWt6aHR6OWw2UmhuWFYzNzJ4S1N2QVZEcURjVGowNjFOMk1nXC9xT3gxOVVRbEJxcnFTWmJzcEVSWHlMTzNIaTcwNTgrM1h5czlzc0FWQW5zMDlcL1U4emZMRUQzY3VQdlpGNjBGMXA0M1dlOWdOWGFLYmtaSzVnXC9hM1RLMStjZnY1XC9kSG4wWklHV0hlUGY4ZlV6UFB6VFhXVlJKeWpTenFtOUhSTHRmZHM5RXZ2QTBlVzhMSGJcL0JrMVFnXC9WSFNZdlFvVVJCVDNMSldvdGUzaGFGUGE4SHk1dnpTNm9lNmU2MndLT0dTVkVmeTBHNWJmcUJvVjhkXC9UcTlPcG02SVlRVXFlMHhrU0oyUEZxQjZsclJRSDE2Y1RRelwvK0gyY0orajl6WGZLODF4bEVRTUFBQT09Iiwic3ViIjoiYWRtaW4iLCJyb2xlcyI6WyJST0xFX0FETUlOIl0sImV4cCI6MTQ1NTAzODQ2NSwiaWF0IjoxNDU1MDM0ODY1fQ.2_Jz9wTYdFWH_ZUngJgNc4hj90_6oYl328ciGd2Ppcs","expires_in":3600,"refresh_token":"eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTUDBcL2JRQlJcL0RvbW9RS0tBMUVvZFlBRTI1RWhsek1TXC9Wa1VtVkUyelVBbDBzUlwvbTRIem4zcDBoV1ZBbUdCaEFiWkdRK2hYNEpyRDBBeUE2ZEdYdXlqdERjTm9GY1pQOTd1ZmZ2K2VMVzZnWURYT3habHdZUHhWWnpLVnZVczFsYkRETU5MY2RQek9vSTdRNTRuME9iTklFN285WEFpK0FFbzhzakFjN2JJOVZCWk54ZGEyMWc2R3R0VFc4VlRwK1lOelNMTUY5cFhmOVIrNVFhZnhIb0tEMlRrc3d1QTVqTEF4VkptMWR5ZVYyeWpWRzZ6QmF6QUlWN3JyUnE1QnVVRnJPaE9tSERxSmtMWUZSQU1Nc3M5dUtWRGthQ3lcL3Z6V2FXaTJvRGJTMkFGeWt6aHR6OWw2UmhuWFYzNzJ4S1N2QVZEcURjVGowNjFOMk1nXC9xT3gxOVVRbEJxcnFTWmJzcEVSWHlMTzNIaTcwNTgrM1h5czlzc0FWQW5zMDlcL1U4emZMRUQzY3VQdlpGNjBGMXA0M1dlOWdOWGFLYmtaSzVnXC9hM1RLMStjZnY1XC9kSG4wWklHV0hlUGY4ZlV6UFB6VFhXVlJKeWpTenFtOUhSTHRmZHM5RXZ2QTBlVzhMSGJcL0JrMVFnXC9WSFNZdlFvVVJCVDNMSldvdGUzaGFGUGE4SHk1dnpTNm9lNmU2MndLT0dTVkVmeTBHNWJmcUJvVjhkXC9UcTlPcG02SVlRVXFlMHhrU0oyUEZxQjZsclJRSDE2Y1RRelwvK0gyY0orajl6WGZLODF4bEVRTUFBQT09Iiwic3ViIjoiYWRtaW4iLCJyb2xlcyI6WyJST0xFX0FETUlOIl0sImlhdCI6MTQ1NTAzNDg2NX0.XLaIsy1dffb_6-5wRFWbBp8x7yonoD4EpKM42KzdFhw" http://localhost:8080/api/employees/findByName/Wi

# response
[{"id":"22","firstName":"Natalie","lastName":"Wilcox","username":"NWilcox"},{"id":"67","firstName":"Yardley","lastName":"William","username":"YWilliam"}]
```

Refer to `grails-app/controllers/api/UrlMappings.groovy` to see the full set of
URLs that have been made available.

## TODO

Tests! Functional tests, especially, need to create an encrypted JWT (JSON Web
Token) containing "admin" credentials, send it to the web service, receive the
response, confirm authentication, and test successful querying. A similar
sequence using "guest" credentials should be tested and confirmed with expected
failure.

Furthermore, the application is currently setup with data that is read-only. It
might be nice to allow for creating and updating of objects; however, this is
not essential.

## Miscellaneous

Sample data is loaded from a file during Bootstrap. Sample data was obtained
from [generatedata.com][6]. Bookmark this site. You're welcome ;-)

[1]: https://grails.org/
[2]: https://grails.github.io/grails-doc/3.1.x/guide/async.html
[3]: https://grails.github.io/grails-doc/3.1.x/guide/webServices.html
[4]: https://grails-plugins.github.io/grails-spring-security-core/v3/index.html
[5]: https://alvarosanchez.github.io/grails-spring-security-rest/2.0.0.M2/docs/index.html
[6]: http://generatedata.com/

