package src.jade;

public class LevelScene extends Scene {
    public LevelScene(){
        System.out.println("inside level scene.");
        Window.get().r = 1;
        Window.get().g = 1;
        Window.get().b = 1;
    }

    @Override
    public void updata(float dt) {
    }

    @Override
    protected GameObject createGameObject(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createGameObject'");
    }
}
