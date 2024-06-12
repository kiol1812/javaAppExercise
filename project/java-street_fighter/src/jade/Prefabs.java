package src.jade;

import src.components.AnimationState;
import src.components.Sprite;
import src.components.SpriteRenderer;
import src.components.Spritesheet;
import src.components.StateMachine;
import src.util.AssetPool;

public class Prefabs {
    public static GameObject generateSpriteObject(Sprite sprite, float sizeX, float sizeY){
        GameObject block = Window.getScene().createGameObject("Sprite_Object_Gen");
        block.transform.scale.x=sizeX;
        block.transform.scale.y=sizeY;
        SpriteRenderer renderer = new SpriteRenderer(sprite);
        block.addComponent(renderer);

        return block;
    }
    public static GameObject generatePlayer(){
        Spritesheet playerSpritesheets = AssetPool.getSpritesheet("assets/imgs/idel.png");
        GameObject player = generateSpriteObject(playerSpritesheets.getSprite(0), 0.25f, 0.25f);

        AnimationState idel = new AnimationState();
        idel.title="idel";
        float defaultFrameTime=0.23f;
        idel.addFrame(playerSpritesheets.getSprite(0), defaultFrameTime);
        idel.addFrame(playerSpritesheets.getSprite(1), defaultFrameTime);
        idel.addFrame(playerSpritesheets.getSprite(2), defaultFrameTime);
        idel.addFrame(playerSpritesheets.getSprite(3), defaultFrameTime);
        idel.addFrame(playerSpritesheets.getSprite(4), defaultFrameTime);
        idel.setLoop(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(idel);
        stateMachine.setDefaultState(idel.title);
        player.addComponent(stateMachine);

        return player;
    }
}
