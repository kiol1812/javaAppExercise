package jade;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import renderer.Shader;
import util.Time;

import java.nio.*;

import static org.lwjgl.opengl.GL30.glBindVertexArray;;

public class LevelEditorScene extends Scene {
    private float[] vertexArray = {
        //position, color
        100.5f,   0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, //bottom right 0
        0.5f,   100.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, //top left     1
        100.5f, 100.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, //top right    2
        0.5f,     0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, //botton left  3
    };
    private int[] elementArray = {//must counter-clockwise order
        2, 1, 0, //top   right triangle
        0, 1, 3, //bottom left triangle
    };
    private int vaoID, vboID, eboID;

    public LevelEditorScene(){
    }
    
    private Shader defaultShader;

    @Override
    public void init(){
        this.camera = new Camera(new Vector2f());
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();
        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        int positionSize = 3; //xyx
        int colorSize = 4;   //rgba
        int floatSizeBytes = 4;
        int vertexSizeBytes= (positionSize+colorSize)*floatSizeBytes;
        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0); // index:0 => glsl file> location 0: pos
        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize*floatSizeBytes);
        glEnableVertexAttribArray(1); // index:1 => glsl file> location 1: color
    }

    @Override
    public void updata(float dt) {
        // camera.position.x-=dt*50.0f;
        defaultShader.use();
        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());
        defaultShader.uploadFloat("uTime", Time.getTime());

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        defaultShader.detach();
    }
}
