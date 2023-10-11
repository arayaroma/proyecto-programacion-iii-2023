#!/bin/bash
curl -X PUT -H "Content-Type: application/json" -d '{
  "id": 4,
  "name": "daniel",
  "firstLastname": "araya",
  "secondLastname": "roman",
  "identification": "901140423",
  "phoneNumber": "86420240",
  "email": "darayaroma@gmail.com",
  "gender": "FEMALE",
  "birthDate": "2003-04-17",
  "version": 1
}' http://localhost:8080/clinicaunaws/api/PatientController/update