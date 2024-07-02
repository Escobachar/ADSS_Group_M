package DataLayer;

import Domain.Network;

public interface NetworkRepository {

    Network get();
    void delete();

}
