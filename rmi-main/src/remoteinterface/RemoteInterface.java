package remoteinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote  {
    void connect() throws RemoteException;

    void disconnect() throws RemoteException;

    String sayHello(String msg) throws RemoteException;

    String introduceName(String msg) throws RemoteException;

    String giveAge(String msg) throws RemoteException;

}
