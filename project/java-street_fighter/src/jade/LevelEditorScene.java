package src.jade;

import org.joml.Vector2f;

import src.components.Sprite;
import src.components.SpriteRenderer;
import src.components.Spritesheet;
import src.util.AssetPool;

public class LevelEditorScene extends Scene {
    public LevelEditorScene(){
    }
    @Override
    public void init(){
        loadResource();
        this.camera = new Camera(new Vector2f(-250, 0));

        Spritesheet sprites = AssetPool.getSpritesheet("assets/imgs/ryu.jpg");

        GameObject obj1 = new GameObject("obj 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        this.addGameObjectToScene(obj1);
    }
    private void loadResource(){
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/imgs/ryu.jpg",
                                new Spritesheet(AssetPool.getTexture("assets/imgs/ryu.jpg"),
                                                64, 64, 5, 0));
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
