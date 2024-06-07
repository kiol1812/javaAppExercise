package jade;

public abstract class Component {
    public GameObject gameObject=null;
    // public Component(){}
    public void start(){}
    public abstract void update(float dt);
}
