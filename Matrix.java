package Graphics3d;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {

	double[][] a;

	public Matrix(double[][] a) {
		this.a = a;
	}

	public Matrix() {
		this.a = new double[4][1];
		a[0][0] = 1;
		a[1][0] = 1;
		a[2][0] = 1;
		a[3][0] = 1;
	}
	
	public static double[][] getArray(Matrix a){
		//convert matrix to 2D array
		return a.a;
	}
	

	public void setMatrix(double[][] a) {
		//convert 2D array to matrix
		this.a = a;
	}
	
	public static String show(Matrix CTM){
		double[][] array = new double[4][1];
		array = getArray(CTM);
		double x = array[0][0];
		double y = array[1][0];
		double z = array[2][0];
		String value = ""+ x + " " + y + " " + z;
		return value;
	}

	public static double[][] matrix(double[][] a, double[][] b) {
		//matrix multiplication between two 2D arrays
		double[][] result = new double[a.length][b[0].length];
		for (int ar = 0; ar < a.length; ar++) {
			for (int bc = 0; bc < b[0].length; bc++) {
				for (int br = 0; br < b.length; br++) {
					result[ar][bc] = result[ar][bc] + a[ar][br] * b[br][bc];
				}
			}
		}	
		return result;
	}

	public static Matrix matrix(Matrix a, Matrix b) {
		//matrix multiplication between two 2D matrixes
		Matrix matrix = new Matrix(Matrix.matrix(a.a, b.a));
		return matrix;
	}
	
	
	
	public static Matrix arrayToMatrix(ArrayList<Double> a, ArrayList<Double> b, ArrayList<Double> c){
		//generate CTM for points 
		double[][] mat = new double[4][a.size()];
		for(int i = 0; i < a.size(); i++){
			mat[0][i] = a.get(i);
			mat[1][i] = b.get(i); 
			mat[2][i] = c.get(i); 
			mat[3][i] = 0.0; 
		}
		return new Matrix(mat);
		
	}
	public void mutiplyBy(Matrix mat){
		a = matrix(mat.a,a); 
	}
	
	
	
  public int[][] toPolyArrays(){
	  int[] X = null;
	  int[] Y = null;
	  int[] Z = null;
	  for(int r = 0; r < 3;r++){
		  for(int c = 0; c < a[r].length; c++){
			  X[c] = (int)a[r][c];
		  }
	  }
	  return null;
  }
  
}
