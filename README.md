# Security Manager

## Running it for local profile
No additional parameters needed

## Running it for Prod profile
When you run the Application from Docker use the 'prod' profile (add `-Dspring.profiles.active=prod`)

## Check that it is running
```
http://localhost:9099/swagger-ui.html
```

## Testing
The Security Manager project contains a service module/directory and this is a standard Spring boot project with 
JUnit 5 and Hamcrest. 