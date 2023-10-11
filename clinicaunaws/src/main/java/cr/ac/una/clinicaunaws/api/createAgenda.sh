#!/bin/bash
curl -X POST -H "Content-Type: application/json" -d '{
  "id": 0,
  "doctor": {
    "id": 1,
    "user": {
      "id": 1,
      "username": "string",
      "password": "string",
      "name": "string",
      "firstLastname": "string",
      "secondLastname": "string",
      "identification": "string",
      "email": "string",
      "role": "ADMINISTRATOR",
      "phoneNumber": "string",
      "isActive": "Y",
      "isAdmin": "Y",
      "passwordChanged": "N",
      "activationCode": "string",
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
  "date": "2020-04-17",
  "shiftStartTime": "a",
  "shiftEndTime": "b",
  "hourlySlots": 0,
  "version": 0
}' http://localhost:8080/clinicaunaws/api/AgendaController/create