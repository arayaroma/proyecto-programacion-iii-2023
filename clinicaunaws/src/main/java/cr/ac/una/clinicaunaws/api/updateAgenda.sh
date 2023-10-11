#!/bin/bash
curl -X PUT -H "Content-Type: application/json" -d '{
    "id": 7,
    "doctor": {
        "id": 1
    },
    "agendaDate": "2000-04-17",
    "shiftStartTime": "ab",
    "shiftEndTime": "bc",
    "hourlySlots": 5,
    "version": 1
}' http://localhost:8080/clinicaunaws/api/AgendaController/update