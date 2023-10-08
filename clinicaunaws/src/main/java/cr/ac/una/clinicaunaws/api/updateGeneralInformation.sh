#!/bin/bash
curl -X PUT -H "Content-Type: application/json" -d '{
  "id": "1",
  "name": "tremendisimo",
  "email": "tremendisimo",
  "photo": null,
  "htmltemplate": "notemplate",
  "version": 1
}' http://localhost:8080/clinicaunaws/api/GeneralInformationController/update