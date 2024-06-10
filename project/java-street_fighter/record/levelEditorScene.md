
before reanderbatch
```java
package src.jade;

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
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import src.components.FontRenderer;
import src.components.SpriteRenderer;
import src.renderer.Shader;
import src.renderer.Texture;
import src.util.Time;

import java.nio.*;

import static org.lwjgl.opengl.GL30.glBindVertexArray;;

public class LevelEditorScene extends Scene {
    private float[] vertexArray = {
        //position,           color,                   UV coordinates
        100.5f,   0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,  1, 1, //bottom right 0
        0.5f,   100.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,  0, 0, //top left     1
        100.5f, 100.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,  1, 0, //top right    2
        0.5f,     0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,  0, 1, //botton left  3
    };
    private int[] elementArray = {//must counter-clockwise order
        2, 1, 0, //top   right triangle
        0, 1, 3, //bottom left triangle
    };
    private int vaoID, vboID, eboID;

    public LevelEditorScene(){
    }
    
    private Shader defaultShader;
    private Texture testTexture;

    GameObject testObj;
    private boolean firstTime=true;

    @Override
    public void init(){
        System.out.println("create 1");
        this.testObj = new GameObject("test object");
        this.testObj.addComponent(new SpriteRenderer());
        this.testObj.addComponent(new FontRenderer());
        this.addGameObjectToScene(this.testObj);

        this.camera = new Camera(new Vector2f());
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();
        // this.testTexture = new Texture("assets/imgs/character.png");
        this.testTexture = new Texture("assets/imgs/ryu.jpg");

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
        int uvSize = 2;
        int vertexSizeBytes = (positionSize+colorSize+uvSize)*Float.BYTES;
        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0); // index:0 => glsl file> location 0: pos
        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize*Float.BYTES);
        glEnableVertexAttribArray(1); // index:1 => glsl file> location 1: color
        glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (positionSize+colorSize)*Float.BYTES);
        glEnableVertexAttribArray(2);
    }

    @Override
    public void updata(float dt) {
        // camera.position.x-=dt*50.0f;
        defaultShader.use();
        
        defaultShader.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0);
        testTexture.bind();
        
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

        if(firstTime){
            System.out.println("create 2");
            GameObject go = new GameObject("game test 2");
            go.addComponent(new SpriteRenderer());
            this.addGameObjectToScene(go);
            firstTime = false;
        }

        for(GameObject GO:this.gameObjects){
            GO.update(dt);
        }
    }
}

```

before texture batching
```java
package src.jade;

import org.joml.Vector2f;
import org.joml.Vector4f;

import src.components.SpriteRenderer;
import src.util.AssetPool;

public class LevelEditorScene extends Scene {
    public LevelEditorScene(){
    }
    @Override
    public void init(){
        this.camera = new Camera(new Vector2f(-250, 0));

        int xOffset = 10, yOffset=10;

        float totalWidth=(float)(600-xOffset*2), totalHeight=(float)(300-yOffset*2);
        float sizeX=totalWidth/100.0f, sizeY=totalHeight/100.0f;

        for(int x=0; x<100; x++){
            for(int y=0; y<100; y++){
                float xPos=xOffset+(x*sizeX);
                float yPos=yOffset+(y*sizeY);

                GameObject GO = new GameObject("Obj"+x+""+y, new Transform(new Vector2f(xPos, yPos), new Vector2f(sizeX, sizeY)));
                GO.addComponent(new SpriteRenderer(new Vector4f(xPos/totalWidth, yPos/totalHeight, 1, 1)));
                this.addGameObjectToScene(GO);
            }
        }
        loadResource();
    }
    private void loadResource(){
        AssetPool.getShader("assets/shaders/default.glsl");
    }

    @Override
    public void updata(float dt) {
        // System.out.println("FPS:"+(1.0f/dt));
        for(GameObject GO:this.gameObjects){
            GO.update(dt);
        }
        this.renderer.render();
    }
}
```