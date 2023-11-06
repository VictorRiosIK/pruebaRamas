import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClaseRMI extends UnicastRemoteObject implements InterfaceRMI{

    protected ClaseRMI() throws RemoteException {
        super();
    }

    @Override
    public double[][] multiplicacionMatrix(double[][] matrizA, double[][] matrizB, int N) {
        double [][] matrixAux = new double[N/4][N/4];
        
        for (int i = 0; i < matrizA.length; i++) { 
            for (int j = 0; j < matrizA[i].length; j++) {
                for (int k = 0; k < matrizB.length; k++) {
                    matrixAux[i][k] += matrizA[i][j] * matrizB[k][j];
                }
            }
        }

        return matrixAux;
    }
    
}