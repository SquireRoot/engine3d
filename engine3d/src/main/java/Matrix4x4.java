

public class Matrix4x4 {
    // matrix array indicies
    // 0  1  2  3
    // 4  5  6  7
    // 8  9  10 11
    // 12 13 14 15
    float[] matrix;

    public Matrix4x4() {
        matrix = new float[16];
    }

    public Matrix4x4(float[] init) {
        if (init.length == 16) {
            matrix = new float[16];
            for (int i = 0; i < 16; i++) {
                matrix[i] = init[i];
            }
        }
    }

    public Matrix4x4(int i) {
        matrix = new float[16];
        matrix[0] = i;
        matrix[5] = i;
        matrix[10] = i;
        matrix[15] = i;
    }

    public Matrix4x4 times(Matrix4x4 in) {
        float[] out = new float[16];
        // row 1
        out[0] = matrix[0]*in.matrix[0] + matrix[1]*in.matrix[4]
                + matrix[2]*in.matrix[8] + matrix[3]*in.matrix[12];
        out[1] = matrix[0]*in.matrix[1] + matrix[1]*in.matrix[5]
                + matrix[2]*in.matrix[9] + matrix[3]*in.matrix[13];
        out[2] = matrix[0]*in.matrix[2] + matrix[1]*in.matrix[6]
                + matrix[2]*in.matrix[10] + matrix[3]*in.matrix[14];
        out[3] = matrix[0]*in.matrix[3] + matrix[1]*in.matrix[7]
                + matrix[2]*in.matrix[11] + matrix[3]*in.matrix[15];
        // row 2
        out[4] = matrix[4]*in.matrix[0] + matrix[5]*in.matrix[4]
                + matrix[6]*in.matrix[8] + matrix[7]*in.matrix[12];
        out[5] = matrix[4]*in.matrix[1] + matrix[5]*in.matrix[5]
                + matrix[6]*in.matrix[9] + matrix[7]*in.matrix[13];
        out[6] = matrix[4]*in.matrix[2] + matrix[5]*in.matrix[6]
                + matrix[6]*in.matrix[10] + matrix[7]*in.matrix[14];
        out[7] = matrix[4]*in.matrix[3] + matrix[5]*in.matrix[7]
                + matrix[6]*in.matrix[11] + matrix[7]*in.matrix[15];
        // row 3
        out[8] = matrix[8]*in.matrix[0] + matrix[9]*in.matrix[4]
                + matrix[10]*in.matrix[8] + matrix[11]*in.matrix[12];
        out[9] = matrix[8]*in.matrix[1] + matrix[9]*in.matrix[5]
                + matrix[10]*in.matrix[9] + matrix[11]*in.matrix[13];
        out[10] = matrix[8]*in.matrix[2] + matrix[9]*in.matrix[6]
                + matrix[10]*in.matrix[10] + matrix[11]*in.matrix[14];
        out[11] = matrix[8]*in.matrix[3] + matrix[9]*in.matrix[7]
                + matrix[10]*in.matrix[11] + matrix[11]*in.matrix[15];
        // row 4
        out[12] = matrix[12]*in.matrix[0] + matrix[13]*in.matrix[4]
                + matrix[14]*in.matrix[8] + matrix[15]*in.matrix[12];
        out[13] = matrix[12]*in.matrix[1] + matrix[13]*in.matrix[5]
                + matrix[14]*in.matrix[9] + matrix[15]*in.matrix[13];
        out[14] = matrix[12]*in.matrix[2] + matrix[13]*in.matrix[6]
                + matrix[14]*in.matrix[10] + matrix[15]*in.matrix[14];
        out[15] = matrix[12]*in.matrix[3] + matrix[13]*in.matrix[7]
                + matrix[14]*in.matrix[11] + matrix[15]*in.matrix[15];

        return new Matrix4x4(out);
    }

    // 0  1  2  3
    // 4  5  6  7
    // 8  9  10 11
    // 12 13 14 15
    public static Matrix4x4 getProjection(float width, float height,
                                       float znear, float zfar) {
        float[] projection = new float[16];

        // http://www.songho.ca/opengl/gl_projectionmatrix.html
        projection[0] = 2 * (znear/width);
        projection[5] = 2 * (znear/height);
        projection[10] = -(zfar + znear)/(zfar - znear);
        projection[11] = (-2 * zfar * znear)/(zfar - znear);
        projection[14] = -1;

        return new Matrix4x4(projection);
    }

    public static Matrix4x4 getOrtho(float width, float height,
                                  float znear, float zfar) {
        float[] projection = new float[16];

        //http://www.songho.ca/opengl/gl_projectionmatrix.html
        projection[0] = 2 / width;
        projection[5] = 2 / height;
        projection[10] = -2 / (zfar - znear);
        projection[11] = -(zfar + znear) / (zfar - znear);
        projection[15] = 1;

        return new Matrix4x4(projection);
    }

    public static Matrix4x4 translation(Vector3 vec) {
        Matrix4x4 out = new Matrix4x4(1);
        out.matrix[3] = vec.x;
        out.matrix[7] = vec.y;
        out.matrix[11] = vec.z;
        return out;
    }

    public static Matrix4x4 scale(Vector3 vec) {
        Matrix4x4 out = new Matrix4x4(1);
        out.matrix[0] = vec.x;
        out.matrix[5] = vec.y;
        out.matrix[10] = vec.z;
        return out;
    }
}
