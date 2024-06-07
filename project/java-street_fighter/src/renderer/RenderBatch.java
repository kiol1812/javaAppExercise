package src.renderer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import org.joml.Vector4f;

import src.components.SpriteRenderer;
import src.jade.Window;

public class RenderBatch {
    private final int POS_SIZE = 2;
    private final int COLOR_SIZE = 4;
    private final int POS_OFFSET = 0;
    private final int COLOR_OFFSET = POS_OFFSET+POS_SIZE*Float.BYTES;
    private final int VERTEX_SIZE = 6;
    private final int VERTEX_SIZE_BYTES = VERTEX_SIZE*Float.BYTES;

    private SpriteRenderer[] sprites;
    private int numSprites;
    private boolean hasRoom;
    private float[] vertices;

    private int vaoID, vboID;
    private int maxBatchSize;
    private Shader shader;

    public RenderBatch(int maxBatchSize){
        shader = new Shader("assets/shaders/default.glsl");
        shader.compile();
        this.sprites = new SpriteRenderer[maxBatchSize];
        this.maxBatchSize = maxBatchSize;

        vertices = new float[maxBatchSize*4*VERTEX_SIZE]; //4 vertices quads
        this.numSprites = 0;
        this.hasRoom = true;
    }
    public void start(){
        //generate and bind a vio(Vertex Array Object)
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
        //allocate space for vertices
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertices.length*Float.BYTES, GL_DYNAMIC_DRAW);
        // create and upload indices buffer
        int eboID = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
        //enable the buffer attribute points // ! 進度到這邊---- 4:25:51
        glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);
        glEnableVertexAttribArray(0);
        
        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);
        glEnableVertexAttribArray(1);
    }
    public void addSprite(SpriteRenderer spr){
        int index = this.numSprites;
        this.sprites[index] = spr;
        this.numSprites++;

        loadVertexProperties(index);

        if(numSprites>=this.maxBatchSize){
            this.hasRoom = false;
        }
    }
    public void render(){
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        shader.use();
        shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        
        glDrawElements(GL_TRIANGLES, this.numSprites*6, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        shader.detach();
    }
    private void loadVertexProperties(int index){
        SpriteRenderer sprite = this.sprites[index];

        int offset = index*4*VERTEX_SIZE;
        Vector4f color = sprite.getColor();

        float xAdd=1.0f, yAdd=1.0f;
        for(int i=0; i<4; i++){
            if(i==1) yAdd=0.0f;
            else if(i==2) xAdd=0.0f;
            else if(i==3) yAdd=1.0f;

            vertices[offset] = sprite.gameObject.transform.position.x+(xAdd*sprite.gameObject.transform.scale.x);
            vertices[offset+1] = sprite.gameObject.transform.position.y+(yAdd*sprite.gameObject.transform.scale.y);

            vertices[offset+2] = color.x;
            vertices[offset+3] = color.y;
            vertices[offset+4] = color.z;
            vertices[offset+5] = color.w;

            offset+=VERTEX_SIZE;
        }
    }
    private int[] generateIndices(){
        int[] elements = new int[6*maxBatchSize];
        for(int i=0; i<maxBatchSize; i++) loadElementIndices(elements, i);
        return elements;
    }
    private void loadElementIndices(int[] elements, int index){
        int offsetArrayIndex = 6*index;
        int offset = 4*index;

        elements[offsetArrayIndex] = offset+3;
        elements[offsetArrayIndex+1] = offset+2;
        elements[offsetArrayIndex+2] = offset+0;
        
        elements[offsetArrayIndex+3] = offset+0;
        elements[offsetArrayIndex+4] = offset+2;
        elements[offsetArrayIndex+5] = offset+1;
    }
    public boolean hasRoom(){
        return this.hasRoom;
    }
}
