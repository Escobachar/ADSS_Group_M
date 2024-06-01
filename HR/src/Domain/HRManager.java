package Domain;

import java.util.*;
public class HRManager extends Manager{
    public HRManager(int ID, String name, String bankAccountDetails, int salary, Date startOfEmployment, Date endOfEmployment, String partOfJob, int vacationsDays) {
        super(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays);
    }
}
