package cr.ac.una.clinicauna.util;

import java.time.LocalDate;

/**
 * 
 * @author arayaroma
 */
public class AgendaBuilder {

    String[] weekDays = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
    LocalDate[] weekDates = getActualWeekDates();

    public int getCurrentDayOfTheWeekIndex(LocalDate date) {
        return date.getDayOfWeek().getValue() % 7;
    }

    public String[] getWeekDays() {
        return weekDays;
    }

    public LocalDate getActualLocalDate() {
        return LocalDate.now();
    }

    public LocalDate[] getActualWeekDates() {
        LocalDate[] weekDates = new LocalDate[7];
        LocalDate actualDate = getActualLocalDate();

        for (int i = 0; i < 7; i++) {
            weekDates[i] = actualDate.plusDays(i);
        }
        return weekDates;
    }

    public String trimLocalDate(LocalDate date) {
        String[] dateParts = date.toString().split("-");
        return dateParts[2];
    }

    private AgendaBuilder(LocalDate actualDate) {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private LocalDate actualDate = LocalDate.now();

        public Builder withActualDate(LocalDate actualDate) {
            this.actualDate = actualDate;
            return this;
        }

        public AgendaBuilder build() {
            return new AgendaBuilder(actualDate);
        }

    }
}
