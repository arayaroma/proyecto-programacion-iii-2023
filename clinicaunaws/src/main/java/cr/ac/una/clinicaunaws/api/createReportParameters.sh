#!/bin/bash
curl -X POST -H "Content-Type: application/json" -d '{
  "id": 0,
  "report": {
    "id": 2,
    "name": "Select Users",
    "description": "tremendisimo",
    "query": "SELECT * FROM TBL_USER",
    "reportDate": "2023-10-12",
    "frequency": "MONTHLY",
    "version": 1
  },
  "name": "a",
  "value": "b",
  "version": 0
}' http://localhost:8080/clinicaunaws/api/ReportParametersController/create