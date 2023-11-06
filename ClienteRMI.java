import java.rmi.Naming;

public class ClienteRMI {
    static int N = 4000;
    static int M = 800;
    static int submatrixTamanio=N/4;
    public static void main(String[] args) throws Exception{
        
        double[][] A = new double[N][M];
        double[][] B = new double[M][N];
        double[][] BT = new double[N][M];
        double[][] C = new double[N][N];
        // Inicializar matriz A 
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                A[i][j] = (6 * i) - (2 * j);
            }
        }
        //Inicializar matriz B
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                B[i][j] = (8 * i) + (3 * j);
            }
        }
        //Transponer matriz B y asignarla a BT
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                BT[i][j] = B[j][i];
            }
        }
        Thread[] nodos = new Thread[2];
       // printMatrix("Matriz A calculada",A);
        //printMatrix("Matriz B calculada",B);
        //printMatrix("Matriz BT calculada",BT);

        nodos[0]=new ProcesaNodo(A,BT,C,1,N);
        nodos[1]=new ProcesaNodo(A,BT,C,2,N);
        for(int i=0;i<2;i++){
            nodos[i].start();
        }

        for(int i=0;i<2;i++){
            nodos[i].join();
        }
       // printMatrix("Matriz C resultante",C);
        ObtenerCheckSum(C);
    }
    public static class ProcesaNodo extends Thread{
        private double[][] A;
        private double[][] B;
        private double[][] C;
        private int nodo;
        private int tamanio;

        public ProcesaNodo(double[][] A, double[][] B, double[][] C,int nodo, int N) {
            this.A = A;
            this.B = B;
            this.C = C;
            this.nodo=nodo;
            this.tamanio = N;
        }

        @Override
        public void run() {
            if(nodo==1){ //tratamos el nodo 1
                try {
                    String url = "rmi://10.0.0.5/prueba";
                    InterfaceRMI rmi = (InterfaceRMI)Naming.lookup(url);
            
                    
                    double[][] A1 = llenarSubmatrices(A, 0, submatrixTamanio);
                    double[][] A2 = llenarSubmatrices(A, submatrixTamanio, submatrixTamanio * 2);
                    
                    double[][] BT1 = llenarSubmatrices(B, 0, submatrixTamanio);
                    double[][] BT2 = llenarSubmatrices(B, submatrixTamanio, submatrixTamanio * 2);
                    double[][] BT3 = llenarSubmatrices(B, submatrixTamanio * 2, submatrixTamanio * 3);
                    double[][] BT4 = llenarSubmatrices(B, submatrixTamanio * 3, submatrixTamanio * 4);
                    
                    double[][] C1 = rmi.multiplicacionMatrix(A1, BT1, N);
                    double[][] C2 = rmi.multiplicacionMatrix(A1, BT2, N);
                    double[][] C3 = rmi.multiplicacionMatrix(A1, BT3, N);
                    double[][] C4 = rmi.multiplicacionMatrix(A1, BT4, N);
                    double[][] C5 = rmi.multiplicacionMatrix(A2, BT1, N);
                    double[][] C6 = rmi.multiplicacionMatrix(A2, BT2, N);
                    double[][] C7 = rmi.multiplicacionMatrix(A2, BT3, N);
                    double[][] C8 = rmi.multiplicacionMatrix(A2, BT4, N);
                    
                    
                    formaMatriz(C, C1, 0, 0);
                    formaMatriz(C, C2, 0, (submatrixTamanio) );
                    formaMatriz(C, C3, 0, (submatrixTamanio) * 2);
                    formaMatriz(C, C4, 0, (submatrixTamanio) * 3);
                    formaMatriz(C, C5, (submatrixTamanio), 0);
                    formaMatriz(C, C6, (submatrixTamanio), (submatrixTamanio));
                    formaMatriz(C, C7, (submatrixTamanio), (submatrixTamanio) * 2);
                    formaMatriz(C, C8, (submatrixTamanio), (submatrixTamanio) * 3);
                
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }else{ //tratamos el nodo 2 
                try {
                    
                String url = "rmi://10.0.0.6/prueba";
                InterfaceRMI rmi = (InterfaceRMI)Naming.lookup(url);
            
                
                
                double[][] A3 = llenarSubmatrices(A, submatrixTamanio * 2, submatrixTamanio * 3);
                double[][] A4 = llenarSubmatrices(A, submatrixTamanio * 3, submatrixTamanio * 4);
                
                double[][] BT1 = llenarSubmatrices(B, 0, submatrixTamanio);
                double[][] BT2 = llenarSubmatrices(B, submatrixTamanio, submatrixTamanio * 2);
                double[][] BT3 = llenarSubmatrices(B, submatrixTamanio * 2, submatrixTamanio * 3);
                double[][] BT4 = llenarSubmatrices(B, submatrixTamanio * 3, submatrixTamanio * 4);
                
                double[][] C9 = rmi.multiplicacionMatrix(A3, BT1, N);
                double[][] C10 = rmi.multiplicacionMatrix(A3, BT2, N);
                double[][] C11 = rmi.multiplicacionMatrix(A3, BT3, N);
                double[][] C12 = rmi.multiplicacionMatrix(A3, BT4, N);
                double[][] C13 = rmi.multiplicacionMatrix(A4, BT1, N);
                double[][] C14 = rmi.multiplicacionMatrix(A4, BT2, N);
                double[][] C15 = rmi.multiplicacionMatrix(A4, BT3, N);
                double[][] C16 = rmi.multiplicacionMatrix(A4, BT4, N);
                
                
                formaMatriz(C, C9, (submatrixTamanio) * 2, 0);
                formaMatriz(C, C10, (submatrixTamanio) * 2, (submatrixTamanio));
                formaMatriz(C, C11, (submatrixTamanio) * 2, (submatrixTamanio) * 2);
                formaMatriz(C, C12, (submatrixTamanio) * 2, (submatrixTamanio) * 3);
                formaMatriz(C, C13, (submatrixTamanio) * 3, 0);
                formaMatriz(C, C14, (submatrixTamanio) * 3, (submatrixTamanio));
                formaMatriz(C, C15, (submatrixTamanio) * 3, (submatrixTamanio) * 2);
                formaMatriz(C, C16, (submatrixTamanio) * 3, (submatrixTamanio) * 3);
            } catch (Exception e) {
                e.getStackTrace();
            }
            }
        }
    }
    
    // Método para imprimir una matriz
    public static void printMatrix(String label, double[][] matrix) {
        System.out.println(label);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }
    public static void ObtenerCheckSum(double[][] matriz) {
        double check = 0.0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                check += matriz[i][j];
            }
        }
        System.err.println("\n El valor del checksum es " + check);
    }
    public static double[][] llenarSubmatrices(double[][] matriz, int inicioFila, int finFila) {
        int numFilas = finFila - inicioFila;
        int numColumnas = matriz[0].length;
        double[][] submatriz = new double[numFilas][numColumnas];
        for (int i = 0; i < numFilas; i++) {
            submatriz[i] = matriz[inicioFila + i].clone();
        }
        return submatriz;
    }
    public static void formaMatriz(double[][] matrizOriginal, double[][] matrizAcomodar, int fila, int columna) {
        for (int i = 0; i < matrizAcomodar.length; i++) {
            for (int j = 0; j < matrizAcomodar[i].length; j++) {
                matrizOriginal[fila + i][columna + j] = matrizAcomodar[i][j];
            }
        }
    }
}