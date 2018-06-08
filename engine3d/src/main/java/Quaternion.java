public class Quaternion {
    float x;
    float y;
    float z;
    float w;

    Vector3 axis;
    float angle;

    Matrix4x4 rotationMatrix;

    Quaternion(Vector3 axisInit, float angleInit) {
        axis = axisInit.normalized();
        rotationMatrix = new Matrix4x4(1);
        angle = angleInit;
        if (axis.x != 0 || axis.y != 0 || axis.z != 0) {
            x = (float)(axis.x * Math.sin(angle/2));
            y = (float)(axis.y * Math.sin(angle/2));
            z = (float)(axis.z * Math.sin(angle/2));
            w = (float)(Math.cos(angle/2));

            rotationMatrix.matrix[0] = 1-2*(y*y+z*z);
            rotationMatrix.matrix[1] = 2*(x*y-z*w);
            rotationMatrix.matrix[2] = 2*(x*z+y*w);

            rotationMatrix.matrix[4] = 2*(x*y+z*w);
            rotationMatrix.matrix[5] = 1-2*(x*x+z*z);
            rotationMatrix.matrix[6] = 2*(y*z-x*w);

            rotationMatrix.matrix[8] = 2*(x*z-y*w);
            rotationMatrix.matrix[9] = 2*(y*z+x*w);
            rotationMatrix.matrix[10] = 1-2*(x*x+y*y);
        } else {
            x = 0;
            y = 0;
            z = 0;
            w = 0;
        }
    }
}
