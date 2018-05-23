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

    public Vector3 normalized() {
        float magnitude = (float)Math.sqrt(x*x+y*y+z*z);
        Vector3 out = new Vector3();
        out.x = x/magnitude;
        out.y = y/magnitude;
        out.z = z/magnitude;
        return out;
    }
}
