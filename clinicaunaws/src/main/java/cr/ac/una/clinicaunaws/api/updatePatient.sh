#!/bin/bash
curl -X PUT -H "Content-Type: application/json" -d '{
  "id": 12,
  "name": "daniel",
  "firstLastname": "araya",
  "secondLastname": "roman",
  "identification": "901140423",
  "phoneNumber": "86420240",
  "email": "darayaroma@gmail.com",
  "gender": "MALE",
  "birthDate": "1990-11-04",
  "patientPersonalHistory": {
      "id": 12,
      "pathological": "Headache",
      "hospitalizations": "Escalante Pradilla",
      "surgical": "Head surgery",
      "allergies": "None",
      "treatments": "Star treatment",
      "medicalExams": [
        {
          "id": 0,
          "name": "Vision test",
          "date": "2019-11-04",
          "notes": "Good",
          "version": 0
        }
      ],
      "patientCares": [
        {
          "id": 0,
          "bloodPressure": "120/80",
          "heartRate": "80",
          "weight": "50Kg",
          "height": "174cm",
          "temperature": "36.5C",
          "bodyMassIndex": "16.5",
          "nursingNotes": "Good health",
          "reason": "Headache",
          "carePlan": "Aspirin",
          "observations": "None",
          "physicalExam": "None",
          "treatment": "Acetaminophen",
          "version": 0
        }
      ],
      "version": 0
    }
  },
  "patientFamilyHistories": [
    {
      "id": 0,
      "disease": "Diabetes",
      "relationship": "Grandmother",
      "version": 0
    }
  ],
  "version": 1
}' http://localhost:8080/clinicaunaws/api/PatientController/update