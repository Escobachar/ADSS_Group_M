package HR.DataLayer.IMP;

import HR.DataLayer.interfaces.BranchDao;
import HR.DataLayer.interfaces.EmployeeDao;
import HR.DataLayer.interfaces.NetworkRepository;
import HR.Domain.HRManager;
import HR.Domain.Network;
import HR.Server.Utility;
import java.sql.SQLException;
import java.sql.*;
import java.util.List;

public class NetworkRepositoryImp implements NetworkRepository {
    private BranchRepositoryImp branchRepository= new BranchRepositoryImp();
    private RoleDaoImp roleDao = new RoleDaoImp();
    private BranchDao branchDao=new BranchDaoImp();
    private EmployeeDao hrManagerDao=new HRManagerDao();

    @Override
    public Network get() {
        HRManager theHRManager = (HRManager) hrManagerDao.readAll(null).get(0);
        Network network = Network.createNewNetwork(theHRManager);
        List<String> branchNames = branchDao.readAll();
        network.setRoles(roleDao.readAll());
        for(String branchName : branchNames) {
            branchRepository.NetworkBuilder(branchName);
        }
        return network;
    }

    public void delete() {
        Connection connection = Utility.toConnect();

        String[] s = {"Branch","BranchManager", "Driver", "DriverLicenseTypes", "EmployeeShifts","GeneralEmployee",
                "GeneralEmployeeRole" ,"HRManager","HistoryOfEmpShifts", "Role", "RoleOfShifts" , "ShiftAvailability", "ShiftsRequests"};
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


