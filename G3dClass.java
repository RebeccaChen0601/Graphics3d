package Graphics3d;

public class G3dClass {

	public static void scale(int[][] X, int[][] Y, double[][] x, double[][] y, double[][] z, double increfactor,
			int scalefactor, int indexrow, int indexcol) {
		increment(x, y, z, increfactor, indexrow, indexcol);
		X[indexrow][indexcol] = (int) ((x[indexrow][indexcol] * scalefactor / z[indexrow][indexcol]) * scalefactor);
		Y[indexrow][indexcol] = (int) ((y[indexrow][indexcol] * scalefactor / z[indexrow][indexcol]) * scalefactor);
	}

	public static void scaleMatrix(int scalefactor, Matrix CTM) {

		Matrix scale = new Matrix(new double[][]{
			{scalefactor,0,0,0},
			{0,scalefactor,0,0},
			{0,0,scalefactor,0},
			{0,0,     0     ,1}
		});
		//generate scale matrix
		CTM.mutiplyBy(scale);;
		//matrix multiply between scale and CTM
	}

	public static void backscale(int[][] X, int[][] Y, double[][] x, double[][] y, double[][] z, double increfactor,
			int scalefactor, int indexrow, int indexcol) {
		X[indexrow][indexcol] = (int) ((x[indexrow][indexcol] / scalefactor / z[indexrow][indexcol]) / scalefactor);
		Y[indexrow][indexcol] = (int) ((y[indexrow][indexcol] / scalefactor / z[indexrow][indexcol]) / scalefactor);
		increment(x, y, z, increfactor, indexrow, indexcol);
	}

	public static void transtoCenter(int[][] X, int[][] Y, int transfactor, int indexrow, int indexcol) {
		X[indexrow][indexcol] = X[indexrow][indexcol] + transfactor;
		Y[indexrow][indexcol] = Y[indexrow][indexcol] + transfactor;
	}
	
	public static Matrix transMatrix(int transfactor, Matrix CTM) {
		double[][] a = new double[4][4];
		for (int r = 0; r < 4; r++) {
			for (int c = 0; c < 4; c++) {
				a[r][c] = 0;
			}
		}
		a[0][0] = 1;
		a[1][1] = 1;
		a[2][2] = 1;
		a[3][3] = 1;
		a[0][3] = transfactor;
		a[1][3] = transfactor;
		a[2][3] = 3;
		Matrix trans = new Matrix(a);
		//generate translation matrix
		CTM = Matrix.matrix(trans, CTM);
		//matrix multiply between translation matrix and CTM
		return CTM;
	}

	public static void increment(double[][] x, double[][] y, double[][] z, double increfactor, int indexrow,
			int indexcol) {
		z[indexrow][indexcol] += increfactor;
	}

	public static void rotation(char axis, double degrees, double x[][], double y[][], double z[][], double increfactor,
			int indexrow, int indexcol) {
		double angle = Math.PI * degrees / 180;
		double temp;
		if (axis == 'z') {
			temp = x[indexrow][indexcol];
			x[indexrow][indexcol] = x[indexrow][indexcol] * Math.cos(angle) - y[indexrow][indexcol] * Math.sin(angle);
			y[indexrow][indexcol] = temp * Math.sin(angle) + y[indexrow][indexcol] * Math.cos(angle);
		}
		if (axis == 'y') {
			temp = z[indexrow][indexcol];
			z[indexrow][indexcol] = z[indexrow][indexcol] * Math.cos(angle) - x[indexrow][indexcol] * Math.sin(angle);
			x[indexrow][indexcol] = temp * Math.sin(angle) + x[indexrow][indexcol] * Math.cos(angle);
		}
		if (axis == 'x') {
			temp = y[indexrow][indexcol];
			y[indexrow][indexcol] = y[indexrow][indexcol] * Math.cos(angle) - z[indexrow][indexcol] * Math.sin(angle);
			z[indexrow][indexcol] = temp * Math.sin(angle) + z[indexrow][indexcol] * Math.cos(angle);
		}
	}

	public static Matrix rotateMatrix(char axis, double degrees, Matrix CTM) {
		double angle = Math.PI * degrees / 180;
		double[][] a = new double[4][4];
		for (int r = 0; r < 4; r++) {
			for (int c = 0; c < 4; c++) {
				a[r][c] = 0;
			}
		}
		if (axis == 'z') {
			a[0][0] = Math.cos(angle);
			a[0][1] = -Math.sin(angle);
			a[1][0] = Math.sin(angle);
			a[1][1] = Math.cos(angle);
			a[2][2] = 1;
			a[3][3] = 1;
		}
		if (axis == 'y') {
			a[0][0] = Math.cos(angle);
			a[0][2] = Math.sin(angle);
			a[2][0] = -Math.sin(angle);
			a[2][2] = Math.cos(angle);
			a[1][1] = 1;
			a[3][3] = 1;
		}
		if (axis == 'z') {
			a[1][1] = Math.cos(angle);
			a[1][2] = -Math.sin(angle);
			a[2][1] = Math.sin(angle);
			a[2][2] = Math.cos(angle);
			a[0][0] = 1;
			a[3][3] = 1;
		}
		Matrix rotate = new Matrix(a);
		//generate translation matrix
		CTM = Matrix.matrix(rotate, CTM);
		//matrix multiply between rotation and CTM
		return CTM;
	}

	public static void cameraDisplay(int transfactor, int scalefactor, int indexrow, int indexcol, int[][] X, int[][] Y,
			double[][] x, double[][] y, double[][] z, Camera cam) {
		double normalscale = 0.88;
		double txp = 0, typ = 0, tzp = 0, w;

		tzp = z[indexrow][indexcol] * normalscale;
		txp = x[indexrow][indexcol] * normalscale;
		typ = y[indexrow][indexcol] * normalscale;
		//let the points value decrease between -1 to 1

		Points p = new Points(txp, typ, tzp);
		txp = cam.cameraMatrix[0][0] * p.x + cam.cameraMatrix[0][1] * p.y + cam.cameraMatrix[0][2] * p.z
				+ cam.cameraMatrix[0][3];
		typ = cam.cameraMatrix[1][0] * p.x + cam.cameraMatrix[1][1] * p.y + cam.cameraMatrix[1][2] * p.z
				+ cam.cameraMatrix[1][3];
		tzp = cam.cameraMatrix[2][0] * p.x + cam.cameraMatrix[2][1] * p.y + cam.cameraMatrix[2][2] * p.z
				+ cam.cameraMatrix[2][3];
		//matrix multiply between camera matrix and the points
		
		tzp += 1;
		txp = txp / tzp;
		typ = typ / tzp;
		//generate perspective
		
		
		
		X[indexrow][indexcol] = (int) (txp * scalefactor * 30 + 100);
		Y[indexrow][indexcol] = (int) (typ * scalefactor * 30 + 100);

	}
	
	public static void cameraMatrix( Camera cam, Matrix CTM){
		Matrix cameraMatrix = new Matrix(Camera.cameraMatrix);
		Matrix.matrix(cameraMatrix, CTM);
		System.out.println(Matrix.show(CTM));
	}

}
