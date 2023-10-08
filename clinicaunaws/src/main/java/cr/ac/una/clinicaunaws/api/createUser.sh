#!/bin/bash
curl -X POST -H "Content-Type: application/json" -d '{
 "id": null,
  "username": "user1",
  "password": "password1",
  "name": "Elliot",
  "firstLastname": "Alderson",
  "secondLastname": "Wellick",
  "identification": "000000000",
  "email": "darayaroma@gmail.com",
  "role": "ADMINISTRATOR",
  "phoneNumber": "00000000",
  "isActive": "N",
  "isAdmin": "N",
  "passwordChanged": "N",
  "activationCode": null,
  "language": "ENGLISH",
  "profilePhoto": null,
  "version": 0
}' http://localhost:8080/clinicaunaws/api/UserController/create