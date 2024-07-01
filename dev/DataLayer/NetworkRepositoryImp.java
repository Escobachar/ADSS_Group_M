package DataLayer;

import Domain.HRManager;
import Domain.Network;

public class NetworkRepositoryImp implements  NetworkRepository{
    private HRManagerDao HRManagerDao;
    private BranchRepositoryImp branchRepositoryImp;
    private RoleDaoImp RoleDaoImp;

    public NetworkRepositoryImp(){
        //todo
    }

    @Override
    public Network get() {
        return null;//todo
    }
}
