#!/bin/bash
curl -X POST -H "Content-Type: application/json" -d '{
  "id": 0,
  "patientHistory":{
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
    "pathological": "string",
    "hospitalizations": "string",
    "surgical": "string",
    "allergies": "string",
    "treatments": "string",
    "version": 1
  }, 
  "name": "diabetes exam",
  "medicalExamDate": "2023-05-01",
  "notes": "high sugar in blood",
  "version": 0
}' http://localhost:8080/clinicaunaws/api/MedicalExamController/create