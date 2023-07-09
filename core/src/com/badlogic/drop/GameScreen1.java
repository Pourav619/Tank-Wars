package com.badlogic.drop;
//import com.badlogic.drop.Scenes.Hud;
import Sprites.tank;
import Sprites.tank2;
import Tools.world;
import Tools.WorldContactListener;
import com.badlogic.drop.Scenes.Hud;
import com.badlogic.drop.Scenes.pause;
        import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
        import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
        import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
        import com.badlogic.gdx.utils.Array;
        import com.badlogic.gdx.utils.TimeUtils;
        import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.physics.box2d;

public class GameScreen1 extends Game implements Screen{
    private Drop game;
    private TextureAtlas atlas;
    private Texture backgroundImage;
    private TextureRegion backgroundTexture;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;
    long lastDropTime;
    //    int dropsGathered;
    private Viewport gamePort;
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    //    private Hud hud;
    public Viewport screenPort1;
    public SpriteBatch batch1;

    //    Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private tank player;
    private tank2 player1;
    private Hud hud;
    public GameScreen1(final Drop game) {
        atlas = new TextureAtlas("fileTanks.atlas");
        this.game = game;
        batch1 = new SpriteBatch();
        camera= new OrthographicCamera();
        gamePort= new StretchViewport((Drop.V_WIDTH/4)/Drop.PPM, (Drop.V_HEIGHT/2)/Drop.PPM, camera);
        hud = new Hud(game.batch);
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);

        raindrops = new Array<Rectangle>();
        raindrops = new Array<Rectangle>();
        spawnRaindrop();
        maploader= new TmxMapLoader();
        map= maploader.load("terrain3.tmx");
        renderer= new OrthogonalTiledMapRenderer(map, 1/Drop.PPM);
        camera.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight(),0);

        world = new World(new Vector2(0,-10 ), true);
        b2dr = new Box2DDebugRenderer();
        new world(world, map);
        player = new tank(world,this);
        player1 = new tank2(world,this);
        world.setContactListener(new WorldContactListener());
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }
    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }
    public void handleInput(float dt){
//        if(Gdx.input.isTouched())
//            camera.position.x+=100*dt;
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0,4f),player.b2body.getWorldCenter(),true);
        if(Gdx.input.isKeyPressed(Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f,0),player.b2body.getWorldCenter(),true);
        if(Gdx.input.isKeyPressed(Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f,0),player.b2body.getWorldCenter(),true);

        if(Gdx.input.isKeyPressed(Input.Keys.W))
            player1.b2body1.applyLinearImpulse(new Vector2(0,4f),player.b2body.getWorldCenter(),true);
        if(Gdx.input.isKeyPressed(Keys.D) && player.b2body.getLinearVelocity().x <= 2)
            player1.b2body1.applyLinearImpulse(new Vector2(0.1f,0),player.b2body.getWorldCenter(),true);
        if(Gdx.input.isKeyPressed(Keys.A) && player.b2body.getLinearVelocity().x >= -2)
            player1.b2body1.applyLinearImpulse(new Vector2(-0.1f,0),player.b2body.getWorldCenter(),true);
    }
    public void update(float dt){
        handleInput(dt);
        world.step(1/60f, 6,2);
        player.update(dt);
        player1.update(dt);
        camera.position.x = player.b2body.getPosition().x;
        camera.update();
        hud.update(dt);
        renderer.setView(camera);
    }
    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        renderer.render();

        hud.stage.draw();

        //renderer our Box2DDebugLines
        b2dr.render(world, camera.combined);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        player.draw(game.batch);
        player1.draw(game.batch);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            game.setScreen(new pause(this));

        }
    }


    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @Override
    public void show() {
        rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        batch1.dispose();
        hud.dispose();
    }

}
