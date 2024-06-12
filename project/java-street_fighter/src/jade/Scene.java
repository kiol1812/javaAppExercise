package src.jade;

import java.util.ArrayList;
import java.util.List;

import src.renderer.Renderer;

public abstract class Scene {
    protected Renderer renderer=new Renderer();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects=new ArrayList<>();
    public Scene(){}
    public void init(){
    }
    public void start(){
        for(GameObject GO:gameObjects){
            GO.start();
            this.renderer.add(GO);
        }
        isRunning = true;
    }
    public void addGameObjectToScene(GameObject GO){
        if(!isRunning){
            gameObjects.add(GO);
        }else{
            gameObjects.add(GO);
            GO.start();
            this.renderer.add(GO);
        }
    }
    public abstract void updata(float dt);
    public Camera camera(){ return this.camera; }
    protected abstract GameObject createGameObject(String string);
    public List<GameObject> getGameObjects(){ return this.gameObjects; }
}