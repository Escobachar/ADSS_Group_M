package HR.DataLayer.interfaces;

import HR.Domain.Network;

public interface NetworkRepository {

    Network get();
    void delete();

}
