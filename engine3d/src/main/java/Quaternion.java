public class Quaternion {
    float x;
    float y;
    float z;
    float w;

    Vector3 axis;
    float angle;

    // 0  1  2  3
    // 4  5  6  7
    // 8  9  10 11
    // 12 13 14 15
    Matrix4x4 rotationMatrix;

    Quaternion(Vector3 axisInit, float angleInit) {
        axis = axisInit;
        angle = (float)((angleInit)*(Math.PI/180));
        x = (float)(axis.x * Math.sin(angle/2));
        y = (float)(axis.y * Math.sin(angle/2));
        z = (float)(axis.z * Math.sin(angle/2));
        w = (float)(Math.cos(angle/2));

        rotationMatrix = new Matrix4x4();
        float s = (float) Math.sqrt(x*x+y*y+z*z+w*w);
        s = 1/(s*s);

        // using https://en.wikipedia.org/wiki/Quaternions_and_spatial_rotation
        rotationMatrix.matrix[0] = 1-2*s*(y*y+z*z);
        rotationMatrix.matrix[1] = 2*s*(x*y-z*w);
        rotationMatrix.matrix[2] = 2*s*(x*z+y*w);

        rotationMatrix.matrix[4] = 2*s*(x*y+z*w);
        rotationMatrix.matrix[5] = 1-2*s*(x*x+z*z);
        rotationMatrix.matrix[6] = 2*s*(y*z-x*w);

        rotationMatrix.matrix[8] = 2*s*(x*z-y*w);
        rotationMatrix.matrix[9] = 2*s*(y*z+x*w);
        rotationMatrix.matrix[10] = 1-2*s*(x*x+y*y);

        rotationMatrix.matrix[15] = 1;
    }
}
