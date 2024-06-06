package suppliers;

public class DaysOfTheWeek {
    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    }

    public static Day intToDay(int day){
        switch (day){
            case 1: return Day.SUNDAY;
            case 2: return Day.MONDAY;
            case 3: return Day.TUESDAY;
            case 4: return Day.WEDNESDAY;
            case 5: return Day.THURSDAY;
            case 6: return Day.FRIDAY;
            case 7: return Day.SATURDAY;
            default: return null;
        }
    }
}