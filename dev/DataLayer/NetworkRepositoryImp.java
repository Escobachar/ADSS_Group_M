package DataLayer;

import Domain.BranchManager;
import Domain.HRManager;
import Domain.Network;
import Server.Utility;
import java.sql.SQLException;
import java.sql.*;

public class NetworkRepositoryImp implements  NetworkRepository {
    private HRManagerDao HRManagerDao;
    private BranchRepositoryImp branchRepositoryImp;
    private RoleDaoImp RoleDaoImp;

    public NetworkRepositoryImp() {
        //todo
    }

    @Override
    public Network get() {
        return null;//todo
    }

    public void delete() {
        Connection connection = Utility.toConnect();

        String[] s = {"Access","Branch","BranchManager", "Driver", "DriverLicenseTypes", "EmpShifts",  "EmployeeList","GeneralEmployee",
                "GeneralEmployeeRole" ,"HRManager","HistoryOfEmpShifts", "Role", "RoleNames", "RoleOfShifts" , "ShiftAvailability", "ShiftsRequests"};
        for(String str : s) {
            try {
                String delete = "DELETE FROM " + str;
                PreparedStatement statement = connection.prepareStatement(delete);
                statement.executeUpdate();
            } catch (SQLException e) {
            System.out.println(e.getMessage());
            }
        }
        Utility.Close(connection);
    }

}


