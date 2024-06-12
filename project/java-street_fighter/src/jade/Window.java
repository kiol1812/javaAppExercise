package src.jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

// import src.util.Time;

import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
// import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.NULL;;

public class Window {
    private int width, height;
    private String title;
    public float r, g, b, a;
    private static Window instance=null;
    private long glfwWindow;
    private static Scene currentScene=null;
    private Window(){
        this.height = 1080;
        this.width  = 1920;
        this.title = "main window";
        r=1.0f;g=1.0f;b=1.0f;a=1.0f;
    }
    public static void main(String args[]){
    }
    public static void changeScene(int newScene){
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                currentScene.start();
                break;
            case 1:
                currentScene = new LevelEditorScene();
                currentScene.init();
                currentScene.start();
                break;
            default:
                assert false : "Unknown scene '"+newScene+"'";
                break;
        }
    }
    public static Window get(){
        if(instance==null) instance=new Window();
        return instance;
    }
    public static Scene getScene(){
        return get().currentScene;
    }
    public void run(){
        System.out.println(Version.getVersion());

        init();
        loop();
        //Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    public void init(){
        //set up an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        //initilize GLFW
        if(!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        //configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        //create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, MemoryUtil.NULL, MemoryUtil.NULL);
        if(glfwWindow==NULL) throw new IllegalStateException("Failed to create the GLFW window.");

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallBack);

        glfwMakeContextCurrent(glfwWindow);//make the OpenGL context current
        glfwSwapInterval(1);//Enable v-sync
        glfwShowWindow(glfwWindow);//make the window visable
        GL.createCapabilities();//important

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA); //alpha

        instance.changeScene(0);
    }
    public void loop(){
        // float beginTime = Time.getTime();
        float beginTime = (float)glfwGetTime();
        float endTime;
        float dt = -1.0f;

        while(!glfwWindowShouldClose(glfwWindow)){
            glfwPollEvents();//poll events
            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if(KeyListener.isKeyPressed(GLFW_KEY_SPACE)) System.out.println("Space key is pressed");

            if(dt>=0) currentScene.updata(dt);

            glfwSwapBuffers(glfwWindow);

            // endTime  = Time.getTime();
            endTime  = (float)glfwGetTime();
            dt = endTime-beginTime;
            beginTime = endTime;
        }
    }
}
