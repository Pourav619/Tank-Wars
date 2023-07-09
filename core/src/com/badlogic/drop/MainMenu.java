package com.badlogic.drop;

import com.badlogic.drop.Scenes.pause;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class MainMenu implements Screen {

    final Drop game;
    private Texture backgroundImage;
    private TextureRegion backgroundTexture;
    OrthographicCamera camera;
    Viewport screenPort;

//    private Texture badlogic;
    private Skin mySkin;
    private Stage stage;


    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonPlay, buttonExit,startBtn,exitBtn;
    private BitmapFont white, black;
    private Label heading;




    public MainMenu(final Drop game) {
        this.game = game;
        screenPort= new FitViewport(Drop.V_WIDTH,Drop.V_HEIGHT,new OrthographicCamera());
        backgroundImage = new Texture(Gdx.files.internal("gameBackground.jpg"));
        backgroundTexture = new TextureRegion(backgroundImage, 0, 0, 1920, 1000);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin= new Skin(Gdx.files.internal(GameConstants.skin));
        table = new Table(skin);
        table.setBounds(50,50,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button");
        textButtonStyle.down= skin.getDrawable("button-down");
        textButtonStyle.pressedOffsetX= 1;
        textButtonStyle.pressedOffsetY= -1;

        Label gameTitle = new Label("Tank Wars",skin,"big");
        gameTitle.setAlignment(Align.top);
        startBtn = new TextButton("New Game",skin,"small");

        Button settingBtn = new TextButton("Load Game",skin,"small");
        exitBtn = new TextButton("Exit Game",skin,"small");

        table.add(gameTitle);
        table.row().pad(10);
        table.add(startBtn).left();
        table.row().pad(10);
        table.add(settingBtn).left();
        table.row().pad(10);
        table.add(exitBtn).left();
        table.left();
        stage.addActor(table);

    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backgroundTexture, 0,0, 800, 480);
        game.batch.end();
        stage.act(delta);
        stage.draw();


        if (startBtn.isPressed()) {

            game.setScreen(new chooseTanks(game));
        }
        if(exitBtn.isPressed()){
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {
        game.screenPort.update(width,height);
        stage.getViewport().update(width,height);
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
        mySkin.dispose();
        stage.dispose();
    }

}
