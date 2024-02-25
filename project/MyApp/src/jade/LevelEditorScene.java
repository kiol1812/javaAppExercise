package jade;

import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import org.lwjgl.BufferUtils;
import java.nio.*;

import static org.lwjgl.opengl.GL30.glBindVertexArray;;

public class LevelEditorScene extends Scene {

    private String vertexShaderSrc = "#version 330 core\r\n" + //
                "layout (location=0) in vec3 aPos;\r\n" + //
                "layout (location=1) in vec4 aColor;\r\n" + //
                "\r\n" + //
                "out vec4 fColor;\r\n" + //
                "\r\n" + //
                "void main(){\r\n" + //
                "    fColor = aColor;\r\n" + //
                "    gl_Position = vec4(aPos, 1.0);\r\n" + //
                "}";
    private String fragmentShaderSrc="#version 330 core\r\n" + //
                "\r\n" + //
                "in vec4 fColor;\r\n" + //
                "\r\n" + //
                "out vec4 color;\r\n" + //
                "\r\n" + //
                "void main(){\r\n" + //
                "    color = fColor;\r\n" + //
                "}";
    private int vertexID, fragmentID, shaderProgram;

    private float[] vertexArray = {
        //position, color
         0.5f, -0.5f,  0.0f,    1.0f, 0.0f, 0.0f, 1.0f, //bottom right 0
        -0.5f,  0.5f,  0.0f,    0.0f, 1.0f, 0.0f, 1.0f, //top left     1
         0.5f,  0.5f,  0.0f,    0.0f, 0.0f, 1.0f, 1.0f, //top right    2
        -0.5f, -0.5f,  0.0f,    1.0f, 1.0f, 0.0f, 1.0f, //botton left  3
    };
    private int[] elementArray = {//must counter-clockwise order
        2, 1, 0, //top   right triangle
        0, 1, 3, //bottom left triangle
    };
    private int vaoID, vboID, eboID;

    public LevelEditorScene(){
    }

    @Override
    public void init(){
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertexShaderSrc);
        glCompileShader(vertexID);
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if(success == GL_FALSE){
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaulthader.glsl'\n\tVertex shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false:"";
        }
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID, fragmentShaderSrc);
        glCompileShader(fragmentID);
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if(success == GL_FALSE){
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaulthader.glsl'\n\tFragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false:"";
        }

        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if(success == GL_FALSE){
            int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaulthader.glsl'\n\tLinking of shaders failed.");
            System.out.println(glGetProgramInfoLog(shaderProgram, len));
            assert false:"";
        }

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
        glUseProgram(shaderProgram);
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        glUseProgram(0);
    }
}
