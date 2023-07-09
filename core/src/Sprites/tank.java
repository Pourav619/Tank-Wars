package Sprites;

import com.badlogic.drop.Drop;
import com.badlogic.drop.GameScreen1;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class tank extends Sprite {
    public enum State{FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion tankStand;
    private Animation<TextureRegion> tankRun;
    private Animation<TextureRegion> tankJump;
    private float stateTimer;
    private boolean runningRight;
    public tank(World world, GameScreen1 screen){
        super(screen.getAtlas().findRegion("p1"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i= 0; i<11;i++)
            frames.add(new TextureRegion(getTexture(),0,i*144,144,144));
        tankRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for(int i=4; i<6;i++)
            frames.add(new TextureRegion(getTexture(),0,i*144,144,144));
        tankStand = new TextureRegion(getTexture(),20,20,100,100);
        tankJump = new Animation<TextureRegion>(0.1f, frames);
        definetank();
        setBounds(0,0,50/Drop.PPM,50/Drop.PPM);
        setRegion(tankStand);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch(currentState){
            case JUMPING:
                region = tankJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = tankRun.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = tankStand;
                break;
        }
        if((b2body.getLinearVelocity().x<0 || !runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x >0 || runningRight) && region.isFlipX()){
            region.flip(true,false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt :0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(b2body.getLinearVelocity().y>0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y<0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x !=0)
            return State.RUNNING;
        else
            return State.STANDING;
    }
    public void definetank(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/ Drop.PPM,32/Drop.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/Drop.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
