package com.badlogic.drop.Scenes;

import com.badlogic.drop.Drop;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer score;

    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    Label worldLabel;
    Label marioLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(Drop.V_WIDTH, Drop.V_HEIGHT , new OrthographicCamera());
        stage = new Stage(viewport,sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel =new Label(String.format("%03d",worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel=new Label("100%",new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        timeLabel=new Label("TIME",new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        levelLabel=new Label("100%",new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        worldLabel=new Label("p1",new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        marioLabel=new Label("p2",new Label.LabelStyle(new BitmapFont(),Color.WHITE));

        table.add(marioLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX().padTop(10);
        table.add(countdownLabel).expand().padTop(10);
        table.add(levelLabel).expandX().padTop(10);
        table.padTop(10);
        stage.addActor(table);


    }

    public void update(float dt){
        timeCount+=dt;
        if(timeCount>=1){
            worldTimer--;
            countdownLabel.setText(String.format("%03d",worldTimer));
            timeCount=0;
        }
    }

    public void dispose(){
        stage.dispose();
    }
}
