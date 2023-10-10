#!/bin/bash
curl -X PUT -H "Content-Type: application/json" -d '{
  "id": 1,
  "name": "daniel",
  "firstLastname": "araya",
  "secondLastname": "roman",
  "identification": "901140423",
  "phoneNumber": "86420240",
  "email": "darayaroma@gmail.com",
  "gender": "MALE",
  "birthDate": "2003-04-17",
  "patientPersonalHistory": {
    "id": 1,
    "patient": {
        "id": 1
    },
    "pathological": "tremendo",
    "hospitalizations": "yes",
    "surgical": "no",
    "allergies": "soccer players",
    "treatments": "star treatment",
    "medicalExams": [
      {
        "id": 0,
        "name": "covid",
        "date": "2020-04-17",
        "notes": "none",
        "version": 0
      }
    ],
    "patientCares": [
      {
        "id": 0,
        "date": "2020-04-17",
        "bloodPressure": "80/120",
        "heartRate": "80",
        "weight": "60Kg",
        "height": "170cm",
        "temperature": "36.5C",
        "bodyMassIndex": "20.7",
        "nursingNotes": "none",
        "reason": "tremendo",
        "carePlan": "aceptable",
        "observations": "none",
        "physicalExam": "none",
        "treatment": "star treatment",
        "version": 0
      }
    ],
    "version": 0
  },
  "patientFamilyHistories": [
    {
      "id": 0,
      "patient": {
        "id": 1
      },
      "disease": "diabetes",
      "relationship": "grandmother",
      "version": 0
    }
  ],
  "version": 1
}' http://localhost:8080/clinicaunaws/api/PatientController/update