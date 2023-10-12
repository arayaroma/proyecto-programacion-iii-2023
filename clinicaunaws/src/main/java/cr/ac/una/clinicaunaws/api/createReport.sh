#!/bin/bash
curl -X POST -H "Content-Type: application/json" -d '{
  "id": 0,
  "name": "Select Users",
  "description": "tremendo",
  "query": "SELECT * FROM TBL_USER",
  "reportDate": "2023-10-12",
  "frequency": "MONTHLY",
  "version": 0
}' http://localhost:8080/clinicaunaws/api/ReportController/create