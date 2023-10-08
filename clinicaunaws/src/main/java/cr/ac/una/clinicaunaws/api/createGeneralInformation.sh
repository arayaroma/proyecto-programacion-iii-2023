#!/bin/bash
curl -X POST -H "Content-Type: application/json" -d '{
  "id": null,
  "name": "tremendo",
  "email": "tremendisimo",
  "photo": null,
  "htmltemplate": "notemplate",
  "version": 0
}' http://localhost:8080/clinicaunaws/api/GeneralInformationController/create