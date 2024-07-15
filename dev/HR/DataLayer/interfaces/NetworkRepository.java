package DataLayer.interfaces;

import Domain.Network;

public interface NetworkRepository {

    Network get();
    void delete();

}
