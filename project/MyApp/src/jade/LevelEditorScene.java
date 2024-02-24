package jade;

public class LevelEditorScene extends Scene {
    public LevelEditorScene(){
        System.out.println("inside level editor scene.");
    }

    private boolean changeScene = false;
    private float timeToChangeScene = 2.0f;

    @Override
    public void updata(float dt) {
        // System.out.println(""+(1.0f/dt)+"FPS");
        if(!changeScene&&KeyListener.isKeyPressed(32)){ //KeyEvent.VK_SPACE
            changeScene = true;
        }
        if(changeScene && timeToChangeScene>0){
            timeToChangeScene-=dt;
            Window.get().r -= dt*5.0f;
            Window.get().g -= dt*5.0f;
            Window.get().b -= dt*5.0f;
        }else if(changeScene){
            Window.changeScene(1);
        }
    }
}
