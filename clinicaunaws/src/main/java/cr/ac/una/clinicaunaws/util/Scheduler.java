/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.clinicaunaws.util;

import cr.ac.una.clinicaunaws.dto.MedicalAppointmentDto;
import cr.ac.una.clinicaunaws.services.EmailService;
import cr.ac.una.clinicaunaws.services.MedicalAppointmentService;
import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author vargas
 */
@Stateless
public class Scheduler {

    @EJB
    MedicalAppointmentService MaService;

    @EJB
    EmailService eService;

    @Schedule(second = "0", minute = "*", hour = "*")
    public void showTime() {
        System.out.print("entra");
    }

    @Schedule(second = "0", minute = "0", hour = "8")
    public void checkReminders() {
        checkAppointments();
    }

    public void checkAppointments() {
        try {
            List<MedicalAppointmentDto> appointments = (List<MedicalAppointmentDto>) MaService.getAllMedicalAppointments().getData();
            if (!appointments.isEmpty()) {
                LocalDate tomorrow = LocalDate.now().plusDays(1);
                List<MedicalAppointmentDto> appToSend = appointments.stream()
                        .filter(isSameDay(tomorrow))
                        .collect(Collectors.toList());
                for (MedicalAppointmentDto m : appToSend) {
                    eService.sendAppointmentReminder(m);
                }
            }
        } catch (Exception e) {

        }
    }

    private Predicate<MedicalAppointmentDto> isSameDay(LocalDate compareDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return appointment -> {
            LocalDate appointmentDate = LocalDate.parse(appointment.getScheduledDate(), dateFormatter);
            return appointmentDate.isEqual(compareDate);
        };
    }

}
