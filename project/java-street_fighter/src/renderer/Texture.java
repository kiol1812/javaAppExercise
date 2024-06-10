package src.renderer;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

public class Texture {
    private String filepath;
    private int texID;
    private int width, height;
    public Texture(String filepath){
        this.filepath = filepath;
        texID = glGenTextures(); // generate testure on GPU
        glBindTexture(GL_TEXTURE_2D, texID);
        //set texture parameters, Repeat img in both direction
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST); //when stretching the img, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST); //when shinking an img, pixelate

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        stbi_set_flip_vertically_on_load(true);
        ByteBuffer img = stbi_load(filepath, width, height, channels, 0);
        if(img!=null){
            this.width = width.get(0);
            this.height = height.get(0);

            if(channels.get(0)==3){
                glTexImage2D(GL_TEXTURE_2D, 0,  GL_RGB, width.get(0), height.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, img);
            }else if(channels.get(0)==4){
                glTexImage2D(GL_TEXTURE_2D, 0,  GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, img);
            }else assert false:"Error: (Texture) Unknow number of channels '"+channels.get(0)+"'";
        }else{
            assert false:"Error: (Texture) Could not load image '"+filepath+"'";
        }
        stbi_image_free(img);
    }
    public void bind(){
        glBindTexture(GL_TEXTURE_2D, texID);
    }
    public void unbind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getWidth(){ return this.width; }
    public int getHeight(){ return this.height; }
}
