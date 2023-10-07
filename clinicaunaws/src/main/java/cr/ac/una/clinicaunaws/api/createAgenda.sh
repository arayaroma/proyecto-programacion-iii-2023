#!/bin/bash
curl -X POST -H "Content-Type: application/json" -d '{
  "id": null,
  "doctor": {
    "id": 1,
    "code": "ABC",
    "idCard": 9999,
    "shiftStartTime": "13:00",
    "shiftEndTime": "21:00",
    "hourlySlots": 5,
    "version": 1
  },
  "patientCare": {
    "id": 0,
    "patientHistory": {
      "id": 0,
      "patient": {
        "id": 0,
        "name": "string",
        "firstLastname": "string",
        "secondLastname": "string",
        "identification": "string",
        "phoneNumber": "string",
        "email": "string",
        "gender": "string",
        "birthDate": "string",
        "version": 0
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
    "observations": "string",
    "physicalExam": "string",
    "treatment": "string",
    "version": 0
  },
  "date": "2020-05-05 12:30:00.000",
  "shiftStartTime": "16:00",
  "shiftEndTime": "22:00",
  "hourlySlots": 5,
  "version": 0
}' http://localhost:8080/clinicaunaws/api/AgendaController/create