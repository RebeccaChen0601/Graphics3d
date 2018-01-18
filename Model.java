package Graphics3d;

public class Model {

	double x, y, z;

	public Model() {

	}

	public Model(double x, double y, double z) {
		setX(x);
		setY(y);
		setZ(z);
	}

	private void setZ(double z2) {
		z = z2;
	}

	private void setY(double y2) {
		y = y2;
	}

	private void setX(double x2) {
		x = x2;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
}
