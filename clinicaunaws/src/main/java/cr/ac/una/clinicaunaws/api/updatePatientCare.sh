#!/bin/bash
curl -X PUT -H "Content-Type: application/json" -d '{
  "id": 1,
  "patientCareDate": "2023-05-12",
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
    "version": 0
  },
  "bloodPressure": "string",
  "heartRate": "string",
  "weight": "string",
  "height": "string",
  "temperature": "string",
  "bodyMassIndex": "string",
  "nursingNotes": "string",
  "reason": "string",
  "carePlan": "string",
  "observations": "tremendous",
  "physicalExam": "string",
  "treatment": "string",
  "version": 1
}' http://localhost:8080/clinicaunaws/api/PatientCareController/update