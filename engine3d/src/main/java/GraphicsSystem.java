import org.lwjgl.opengl.GL;

import java.io.*;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GraphicsSystem {
    final private int SAMPLING = 4;
    public long window;

    final int windowWidth = 1280;
    final int windowHeight = 720;
    final float aspectRatio = ((float)(windowWidth))/((float)(windowHeight));

    private Matrix4x4 projection;
    private Matrix4x4 view;
    private Matrix4x4 pv;

    public void init() {
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwWindowHint(GLFW_SAMPLES, SAMPLING); // multisampling
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(windowWidth, windowHeight, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); // enable v-sync

        GL.createCapabilities(); // the key

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);
        glClearColor(0.0f, 0.0f, 1.0f, 1.0f);

        projection = Matrix4x4.getProjection(45,aspectRatio,0.1f, 100.0f);
        view = new Matrix4x4(1);
        pv = projection.times(view);
    }

    public void draw(Drawable object) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        object.draw(pv);
        glfwSwapBuffers(window);
    }

    public void exit() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
    }

    public static int compileShaders(String vertexShader, String fragmentShader) {
        int vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
        int fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);

        // read in vertex shader
        String vertexShaderSource = "";
        try {
            FileReader vsFileReader = new FileReader(vertexShader);
            BufferedReader vsBuffReader = new BufferedReader(vsFileReader);
            String nextLine = vsBuffReader.readLine();
            while (nextLine != null) {
                vertexShaderSource += nextLine + "\n";
                nextLine = vsBuffReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // read in fragment shader
        String fragmentShaderSource = "";
        try {
            FileReader fsFileReader = new FileReader(fragmentShader);
            BufferedReader fsBuffReader = new BufferedReader(fsFileReader);
            String nextLine = fsBuffReader.readLine();
            while (nextLine != null) {
                fragmentShaderSource += nextLine + "\n";
                nextLine = fsBuffReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // compile vertex shader and check for errors
        glShaderSource(vertexShaderID, vertexShaderSource);
        glCompileShader(vertexShaderID);

        int[] numLogChars = new int[1];
        glGetShaderiv(vertexShaderID, GL_INFO_LOG_LENGTH, numLogChars);
        if (numLogChars[0] > 1) {
            String errormsg = glGetShaderInfoLog(vertexShaderID);
            GameEngine.writeLogLn("begin vertex shader log");
            GameEngine.writeLogLn(errormsg);
            GameEngine.writeLogLn("end vertex shader log");
        }

        // compile fragment shader and check for errors
        glShaderSource(fragmentShaderID, fragmentShaderSource);
        glCompileShader(fragmentShaderID);

        glGetShaderiv(fragmentShaderID, GL_INFO_LOG_LENGTH, numLogChars);
        if (numLogChars[0] > 1) {
            String errormsg = glGetShaderInfoLog(fragmentShaderID);
            GameEngine.writeLogLn("begin fragment shader log");
            GameEngine.writeLogLn(errormsg);
            GameEngine.writeLogLn("end fragment shader log");
        }

        // link shaders
        int programID = glCreateProgram();
        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);
        glLinkProgram(programID);

        // check linking
        glGetShaderiv(programID, GL_INFO_LOG_LENGTH, numLogChars);
        if (numLogChars[0] > 1) {
            String errormsg = glGetProgramInfoLog(programID);
            GameEngine.writeLogLn("begin program log");
            GameEngine.writeLogLn(errormsg);
            GameEngine.writeLogLn("end program log");
        }

        glDetachShader(programID, vertexShaderID);
        glDetachShader(programID,fragmentShaderID);
        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);

        return programID;
    }
}
