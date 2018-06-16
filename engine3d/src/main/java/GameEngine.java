import java.io.*;
import static org.lwjgl.glfw.GLFW.*;

class GameEngine {
    private GameState state;
    private GraphicsSystem graphicsSystem;
    private Cube testCube;

    private final float OPTIMAL_DT = 1000.0f/60.0f; // 60 frames per 1000 milliseconds
    //public float fps = 0;

    GameEngine() {
        state = GameState.INIT;
        graphicsSystem = new GraphicsSystem();
        testCube = new Cube();
    }

    public void run() {
        System.loadLibrary("renderdoc"); // extra debugging software library for windows
        graphicsSystem.init();

        //drawables must be initalized after graphics system is initialized
        testCube.load();

        long startTime = getMillisTime();
        long time;
        long prevTime = 0;
        long deltaTime;
        long accumulator = 0;
        state = GameState.LOOP;
        while (state == GameState.LOOP && !glfwWindowShouldClose(graphicsSystem.window)) {
            time = getMillisTime() - startTime;
            deltaTime = time - prevTime;
            accumulator += deltaTime;

            //int framecount = 0;
            while (accumulator >= OPTIMAL_DT && !glfwWindowShouldClose(graphicsSystem.window)) {
                graphicsSystem.update();
                graphicsSystem.draw(testCube);
                glfwPollEvents();

                accumulator -= OPTIMAL_DT;
                //framecount++;
            }

            //fps = framecount / ((getMillisTime() - startTime) - time);
            prevTime = time;
        }

        state = GameState.EXIT;
        graphicsSystem.exit();
        System.exit(0);
    }

    public static void errorExit(String error) {
        writeLogLn(error);
        System.exit(1);
    }

    public static long getMillisTime() {
        return System.nanoTime() / 1000000;
    }

    private static boolean append = false;
    public static void writeLog(String inStr) {
        try {
            FileWriter logFile = new FileWriter("log.txt", append);
            append = true;
            BufferedWriter logBuffer = new BufferedWriter(logFile);
            logBuffer.write(inStr);
            logBuffer.close();
            logFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLogLn(String inStr) {
        try {
            FileWriter logFile = new FileWriter("log.txt", append);
            append = true;
            BufferedWriter logBuffer = new BufferedWriter(logFile);
            logBuffer.write(inStr + "\n");
            logBuffer.close();
            logFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
