import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceRMI extends Remote{
    
    double[][] multiplicacionMatrix(double[][] matrizA, double[][] matrizB, int N ) throws RemoteException; 

}