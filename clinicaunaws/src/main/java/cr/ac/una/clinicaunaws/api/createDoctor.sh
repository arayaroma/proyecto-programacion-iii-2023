#!/bin/bash
curl -X POST -H "Content-Type: application/json" -d '{
  "id": 1,
  "code": "ABC",
  "idCard": 9999,
  "shiftStartTime": "13:00",
  "shiftEndTime": "21:00",
  "hourlySlots": 5,
  "version": 0
}' http://localhost:8080/clinicaunaws/api/DoctorController/create 