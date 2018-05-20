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

    private Matrix4x4 projection;
    private Matrix4x4 view;
    private Matrix4x4 vp;

    public void init() {
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwWindowHint(GLFW_SAMPLES, SAMPLING); // multisampling
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(1280, 720, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); // enable v-sync

        GL.createCapabilities(); // the key

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);
        glClearColor(0.0f, 0.0f, 1.0f, 1.0f);

        projection = Matrix4x4.getOrtho(10,10,0, -10);
        view = new Matrix4x4(1);
        vp = view.times(projection);
    }

    public void draw(Drawable object) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        object.draw(vp);
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
