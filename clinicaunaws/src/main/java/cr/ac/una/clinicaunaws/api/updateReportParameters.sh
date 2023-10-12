#!/bin/bash
curl -X PUT -H "Content-Type: application/json" -d '{
  "id": 4,
  "report": {
    "id": 2,
    "name": "Select Users",
    "description": "tremendisimo",
    "query": "SELECT * FROM TBL_USER",
    "reportDate": "2023-10-12",
    "frequency": "MONTHLY",
    "version": 1
  },
  "name": "abc",
  "value": "def",
  "version": 1
}' http://localhost:8080/clinicaunaws/api/ReportParametersController/update