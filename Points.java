package Graphics3d;

public class Points extends Object { // holds the x,y,z values for a point
	double x;
	double y;
	double z;

	public Points(double ix, double iy, double iz) {
		setX(ix);
		setY(iy);
		setZ(iz);
		
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

	public void setX(double ix) {
		x = ix;
	}

	public void setY(double iy) {
		y = iy;
	}

	public void setZ(double iz) {
		z = iz;
	}
	
	public int hashCode(){
		return (int)(x + y * 4 + z * 3);
	}
	
	public boolean equals(Object c){
		if(!(c instanceof Points)) 
			throw new  ClassCastException("category is incorrect");
		Points p = (Points)c;
		return (c == this|| (p.x == this.x && p.y == this.y && p.z == this.z))?true:false;
	}
}
