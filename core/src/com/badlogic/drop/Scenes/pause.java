package com.badlogic.drop.Scenes;

import com.badlogic.drop.Drop;
import com.badlogic.drop.GameConstants;
import com.badlogic.drop.GameScreen1;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class pause implements Screen {
    private Texture backgroundImg;
    private TextureRegion backgroundTex;
    OrthographicCamera camera1;
    final GameScreen1 game1;

    private Stage stage2;


    private TextureAtlas atlas;
    private Skin skin1;
    private Table table1;
    private TextButton buttonPlay, buttonExit,startBtn,exitBtn;
    private BitmapFont white, black;
    private Label heading;
    Viewport screenPort1;
    private Drop game2;

    public pause(final GameScreen1 game1){
            this.game1 = game1;
        screenPort1= new FitViewport(Drop.V_WIDTH,Drop.V_HEIGHT,new OrthographicCamera());

        backgroundImg = new Texture(Gdx.files.internal("gameBackground.jpg"));
        backgroundTex= new TextureRegion(backgroundImg, 0, 0, 1920, 1000);
//        new MyFrame();
        camera1 = new OrthographicCamera();
        camera1.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {
        stage2 = new Stage();
        Gdx.input.setInputProcessor(stage2);
        skin1= new Skin(Gdx.files.internal(GameConstants.skin));
        table1 = new Table(skin1);
        table1.setBounds(50,50,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin1.getDrawable("button");
        textButtonStyle.down= skin1.getDrawable("button-down");
        textButtonStyle.pressedOffsetX= 1;
        textButtonStyle.pressedOffsetY= -1;

        Label gameTitle = new Label("Paused",skin1,"big");
        gameTitle.setAlignment(Align.top);
        startBtn = new TextButton("Resume game",skin1,"small");
        Button settingBtn = new TextButton("Add to list of load games",skin1,"small");
        exitBtn = new TextButton("Exit Game",skin1,"small");

        table1.add(gameTitle);
        table1.row().pad(10);
        table1.add(startBtn).left();
        table1.row().pad(10);
        table1.add(settingBtn).left();
        table1.row().pad(10);
        table1.add(exitBtn).left();
        table1.left();

        stage2.addActor(table1);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);

        camera1.update();
        game1.batch1.setProjectionMatrix(camera1.combined);

        game1.batch1.begin();
        game1.batch1.draw(backgroundTex, 0,0, 800, 480);
        game1.batch1.end();

        stage2.act(delta);
        stage2.draw();

        if (startBtn.isPressed()) {
            game1.setScreen(new GameScreen1(game2));
        }
        if(exitBtn.isPressed()){
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {
        stage2.getViewport().update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        skin1.dispose();
        stage2.dispose();
//        game1.batch1.dispose();
//        game1.batch1.dispose();
    }
}
