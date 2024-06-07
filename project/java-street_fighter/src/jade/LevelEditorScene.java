package src.jade;

import org.joml.Vector2f;
import org.joml.Vector4f;

import src.components.SpriteRenderer;

public class LevelEditorScene extends Scene {
    public LevelEditorScene(){
    }
    @Override
    public void init(){
        this.camera = new Camera(new Vector2f());

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