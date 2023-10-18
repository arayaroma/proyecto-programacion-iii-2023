/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.clinicaunaws.util;

import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;

/**
 *
 * @author vargas
 */
@Stateless
public class Scheduler {
    
    @Schedule(second= "0", minute= "*", hour= "*")
    public void showTime(){
        System.out.print("entra");
    }
    
    @Schedule(second= "0", minute= "0", hour= "8")
    public void checkReminders(){
        //TODO
    }
    
}
