package Graphics3d;

public class Transformation {
	static float X = 0;
	static float Y = 0;
	static float Z = 0;

	public static void rotation(char axis, double degrees, float[] points) {

		double angle = Math.PI * degrees / 180;
		double temp;

		for (int i = 0; i < points.length; i += 3) {
			if (axis == 'z') {
				// rotate at z axis
				temp = points[i];
				points[i] = (float) (points[i] * Math.cos(angle) - points[i + 1] * Math.sin(angle));
				points[i + 1] = (float) (temp * Math.sin(angle) + points[i + 1] * Math.cos(angle));
			}
			if (axis == 'y') {
				// rotate at y axis
				temp = points[i + 2];
				points[i + 2] = (float) (points[i + 2] * Math.cos(angle) - points[i] * Math.sin(angle));
				points[i] = (float) (temp * Math.sin(angle) + points[i] * Math.cos(angle));
				Z = points[i + 2];
			}
			if (axis == 'x') {
				// rotate at x axis
				temp = points[i + 1];
				points[i + 1] = (float) (points[i + 1] * Math.cos(angle) - points[i + 2] * Math.sin(angle));
				points[i + 2] = (float) (temp * Math.sin(angle) + points[i + 2] * Math.cos(angle));
				Z = points[i + 2];
			}
		}
	}
}
