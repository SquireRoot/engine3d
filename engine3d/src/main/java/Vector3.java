public class Vector3 {
    public float x;
    public float y;
    public float z;

    public Vector3() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3(float initx, float inity, float initz) {
        x = initx;
        y = inity;
        z = initz;
    }

    public float magnitude() {
        return (float)Math.sqrt(x*x+y*y+z*z);
    }

    public Vector3 normalized() {
        Vector3 out = new Vector3();
        float mag = magnitude();
        if (mag != 0) {
            out.x = x / mag;
            out.y = y / mag;
            out.z = z / mag;
        }
        return out;
    }

    public float dot(Vector3 vec) {
        return x*vec.x + y*vec.y + z*vec.z;
    }

    public Vector3 cross(Vector3 vec) {
        Vector3 out = new Vector3();
        out.x = y*vec.z - z*vec.y;
        out.y = z*vec.x - x*vec.z;
        out.z = x*vec.y - y*vec.x;
        return out;
    }

    public Vector3 negative() {
        Vector3 out = new Vector3();
        out.x = -x;
        out.y = -y;
        out.z = -z;
        return out;
    }
}
