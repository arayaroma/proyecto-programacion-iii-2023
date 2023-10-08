#!/bin/bash
curl -X POST -H "Content-Type: application/json" -d '{
  "id": 0,
  "agenda": {
    "id": 0,
    "doctor": {
      "id": 0,
      "code": "string",
      "idCard": 0,
      "shiftStartTime": "string",
      "shiftEndTime": "string",
      "hourlySlots": 0,
      "version": 0
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
    "date": "string",
    "shiftStartTime": "string",
    "shiftEndTime": "string",
    "hourlySlots": 0,
    "version": 0
  },
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
  "scheduledBy": {
    "id": 0,
    "username": "string",
    "password": "string",
    "name": "string",
    "firstLastname": "string",
    "secondLastname": "string",
    "identification": "string",
    "email": "string",
    "role": "string",
    "phoneNumber": "string",
    "isActive": "string",
    "isAdmin": "string",
    "passwordChanged": "string",
    "activationCode": "string",
    "language": "string",
    "profilePhoto": [
      "string"
    ],
    "version": 0
  },
  "scheduleDate": "string",
  "scheduleTime": "string",
  "state": "string",
  "reason": "string",
  "patientPhoneNumber": "string",
  "patientEmail": "string",
  "version": 0
}' http://localhost:8080/clinicaunaws/api/MedicalAppointmentController/create