package Graphics3d;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Polygons extends JPanel implements ActionListener, KeyListener {
	static JFrame f;
	static int width = 800;
	static int height = 800;
	static File file = new File("Enterprise.txt");
	static ArrayList poly = new ArrayList();
	static double[][] x;
	static int[][] X;
	static double[][] y;
	static int[][] Y;
	static double[][] z;
	static int[][] Z;
	static final int scalefactor = 20;
	static int transfactor = 300;
	static double increfactor = 3;
	static double eincrefactor = 20;
	static char axis;
	static double degrees;
	static int counter = 0;
	static Camera cam = new Camera();
	static Model lookat = new Model(0.0, 0.0, 0.0);
	static Model lookfrom = new Model(0.0, 0.0, 10.0);
	static Model lookup = new Model(0.0, 0.0, 1.0);
	static double looklist = 0.25;
	static Matrix CTM = new Matrix();
	static double[][] coordinate;

	public Polygons() {
		addKeyListener(this);
		setFocusable(true);
	}

	public static void main(String[] args) {
		
		fileReader(file);
		// read the cordinates from the file
		Polygons p = new Polygons();
		f = new JFrame();
		f.add(p);
		f.setSize(width, height);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// end the program when the frame is closed
	}

	public void paintComponent(Graphics g) {
		for (int row = 0; row < x.length; row++) {
			for (int col = 0; col < x[row].length; col++) {
				MCStoWCS(x, y, z, X, Y, row, col);
				// convert coordinates from model to world
			}
			//if(Orientation(x,y,row) == true)
			g.drawPolygon(X[row], Y[row], x[row].length);
		}
		
	}
	static ArrayList<Points> list = null;
	static float Points[] = null;
	public static void fileReader(File file) {
		FileReader fileobj = null;
		ArrayList cor;
		try {
			fileobj = new FileReader(file);
			Scanner input = new Scanner(fileobj);
			Scanner line = new Scanner(input.nextLine());
			Hashtable<Integer, Points> set = new Hashtable<Integer, Points>();
			while (input.hasNextLine() || line.hasNext()) {				
				list = new ArrayList<Points>();
				while (line.hasNextDouble()) {
					double xtmp = line.nextDouble();
					double ytmp = line.nextDouble();
					double ztmp = line.nextDouble();
					Points t = new Points(xtmp,ytmp, ztmp);
					int hash = t.hashCode();
					if(set.contains(t)){
						list.add(set.get(hash));
					}
					else{
					set.put(hash,t);
					list.add(set.get(hash));
					}	
				}
				if (input.hasNextLine()) {
					line = new Scanner(input.nextLine());
				}
				poly.add(list);
			}

//			while (input.hasNextLine() || line.hasNext()) {
//				cor = new ArrayList();
//				while (line.hasNextDouble()) {
//					cor.add(new points(line.nextDouble(), line.nextDouble(), line.nextDouble()));
//					// add the coordinates to the points class and to the
//					// ArrayList
//				}
//				if (input.hasNextLine()) {
//					line = new Scanner(input.nextLine());
//				}
//				poly.add(cor);
//				// add polygons to the overall ArrayList
//			}
			// add all the coordinates in to 2D ArrayLists
			
			x = new double[poly.size()][];
			y = new double[poly.size()][];
			z = new double[poly.size()][];
			X = new int[x.length][];
			Y = new int[y.length][];
			Z = new int[z.length][];
			Points = new float[poly.size()*list.size()*3];
			// set the size of the array

			int index = 0;
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
					Points[index] = (float) poi.getX();
					Points[index+1] = (float) poi.getY();
					Points[index+2] = (float) poi.getZ();
					x[p][c] = poi.getX();
					y[p][c] = poi.getY();
					z[p][c] = poi.getZ();
					index+=3;
					// assign the values for the array
				}
			}

			line.close();
			input.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public static boolean Orientation(double[][] x, double[][] y, int p) {
		double V1X = x[p][2] - x[p][1];
		double V1Y = y[p][2] - y[p][1];
		double V2X = x[p][1] - x[p][0];
		double V2Y = y[p][1] - y[p][0];
		double result = V1X * V2Y - V2X * V1Y;
		if (result > 0)
			return true;
		return false;
	}

	public static void MCStoWCS(double x[][], double y[][], double z[][], int X[][], int Y[][], int indexrow,
			int indexcol) {
		WCStoMCS(x, y, z, X, Y, indexrow, indexcol);
		// convert from world to model
	}

	public static void WCStoMCS(double x[][], double y[][], double z[][], int[][] X, int[][] Y, int indexrow,
			int indexcol) {
		G3dClass.cameraDisplay(transfactor, scalefactor, indexrow, indexcol, X, Y, x, y, z, cam);
		// use camera to view the object
		// graphMatrix();
		G3dClass.transtoCenter(X, Y, transfactor, indexrow, indexcol);
		// move to the center for display

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public static void graphMatrix() {
		ArrayList cor;
		double normalscale = 0.88;
		for (int p = 0; p < poly.size(); p++) {
			coordinate = new double[4][1];
			cor = (ArrayList) poly.get(p);
			for (int c = 0; c < cor.size(); c++) {
				Points poi = (Points) cor.get(c);
				coordinate[0][0] = poi.getX() * normalscale;
				coordinate[1][0] = poi.getY() * normalscale;
				coordinate[2][0] = poi.getZ() * normalscale;
				coordinate[3][0] = 1.0;
				CTM = new Matrix(coordinate);
				// generate CTM matrix
				G3dClass.cameraMatrix(cam, CTM);
				CTM = G3dClass.rotateMatrix(axis, degrees, CTM);
				G3dClass.scaleMatrix(scalefactor, CTM);
				CTM = G3dClass.transMatrix(transfactor, CTM);
				// rotate, scale and translate
				coordinate = CTM.a;
				// convert matrix to array
				X[p][c] = (int) (coordinate[0][0] / coordinate[2][0]);
				Y[p][c] = (int) (coordinate[1][0] / coordinate[2][0]);
				// make perspective
				coordinate = new double[4][1];
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int k = e.getKeyCode();
		if (k == KeyEvent.VK_1) {
			axis = 'y';
			degrees = 10;
			counter++;
			for (int row = 0; row < x.length; row++) {
				for (int col = 0; col < x[row].length; col++) {
					if (file.getName() == "Enterprise.txt")
						increfactor = eincrefactor;
					if (counter > 0) {
						G3dClass.transtoCenter(X, Y, -transfactor, row, col);
						G3dClass.backscale(X, Y, x, y, z, -increfactor, scalefactor, row, col);
					}
					G3dClass.rotation(axis, degrees, x, y, z, increfactor, row, col);
					G3dClass.scale(X, Y, x, y, z, increfactor, scalefactor, row, col);
				}
			}
			// graphMatrix();
		}
		if (k == KeyEvent.VK_2) {
			axis = 'y';
			degrees = -10;
			counter++;
			for (int row = 0; row < x.length; row++) {
				for (int col = 0; col < x[row].length; col++) {
					if (file.getName() == "Enterprise.txt")
						increfactor = eincrefactor;
					if (counter > 0) {
						G3dClass.transtoCenter(X, Y, -transfactor, row, col);
						G3dClass.backscale(X, Y, x, y, z, -increfactor, scalefactor, row, col);
					}
					G3dClass.rotation(axis, degrees, x, y, z, increfactor, row, col);
					G3dClass.scale(X, Y, x, y, z, increfactor, scalefactor, row, col);
				}
			}
		}
		if (k == KeyEvent.VK_3) {
			axis = 'x';
			degrees = 10;
			counter++;
			for (int row = 0; row < x.length; row++) {
				for (int col = 0; col < x[row].length; col++) {
					if (file.getName() == "Enterprise.txt")
						increfactor = eincrefactor;
					if (counter > 0) {
						G3dClass.transtoCenter(X, Y, -transfactor, row, col);
						G3dClass.backscale(X, Y, x, y, z, -increfactor, scalefactor, row, col);
					}
					G3dClass.rotation(axis, degrees, x, y, z, increfactor, row, col);
					G3dClass.scale(X, Y, x, y, z, increfactor, scalefactor, row, col);
				}
			}
		}
		if (k == KeyEvent.VK_4) {
			axis = 'x';
			degrees = -10;
			counter++;
			for (int row = 0; row < x.length; row++) {
				for (int col = 0; col < x[row].length; col++) {
					if (file.getName() == "Enterprise.txt")
						increfactor = eincrefactor;
					if (counter > 0) {
						G3dClass.transtoCenter(X, Y, -transfactor, row, col);
						G3dClass.backscale(X, Y, x, y, z, -increfactor, scalefactor, row, col);
					}
					G3dClass.rotation(axis, degrees, x, y, z, increfactor, row, col);
					G3dClass.scale(X, Y, x, y, z, increfactor, scalefactor, row, col);
				}
			}
		}
		if (k == KeyEvent.VK_5) {
			axis = 'z';
			degrees = 10;
			counter++;
			for (int row = 0; row < x.length; row++) {
				for (int col = 0; col < x[row].length; col++) {
					if (file.getName() == "Enterprise.txt")
						increfactor = eincrefactor;
					if (counter > 0) {
						G3dClass.transtoCenter(X, Y, -transfactor, row, col);
						G3dClass.backscale(X, Y, x, y, z, -increfactor, scalefactor, row, col);
					}
					G3dClass.rotation(axis, degrees, x, y, z, increfactor, row, col);
					G3dClass.scale(X, Y, x, y, z, increfactor, scalefactor, row, col);
				}
			}
		}
		if (k == KeyEvent.VK_6) {
			axis = 'z';
			degrees = -10;
			counter++;
			for (int row = 0; row < x.length; row++) {
				for (int col = 0; col < x[row].length; col++) {
					if (file.getName() == "Enterprise.txt")
						increfactor = eincrefactor;
					if (counter > 0) {
						G3dClass.transtoCenter(X, Y, -transfactor, row, col);
						G3dClass.backscale(X, Y, x, y, z, -increfactor, scalefactor, row, col);
					}
					G3dClass.rotation(axis, degrees, x, y, z, increfactor, row, col);
					G3dClass.scale(X, Y, x, y, z, increfactor, scalefactor, row, col);
				}
			}
		}
		if (k == KeyEvent.VK_D) {
			// increase lookfrom.x to rotate at x axis
			counter++;
			lookfrom.x += looklist;
		}
		if (k == KeyEvent.VK_A) {
			// increase lookfrom.y to rotate at y axis
			counter++;
			lookfrom.y += looklist;
		}
		if (k == KeyEvent.VK_W) {
			// increase lookfrom.z to zoom out
			counter++;
			lookfrom.z += looklist;
		}
		if (k == KeyEvent.VK_S) {
			// increase lookat.z to move upward
			counter++;
			lookat.z += looklist;
		}
		if (k == KeyEvent.VK_C) {
			// increase lookat.x to rotate at x axis
			counter++;
			lookat.x += looklist;
		}
		if (k == KeyEvent.VK_Z) {
			// increase lookat.y to rotate at y axis
			counter++;
			lookat.y += looklist;
		}
		if (k == KeyEvent.VK_H) {
			// decrease lookfrom.x to rotate at x axis
			counter++;
			lookfrom.x -= looklist;
		}
		if (k == KeyEvent.VK_F) {
			// decrease lookfrom.y to rotate at y axis
			counter++;
			lookfrom.y -= looklist;
		}
		if (k == KeyEvent.VK_T) {
			// increase lookfrom.z to zoom in
			counter++;
			lookfrom.z -= looklist;
		}
		if (k == KeyEvent.VK_N) {
			// decrease lookat.x to rotate at x axis
			counter++;
			lookat.x -= looklist;
		}
		if (k == KeyEvent.VK_V) {
			// decrease lookat.y to rotate at y axis
			counter++;
			lookat.y -= looklist;
		}
		if (k == KeyEvent.VK_G) {
			// increase lookat.z to move downward
			counter++;
			lookat.z -= looklist;
		}
		if (k == KeyEvent.VK_L) {
			// decrease lookup.x to rotate the camera at x axis
			counter++;
			lookup.x -= looklist;
		}
		if (k == KeyEvent.VK_J) {
			// decrease lookup.y to rotate the camera at y axis
			counter++;
			lookup.y -= looklist;
		}
		if (k == KeyEvent.VK_I) {
			// decrease lookup.z to rotate the camera at x axis
			counter++;
			lookup.z -= looklist;
		}
		if (k == KeyEvent.VK_RIGHT) {
			// increase lookup.x to rotate the camera at x axis
			counter++;
			lookup.x += looklist;
		}
		if (k == KeyEvent.VK_LEFT) {
			// increase lookup.y to rotate the camera at y axis
			counter++;
			lookup.y += looklist;
		}
		if (k == KeyEvent.VK_UP) {
			// increase lookup.z to rotate the camera at z axis
			counter++;
			lookup.z += looklist;

		}
		Camera.updateCamera(lookat, lookfrom, lookup);
		// updateCemara for the new lookat, lookfrom, and lookup values
		f.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
