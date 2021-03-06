import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_UNSIGNED_INT_8_8_8_8;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class Mesh {
    private float[] vertices;
    private int[] vertIndicies;
    private float[] uvCoords;
    private int[] texture;
    private int textureWidth;
    private int textureHeight;

    public int vertexBufferID;
    public int uvBufferID;
    public int textureID;

    Mesh(int type) {
        switch (type) {
            case 1:
                vertices = new float[]{
                        // triangle 1: top left
                        -1.0f, -1.0f, 0.0f,
                        -1.0f, 1.0f, 0.0f,
                        1.0f, 1.0f, 0.0f,
                        // triangle 2: bottom right
                        -1.0f, -1.0f, 0.0f,
                        1.0f, -1.0f, 0.0f,
                        1.0f, 1.0f, 0.0f
                };

                uvCoords = new float[]{
                        // triangle 1: top left
                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 1.0f,
                        // triangle 2: bottom right
                        0.0f, 0.0f,
                        1.0f, 0.0f,
                        1.0f, 1.0f
                };
                break;
            case 2:
                vertices = new float[]{
                        -1.0f, -1.0f, -1.0f, // left bottom #4
                        -1.0f, -1.0f, 1.0f,
                        -1.0f, 1.0f, 1.0f,
                        1.0f, 1.0f, -1.0f, // back top
                        -1.0f, -1.0f, -1.0f,
                        -1.0f, 1.0f, -1.0f,
                        1.0f, -1.0f, 1.0f, // bottom back
                        -1.0f, -1.0f, -1.0f,
                        1.0f, -1.0f, -1.0f,
                        1.0f, 1.0f, -1.0f, //
                        1.0f, -1.0f, -1.0f,
                        -1.0f, -1.0f, -1.0f,
                        -1.0f, -1.0f, -1.0f,
                        -1.0f, 1.0f, 1.0f,
                        -1.0f, 1.0f, -1.0f,
                        1.0f, -1.0f, 1.0f,
                        -1.0f, -1.0f, 1.0f,
                        -1.0f, -1.0f, -1.0f,
                        -1.0f, 1.0f, 1.0f, // front bottom
                        -1.0f, -1.0f, 1.0f,
                        1.0f, -1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f,
                        1.0f, -1.0f, -1.0f,
                        1.0f, 1.0f, -1.0f,
                        1.0f, -1.0f, -1.0f,
                        1.0f, 1.0f, 1.0f,
                        1.0f, -1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f,
                        1.0f, 1.0f, -1.0f,
                        -1.0f, 1.0f, -1.0f,
                        1.0f, 1.0f, 1.0f,
                        -1.0f, 1.0f, -1.0f,
                        -1.0f, 1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f,
                        -1.0f, 1.0f, 1.0f,
                        1.0f, -1.0f, 1.0f
                };

                uvCoords = new float[]{
                        0.000059f, 1.0f - 0.000004f,
                        0.000103f, 1.0f - 0.336048f,
                        0.335973f, 1.0f - 0.335903f,
                        1.0000f, 1.0f - 0.000013f,
                        0.667979f, 1.0f - 0.335851f,
                        0.999958f, 1.0f - 0.336064f,
                        0.667979f, 1.0f - 0.335851f,
                        0.336024f, 1.0f - 0.671877f,
                        0.667969f, 1.0f - 0.671889f,
                        1.0000f, 1.0f - 0.000013f,
                        0.668104f, 1.0f - 0.000013f,
                        0.667979f, 1.0f - 0.335851f,
                        0.000059f, 1.0f - 0.000004f,
                        0.335973f, 1.0f - 0.335903f,
                        0.336098f, 1.0f - 0.000071f,
                        0.667979f, 1.0f - 0.335851f,
                        0.335973f, 1.0f - 0.335903f,
                        0.336024f, 1.0f - 0.671877f,
                        1.000000f, 1.0f - 0.671847f,
                        0.999958f, 1.0f - 0.336064f,
                        0.667979f, 1.0f - 0.335851f,
                        0.668104f, 1.0f - 0.000013f,
                        0.335973f, 1.0f - 0.335903f,
                        0.667979f, 1.0f - 0.335851f,
                        0.335973f, 1.0f - 0.335903f,
                        0.668104f, 1.0f - 0.000013f,
                        0.336098f, 1.0f - 0.000071f,
                        0.000103f, 1.0f - 0.336048f,
                        0.000004f, 1.0f - 0.671870f,
                        0.336024f, 1.0f - 0.671877f,
                        0.000103f, 1.0f - 0.336048f,
                        0.336024f, 1.0f - 0.671877f,
                        0.335973f, 1.0f - 0.335903f,
                        0.667969f, 1.0f - 0.671889f,
                        1.000000f, 1.0f - 0.671847f,
                        0.667979f, 1.0f - 0.335851f
                };
        }

        File textureFile = new File("texture.png");
        BufferedImage textureBuffer;
        try {
            textureBuffer = ImageIO.read(textureFile);
            textureWidth = textureBuffer.getWidth();
            textureHeight = textureBuffer.getHeight();
            if ((textureWidth % 2 != 0) && (textureHeight % 2 != 0)) {
                throw new Exception();
            }

            texture = new int[textureBuffer.getWidth()*textureBuffer.getHeight()];
            textureBuffer.getRGB(0, 0,
                                textureWidth, textureHeight,
                                    texture,0, textureWidth);
            // mirror image horizontally and bitshift to convert from ARGB to RGB
            for (int r = 0; r < textureHeight/2; r++) {
                for (int c = 0; c < textureWidth; c++) {
                    int topIndex = r*textureWidth + c;
                    int bottomIndex = (textureHeight - r - 1)*textureWidth + c;
                    int temp = texture[topIndex];
                    texture[topIndex] = texture[bottomIndex];
                    texture[bottomIndex] = temp;
                    texture[topIndex] <<= 8;
                    texture[bottomIndex] <<= 8;
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            GameEngine.errorExit("Mesh: triangle.bmp could not be found");
        } catch (Exception e) {
            GameEngine.errorExit("Mesh: image is not even in width and height");
        }

    }

    void loadData() {
        int[] temp = new int[1];
        glGenBuffers(temp);
        vertexBufferID = temp[0];
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glGenBuffers(temp);
        uvBufferID = temp[0];
        glBindBuffer(GL_ARRAY_BUFFER, uvBufferID);
        glBufferData(GL_ARRAY_BUFFER, uvCoords, GL_STATIC_DRAW);

        glGenTextures(temp);
        textureID = temp[0];
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(GL_TEXTURE_2D, 0,
                    GL_RGB, textureWidth, textureHeight,
                    0, GL_RGBA,  GL_UNSIGNED_INT_8_8_8_8, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    }

    int getNumTris() {
        return (vertices.length/3);
    }
}