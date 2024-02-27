package jade;
public abstract class Scene {
    protected Camera camera;
    public Scene(){}

    public void init(){
    }
    public abstract void updata(float dt);
}