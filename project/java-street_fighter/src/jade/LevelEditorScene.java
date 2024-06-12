package src.jade;

import org.joml.Vector2f;

import src.components.AnimationState;
import src.components.Sprite;
// import src.components.Sprite;
import src.components.SpriteRenderer;
import src.components.Spritesheet;
import src.components.StateMachine;
import src.util.AssetPool;

public class LevelEditorScene extends Scene {
    // private final String filePath="assets/imgs/idle.png";
    private final String filePath="assets/imgs/merge.png";
    private GameObject obj1;
    private Spritesheet sprites;
    public LevelEditorScene(){
    }
    public GameObject generateSpriteObject(Sprite sprite, float sizeX, float sizeY){
        GameObject block = new GameObject("obj 1", new Transform(new Vector2f(100, 100), new Vector2f(128, 128)), 2);
        block.transform.scale.x=sizeX;
        block.transform.scale.y=sizeY;
        SpriteRenderer renderer = new SpriteRenderer(sprite);
        block.addComponent(renderer);

        return block;
    }
    public GameObject generatePlayer(){
        // Spritesheet playerSpritesheets = AssetPool.getSpritesheet("assets/imgs/idle.png");
        GameObject player = new GameObject("obj 1", new Transform(new Vector2f(100, 100), new Vector2f(128, 128)), 2);

        AnimationState idle = new AnimationState();
        idle.title="idle";
        float defaultFrameTime=0.23f;
        idle.addFrame(sprites.getSprite(0), defaultFrameTime);
        idle.addFrame(sprites.getSprite(1), defaultFrameTime);
        idle.addFrame(sprites.getSprite(2), defaultFrameTime);
        idle.addFrame(sprites.getSprite(3), defaultFrameTime);
        idle.addFrame(sprites.getSprite(4), defaultFrameTime);
        idle.setLoop(true);

        AnimationState dd = new AnimationState();
        dd.title="dd";
        dd.addFrame(sprites.getSprite(10), defaultFrameTime);
        dd.addFrame(sprites.getSprite(11), defaultFrameTime);
        dd.addFrame(sprites.getSprite(12), defaultFrameTime);
        dd.addFrame(sprites.getSprite(13), defaultFrameTime);
        dd.addFrame(sprites.getSprite(14), defaultFrameTime);
        dd.addFrame(sprites.getSprite(15), defaultFrameTime);
        dd.setLoop(false);

        AnimationState aa = new AnimationState();
        aa.title="aa";
        aa.addFrame(sprites.getSprite(20), defaultFrameTime);
        aa.addFrame(sprites.getSprite(21), defaultFrameTime);
        aa.addFrame(sprites.getSprite(22), defaultFrameTime);
        aa.addFrame(sprites.getSprite(23), defaultFrameTime);
        aa.addFrame(sprites.getSprite(24), defaultFrameTime);
        aa.addFrame(sprites.getSprite(25), defaultFrameTime);
        aa.setLoop(false);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(idle);
        stateMachine.addState(dd);
        stateMachine.addState(aa);
        stateMachine.setDefaultState(aa.title);
        player.addComponent(stateMachine);

        return player;
    }
    @Override
    public void init(){
        loadResource();
        this.camera = new Camera(new Vector2f(-250, 0));

        sprites = AssetPool.getSpritesheet(filePath);

        obj1 = generatePlayer();
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        
        this.addGameObjectToScene(obj1);
    }
    private void loadResource(){
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet(filePath,new Spritesheet(AssetPool.getTexture(filePath),128, 128, 30, 0));

        for(GameObject g:this.getGameObjects()){
            if(g.getComponent(SpriteRenderer.class)!=null){
                SpriteRenderer spr = g.getComponent(SpriteRenderer.class);
                if(spr.getTexture()!=null){
                    spr.setTexture(AssetPool.getTexture(spr.getTexture().getFilepath()));
                }
            }
            if(g.getComponent(StateMachine.class)!=null){
                StateMachine stateMachine = g.getComponent(StateMachine.class);
                stateMachine.refreshTextures();
            }//18:14:40
        }
    }

    private int spriteIndex=0;
    private float spriteFilpTime=0.2f;
    private float spriteFilpTimeLeft=0.0f;
    @Override
    public void updata(float dt) {
        // System.out.println("FPS:"+(1.0f/dt));

        // spriteFilpTimeLeft-=dt;
        // if(spriteFilpTimeLeft<=0){
        //     spriteFilpTimeLeft=spriteFilpTime;
        //     spriteIndex++;
        //     if(spriteIndex>4) spriteIndex=0;
        //     obj1.getComponent(SpriteRenderer.class).setSprite(sprites.getSprite(spriteIndex));
        // }

        for(GameObject GO:this.gameObjects){
            GO.update(dt);
        }
        this.renderer.render();
    }
    @Override
    public GameObject createGameObject(String name){
        GameObject go = new GameObject(name);
        go.addComponent(new Transform());
        go.transform=go.getComponent(Transform.class);
        return go;
    }
}
