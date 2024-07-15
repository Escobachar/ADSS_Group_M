package DataUnitTests;

import DataLayer.IMP.NetworkRepositoryImp;
import DataLayer.interfaces.NetworkRepository;
import Domain.Network;
import Server.Utility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NetworkRepositoryTest {
    NetworkRepository NR = new NetworkRepositoryImp();
    Network network=null;

    @Before
    public void setUp() {
        //enter data to db
        network = Server.init.init();
    }

    @Test
    public void delete() {
        NR.delete();

        Connection connection = Utility.toConnect();
        int rowCount = 0;
        String[] tables = {"Branch","BranchManager", "Driver", "DriverLicenseTypes", "EmployeeShifts","GeneralEmployee",
                "GeneralEmployeeRole" ,"HRManager","HistoryOfEmpShifts", "Role", "RoleOfShifts" , "ShiftAvailability", "ShiftsRequests"};
        for(String table : tables) {
            String query = "SELECT COUNT(*) FROM " + table;
            try {
                PreparedStatement prepare = connection.prepareStatement(query);
                ResultSet resultSet = prepare.executeQuery();
                resultSet.next();
                rowCount = rowCount + resultSet.getInt(1);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            Utility.Close(connection);
        }
        Assert.assertEquals(0, rowCount);
    }


}
