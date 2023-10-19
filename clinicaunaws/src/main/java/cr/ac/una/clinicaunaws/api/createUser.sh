#!/bin/bash
curl -X POST -H "Content-Type: application/json" -d '{
 "id": null,
  "username": "admin",
  "password": "admin",
  "name": "Administrator",
  "firstLastname": "Admin",
  "secondLastname": "Admin",
  "identification": "000000000",
  "email": "darayaroma@gmail.com",
  "role": "ADMINISTRATOR",
  "phoneNumber": "00000000",
  "isActive": "Y",
  "isAdmin": "Y",
  "passwordChanged": "N",
  "activationCode": null,
  "language": "ENGLISH",
  "profilePhoto": null,
  "version": 0
}' http://localhost:8080/clinicaunaws/api/UserController/create