package Graphics3d;

public class Camera {
	static double[][] cameraMatrix = new double[3][4];

	public static void updateCamera(Model lookAt, Model lookFrom, Model lookUp) {
		Model Vx, Vy, Vz;
		double zlength = 0.0, xlength = 0.0;

		Vz = Minus(lookAt, lookFrom);
		zlength = Nomalize(Vz);
		//make the values present between -1 to 1
		Vx = Cross(Vz, lookUp);
		//get the cross product between Vz and lookUp
		xlength = Nomalize(Vx);
		Vy = Cross(Vx, Vz);
		//get the cross product between Vx and Vz
		

		cameraMatrix[0][0] = Vx.x;
		cameraMatrix[0][1] = Vx.y;
		cameraMatrix[0][2] = Vx.z;
		cameraMatrix[0][3] = -Dot(Vx, lookFrom);
		cameraMatrix[1][0] = Vy.x;
		cameraMatrix[1][1] = Vy.y;
		cameraMatrix[1][2] = Vy.z;
		cameraMatrix[1][3] = -Dot(Vy, lookFrom);
		cameraMatrix[2][0] = Vz.x;
		cameraMatrix[2][1] = Vz.y;
		cameraMatrix[2][2] = Vz.z;
		cameraMatrix[2][3] = -Dot(Vz, lookFrom);
		//create camera matrix

	}

	public static double Nomalize(Model a) {
		double length, scale;
		length = Dot(a, a);
		length = Math.sqrt(length);
		if (length != 0.0) {
			scale = 1.0 / length;
			a.x *= scale;
			a.y *= scale;
			a.z *= scale;
		}
		return length;
	}

	public static Model Cross(Model a, Model b) {
		Model result = new Model();
		result.x = a.y * b.z - a.z * b.y;
		result.y = a.z * b.x - a.x * b.z;
		result.z = a.x * b.y - a.y * b.x;
		return result;
	}

	public static double Dot(Model a, Model b) {
		return (a.x * b.x + a.y * b.y + a.z * b.z);
	}

	public Model Plus(Model a, Model b) {
		Model result = new Model();
		result.x = a.x + b.x;
		result.y = a.y + b.y;
		result.z = a.z + b.z;
		return result;
	}

	public static Model Minus(Model a, Model b) {
		Model result = new Model();
		result.x = a.x - b.x;
		result.y = a.y - b.y;
		result.z = a.z - b.z;
		return result;
	}

}
