package src.jade;

import org.joml.Vector2f;

// import src.components.Sprite;
import src.components.SpriteRenderer;
import src.components.Spritesheet;
import src.util.AssetPool;

public class LevelEditorScene extends Scene {
    private final String filePath="assets/imgs/idel.png";
    private GameObject obj1;
    private Spritesheet sprites;
    public LevelEditorScene(){
    }
    @Override
    public void init(){
        loadResource();
        this.camera = new Camera(new Vector2f(-250, 0));

        sprites = AssetPool.getSpritesheet(filePath);

        obj1 = new GameObject("obj 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        this.addGameObjectToScene(obj1);
    }
    private void loadResource(){
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet(filePath,
                                new Spritesheet(AssetPool.getTexture(filePath),
                                                128, 128, 5, 0));
    }

    private int spriteIndex=0;
    private float spriteFilpTime=0.2f;
    private float spriteFilpTimeLeft=0.0f;
    @Override
    public void updata(float dt) {
        // System.out.println("FPS:"+(1.0f/dt));
        spriteFilpTimeLeft-=dt;
        if(spriteFilpTimeLeft<=0){
            spriteFilpTimeLeft=spriteFilpTime;
            spriteIndex++;
            if(spriteIndex>4) spriteIndex=0;
            obj1.getComponent(SpriteRenderer.class).setSprite(sprites.getSprite(spriteIndex));
        }

        for(GameObject GO:this.gameObjects){
            GO.update(dt);
        }
        this.renderer.render();
    }
}
