package DataLayer.IMP;

import DataLayer.interfaces.BranchDao;
import DataLayer.interfaces.EmployeeDao;
import DataLayer.interfaces.NetworkRepository;
import Domain.HRManager;
import Domain.Network;
import Server.Utility;
import java.sql.SQLException;
import java.sql.*;
import java.util.List;

public class NetworkRepositoryImp implements NetworkRepository {
    private BranchRepositoryImp branchRepository= new BranchRepositoryImp();
    private RoleDaoImp RoleDao = new RoleDaoImp();
    private BranchDao branchDao=new BranchDaoImp();
    private DataLayer.interfaces.RoleDao roleDao=new RoleDaoImp();
    private EmployeeDao hrManagerDao=new HRManagerDao();

    @Override
    public Network get() {
        HRManager theHRManager = (HRManager) hrManagerDao.readAll(null).get(0);
        Network network = Network.createNewNetwork(theHRManager);
        List<String> branchNames = branchDao.readAll();
        network.setRoles(roleDao.readAll());
        for(String branchName : branchNames) {
            network.addBranch(branchRepository.get(branchName));
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

