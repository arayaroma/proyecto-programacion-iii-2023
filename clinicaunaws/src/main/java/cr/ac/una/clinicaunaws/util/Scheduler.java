/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.clinicaunaws.util;

import cr.ac.una.clinicaunaws.dto.MedicalAppointmentDto;
import cr.ac.una.clinicaunaws.dto.ReportDto;
import cr.ac.una.clinicaunaws.services.EmailService;
import cr.ac.una.clinicaunaws.services.MedicalAppointmentService;
import cr.ac.una.clinicaunaws.services.ReportService;
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
    
    @EJB
    ReportService rService;
    
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Schedule(second = "0", minute = "*", hour = "*")
    public void showTime() {
        System.out.print("entra");
    }

    @Schedule(second = "0", minute = "0", hour = "8")
    public void checkReminders() {
        checkAppointments();
        checkReports();
    }

    public void checkAppointments() {
        try {
            List<MedicalAppointmentDto> appointments = (List<MedicalAppointmentDto>) MaService.getAllMedicalAppointments().getData();
            if (!appointments.isEmpty()) {
                LocalDate tomorrow = LocalDate.now().plusDays(1);
                List<MedicalAppointmentDto> appToSend = appointments.stream()
                        .filter(isSameDayApp(tomorrow))
                        .collect(Collectors.toList());
                for (MedicalAppointmentDto m : appToSend) {
                    eService.sendAppointmentReminder(m);
                }
            }
        } catch (Exception e) {

        }
    }

    public void checkReports() {
        try{
            List<ReportDto> reports = (List<ReportDto>) rService.getAllReports().getData();
            if(!reports.isEmpty()){
                LocalDate today = LocalDate.now();
                List<ReportDto> repToSend = reports.stream()
                        .filter(isSameDayRep(today))
                        .collect(Collectors.toList());
                for (ReportDto r: repToSend) {
                    r = changeReportDate(r);
                    rService.updateReport(r);
                }
            }
        } catch (Exception e) {

        }
    }

    private Predicate<MedicalAppointmentDto> isSameDayApp(LocalDate compareDate) {
        return appointment -> {
            LocalDate appointmentDate = LocalDate.parse(appointment.getScheduledDate(), dateFormatter);
            return appointmentDate.isEqual(compareDate);
        };
    }
    
    private Predicate<ReportDto> isSameDayRep(LocalDate compareDate) {
        return report -> {
            LocalDate reportDate = LocalDate.parse(report.getReportDate(), dateFormatter);
            return reportDate.isEqual(compareDate);
        };
    }
    
    private ReportDto changeReportDate(ReportDto report){
        if(report.getFrequency() == "ANNUALLY"){
            report.setReportDate(plusYears(report.getReportDate(), 1));
        }
        else if(report.getFrequency() == "MONTHLY"){
            report.setReportDate(plusMonths(report.getReportDate(), 1));
        }
        else if(report.getFrequency() == "WEEKLY"){
            report.setReportDate(plusWeeks(report.getReportDate(), 1));
        }
        else if(report.getFrequency() == "DAILY"){
            report.setReportDate(plusDays(report.getReportDate(), 1));
        }
        return report;
    }
    
    private String plusYears(String date, int years) {
        LocalDate localDate = LocalDate.parse(date, dateFormatter).plusYears(years);
        return localDate.format(dateFormatter);
    }

    private String plusMonths(String date, int months) {
        LocalDate localDate = LocalDate.parse(date, dateFormatter).plusMonths(months);
        return localDate.format(dateFormatter);
    }

    private String plusWeeks(String date, int weeks) {
        LocalDate localDate = LocalDate.parse(date, dateFormatter).plusWeeks(weeks);
        return localDate.format(dateFormatter);
    }

    private String plusDays(String date, int days) {
        LocalDate localDate = LocalDate.parse(date, dateFormatter).plusDays(days);
        return localDate.format(dateFormatter);
    }
}
