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
public class chooseTanks implements Screen {

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
    private TextButton buttonPlay, buttonExit,startBtn,exitBtn,settingBtn;
    private BitmapFont white, black;
    private Label heading;




    public chooseTanks(final Drop game) {
        this.game = game;
        screenPort= new FitViewport(Drop.V_WIDTH,Drop.V_HEIGHT,new OrthographicCamera());

        backgroundImage = new Texture(Gdx.files.internal("chooseTANKS1.png"));
        backgroundTexture = new TextureRegion(backgroundImage, 50, 50, 720, 500);
//        new MyFrame();
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

        startBtn = new TextButton("Tank 1",skin,"small");
        settingBtn = new TextButton("Tank 2",skin,"small");
        exitBtn = new TextButton("Tank 3",skin,"small");
        table.add(startBtn).pad(30);

        table.add(settingBtn).pad(30);

        table.add(exitBtn).pad(30);
        table.left();
        table.bottom();
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
            game.setScreen(new GameScreen1(game));
        }
        if(exitBtn.isPressed()){
            game.setScreen(new GameScreen1(game));
        }
        if(settingBtn.isPressed()){
            game.setScreen(new GameScreen1(game));
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

