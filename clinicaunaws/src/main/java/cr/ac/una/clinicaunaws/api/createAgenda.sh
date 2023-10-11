#!/bin/bash
curl -X POST -H "Content-Type: application/json" -d '{
  "id": 0,
  "doctor": {
    "id": 1,
    "user": {
      "id": 1,
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
      "version": 1
    },
    "code": "string",
    "idCard": 0,
    "shiftStartTime": "a",
    "shiftEndTime": "b",
    "hourlySlots": 0,
    "agendas": null,
    "version": 1
  },
  "agendaDate": "2020-04-17",
  "shiftStartTime": "a",
  "shiftEndTime": "b",
  "hourlySlots": 0,
  "version": 0
}' http://localhost:8080/clinicaunaws/api/AgendaController/create