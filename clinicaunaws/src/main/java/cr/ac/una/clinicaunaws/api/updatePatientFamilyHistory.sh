#!/bin/bash
curl -X PUT -H "Content-Type: application/json" -d '{
  "id": 1,
  "patient": {
    "id": 1,
    "name": "daniel",
    "firstLastname": "araya",
    "secondLastname": "roman",
    "identification": "901140423",
    "phoneNumber": "86420240",
    "email": "darayaroma@gmail.com",
    "gender": "MALE",
    "birthDate": "2003-04-17",
    "version": 1
    },
    "disease": "diabetes",
    "relationship": "grandfather",
    "version": 1
}' http://localhost:8080/clinicaunaws/api/PatientFamilyHistoryController/update