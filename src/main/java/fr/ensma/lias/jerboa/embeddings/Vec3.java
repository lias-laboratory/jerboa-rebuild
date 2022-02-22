package fr.ensma.lias.jerboa.embeddings;

import java.util.List;

public class Vec3 {

	private float x, y, z;

	public Vec3() {}

	public Vec3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec3(double x, double y, double z) {
		this.x = (float) x;
		this.y = (float) y;
		this.z = (float) z;
	}

	public Vec3(Vec3 v) {
		this.x = v.x();
		this.y = v.y();
		this.z = v.z();
	}

	public float x() {
		return x;
	}

	public float y() {
		return y;
	}

	public float z() {
		return z;
	}

	public Vec3 addn(float dx, float dy, float dz) {
		return new Vec3(x + dx, y + dy, z + dz);
	}

	public void barycenter(List<Vec3> vecList) {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		for (Vec3 v : vecList) {
			this.x += v.x();
			this.y += v.y();
			this.z += v.z();
		}
		this.x /= vecList.size();
		this.y /= vecList.size();
		this.z /= vecList.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Vec3[");
		sb.append(x).append("; ").append(y).append("; ").append(z).append("]");
		return sb.toString();
	}

	public static Vec3 mid(Vec3 a, Vec3 b) {
		return new Vec3((a.x + b.x) / 2.f, (a.y + b.y) / 2.f, (a.z + b.z) / 2.f);
	}

	// Compute a position between two points A and B and balance it with a given
	// weight parameter.
	public static Vec3 segmentABByWeight(Vec3 a, Vec3 b, float weight) {
		return new Vec3(a.x + ((b.x - a.x) * weight), a.y + ((b.y - a.y) * weight),
				a.z + ((b.z - a.z) * weight));
	}

}
