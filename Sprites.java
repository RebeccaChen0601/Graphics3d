package Graphics3d;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Timer;

public class Sprites implements ActionListener {
	static File file = new File("Enterprise.txt");
	static ArrayList poly = new ArrayList();
	double[][] x;
	int[][] X;
	double[][] y;
	int[][] Y;
	double[][] z;
	int[][] Z;
	final int scalefactor = 20;
	int transfactor = 300;
	double increfactor = 3;
	double eincrefactor = 200;
	char axis;
	double degrees;
	int counter = 0;
	// static Polygon p;

	public Sprites(String name) {
		fileReader(new File(name));
	}

	public void paint(Graphics g) {
		if (counter == 0) {
			for (int row = 0; row < x.length; row++) {
				for (int col = 0; col < x[row].length; col++) {
					if (file.getName() == "Enterprise.txt")
						increfactor = eincrefactor;
					G3dClass.scale(X, Y, x, y, z, increfactor, scalefactor, row, col);
					G3dClass.transtoCenter(X, Y, transfactor, row, col);
				}
				g.drawPolygon(X[row], Y[row], x[row].length);
			}
		}
		
		if (counter > 0) {
			for (int i = 0; i < x.length; i++) {
				g.drawPolygon(X[i], Y[i], x[i].length);
			}
		}
		
	}

	public void update(){
		for (int row = 0; row < x.length; row++) {
			for (int col = 0; col < x[row].length; col++) {
				WCStoMCS(x, y, z, X, Y, row, col);
				MCStoWCS(X, Y, row, col);
			}
		}
	}
	public void fileReader(File file) {
		FileReader fileobj = null;
		ArrayList cor;
		try {
			fileobj = new FileReader(file);
			Scanner input = new Scanner(fileobj);
			Scanner line = new Scanner(input.nextLine());
			while (input.hasNextLine() || line.hasNext()) {
				cor = new ArrayList();
				while (line.hasNextDouble()) {
					cor.add(new Points(line.nextDouble(), line.nextDouble(), line.nextDouble()));
				}
				if (input.hasNextLine()) {
					line = new Scanner(input.nextLine());
				}
				poly.add(cor);
			}

			x = new double[poly.size()][];
			y = new double[poly.size()][];
			z = new double[poly.size()][];
			X = new int[x.length][];
			Y = new int[y.length][];
			Z = new int[z.length][];

			for (int p = 0; p < poly.size(); p++) {
				cor = (ArrayList) poly.get(p);
				x[p] = new double[cor.size()];
				y[p] = new double[cor.size()];
				z[p] = new double[cor.size()];
				X[p] = new int[cor.size()];
				Y[p] = new int[cor.size()];
				Z[p] = new int[cor.size()];
				for (int c = 0; c < cor.size(); c++) {
					Points poi = (Points) cor.get(c);
					x[p][c] = poi.getX();
					y[p][c] = poi.getY();
					z[p][c] = poi.getZ();
				}
			}
			line.close();
			input.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public void MCStoWCS(int X[][], int Y[][], int indexrow, int indexcol) {
		G3dClass.transtoCenter(X, Y, transfactor, indexrow, indexcol);
	}

	public void WCStoMCS(double x[][], double y[][], double z[][], int[][] X, int[][] Y, int indexrow, int indexcol) {
		if (file.getName() == "Enterprise.txt")
			increfactor = eincrefactor;
		if (counter > 0) {
			G3dClass.transtoCenter(X, Y, -transfactor, indexrow, indexcol);
			G3dClass.backscale(X, Y, x, y, z, -increfactor, scalefactor, indexrow, indexcol);
		}
		G3dClass.rotation(axis, degrees, x, y, z, increfactor, indexrow, indexcol);
		G3dClass.scale(X, Y, x, y, z, increfactor, scalefactor, indexrow, indexcol);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
