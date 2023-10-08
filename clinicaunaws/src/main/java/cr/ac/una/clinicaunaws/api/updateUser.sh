#!/bin/bash
curl -X PUT -H "Content-Type: application/json" -d '{
 "id": 11,
  "username": "user1",
  "password": "password1",
  "name": "Elliot",
  "firstLastname": "Alderson",
  "secondLastname": "Wellick",
  "identification": "000000000",
  "email": "elliot@alderson.com",
  "role": "ADMINISTRATOR",
  "phoneNumber": "00000000",
  "isActive": "Y",
  "isAdmin": "Y",
  "passwordChanged": "N",
  "activationCode": null,
  "language": "ENGLISH",
  "profilePhoto": null,
  "version": 1
}' http://localhost:8080/clinicaunaws/api/UserController/update