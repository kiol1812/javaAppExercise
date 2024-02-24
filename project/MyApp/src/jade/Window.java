package jade;

import org.lwjgl.Version;

// import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
// import org.lwjgl.system.*;

import util.Time;

// import java.nio.*;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
// import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private int width, height;
    private String title;
    public float r, g, b, a;
    private long glfwWindow;

    private static Window window = null;

    private static Scene currentScene;

    private Window(){
        this.width = 1920;
        this.height= 1080;
        this.title = "title test";
        r=1.0f;g=1.0f;b=1.0f;a=1.0f;
    }

    public static void changeScene(int newScene){
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                break;
            case 1:
                currentScene = new LevelScene();
                break;
            default:
                assert false:"Unkown scenr '"+newScene+"'";
                break;
        }
    }

    public static Window get(){
        if(Window.window==null){
            Window.window = new Window();
        }
        return Window.window;
    }
    public void run(){
        System.out.println("Hello LWJGL"+Version.getVersion()+"!");
        init();
        loop();
        
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();
        if(!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if(glfwWindow==NULL) throw new IllegalStateException("Failed to create the GLFW window.");

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallBack);

        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(1);
        glfwShowWindow(glfwWindow);
        GL.createCapabilities();

        window.changeScene(0);
    }
    public void loop(){
        float beginTime = Time.getTime();
        float endTime;
        float dt = -1.0f;

        while(!glfwWindowShouldClose(glfwWindow)){
            glfwPollEvents();
            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            // if(KeyListener.isKeyPressed(GLFW_KEY_SPACE)) System.out.println("Space key is pressed");

            if(dt>=0) currentScene.updata(dt);

            glfwSwapBuffers(glfwWindow);

            endTime  = Time.getTime();
            dt = endTime-beginTime;
            beginTime = endTime;
        }
    }
}
