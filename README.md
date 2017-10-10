# A "Guess word" game backend

## Intro

The backend has been implemented in scala.

It can be run using docker as

```bash
docker run -p 8080:8080 pigovsky/guess-word-game-backend
```

## API
### Register a new user 

    POST localhost:8080/users
    
request body:

```json
{"login":"bob","password":"123456"}
```
    
### Login as a user

    POST localhost:8080/login
    
request body:

```json
{"login":"bob","password":"123456"}
```
    
response body:

```json
{
  "accessToken": "6305e570-7399-477a-986e-ed08c65f6316"
}
```

Copyright © 2017 Yuriy Pigovsky
