package fr.ensma.lias.jerboa.embeddings;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

// TODO: Maxime tu dois continuer/remodeler cette classe selon tes besoins
public class Vec3 {

  private float x, y, z;

  public Vec3(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vec3(Vec3 v) {
    this.x = v.x();
    this.y = v.y();
    this.z = v.z();
  }

  public float x() { return x; }
  public float y() { return y; }
  public float z() { return z; }

  public Vec3 addn(float dx, float dy, float dz) {
    return new Vec3(x + dx, y + dy, z + dz);
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

  // TODO: implémenter un getter pour le vecteur normal d'une face
}
