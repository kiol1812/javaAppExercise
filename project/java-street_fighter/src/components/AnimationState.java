package src.components;

import java.util.List;

import src.util.AssetPool;

import java.util.ArrayList;

public class AnimationState {
    public String title;
    public List<Frame> animeationFrames = new ArrayList<>();
    private static Sprite deafaultSprite = new Sprite(null);
    private transient float timeTracker = 0.0f;
    private transient int currentSprite=0;
    private boolean doesLoop = false;

    public void refreshTextures(){
        for(Frame frame:animeationFrames){
            frame.sprite.setTexture(AssetPool.getTexture(frame.sprite.getTexture().getFilepath()));
        }
    }

    public void addFrame(Sprite sprite, float frameTime){
        Frame newFrame = new Frame(sprite, frameTime);
        // newFrame.sprite.setTexture(AssetPool.getTexture(newFrame.sprite.getTexture().getFilepath()));
        animeationFrames.add(newFrame);
    }
    public void setLoop(boolean doesLoop){
        this.doesLoop=doesLoop;
    }
    public void update(float dt){
        if(currentSprite<animeationFrames.size()){
            timeTracker -= dt;
            if(timeTracker<=0){
                if(currentSprite!=animeationFrames.size()-1||doesLoop){
                    currentSprite = (currentSprite+1)%animeationFrames.size();
                }
                timeTracker = animeationFrames.get(currentSprite).frameTime;
            }
        }
    }
    public Sprite getCurrentSprite(){
        if(currentSprite<animeationFrames.size()) return animeationFrames.get(currentSprite).sprite;
        return deafaultSprite;
    }
}
