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

        GameObject obj1 = new GameObject("obj 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/imgs/ryu.jpg")));
        this.addGameObjectToScene(obj1);

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
