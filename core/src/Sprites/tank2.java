package Sprites;

import com.badlogic.drop.Drop;
import com.badlogic.drop.GameScreen1;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class tank2 extends Sprite {
    public enum State{FALLING, JUMPING, STANDING, RUNNING};
    public tank2.State currentState;
    public tank2.State previousState;
    public World world;
    public Body b2body1;
    private TextureRegion tank2Stand;
    private Animation<TextureRegion> tank2Run;
    private Animation<TextureRegion> tank2Jump;
    private float stateTimer;
    private boolean runningRight;
    public tank2(World world, GameScreen1 screen){
        super(screen.getAtlas().findRegion("p1"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i= 2; i<8;i++)
            frames.add(new TextureRegion(getTexture(),144*4,i*144,144,144));
        tank2Run = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for(int i=4; i<8;i++)
            frames.add(new TextureRegion(getTexture(),144*4,i*144,144,144));
        tank2Stand = new TextureRegion(getTexture(),600,310,100,100);
        tank2Jump = new Animation<TextureRegion>(0.1f, frames);
        definetank2();
        setBounds(0,0,50/ Drop.PPM,50/Drop.PPM);
        setRegion(tank2Stand);
    }

    public void update(float dt){
        setPosition(b2body1.getPosition().x - getWidth()/2, b2body1.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch(currentState){
            case JUMPING:
                region = tank2Jump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = tank2Run.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = tank2Stand;
                break;
        }
        if((b2body1.getLinearVelocity().x<0 || !runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight = false;
        }
        else if((b2body1.getLinearVelocity().x >0 || runningRight) && region.isFlipX()){
            region.flip(true,false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt :0;
        previousState = currentState;
        return region;
    }

    public tank2.State getState(){
        if(b2body1.getLinearVelocity().y>0 || (b2body1.getLinearVelocity().y < 0 && previousState == tank2.State.JUMPING))
            return tank2.State.JUMPING;
        else if(b2body1.getLinearVelocity().y<0)
            return tank2.State.FALLING;
        else if(b2body1.getLinearVelocity().x !=0)
            return tank2.State.RUNNING;
        else
            return tank2.State.STANDING;
    }
    public void definetank2(){
        BodyDef bdef1 = new BodyDef();
        bdef1.position.set(32/ Drop.PPM,32/Drop.PPM);
        bdef1.type = BodyDef.BodyType.DynamicBody;
        b2body1 = world.createBody(bdef1);
        FixtureDef fdef1 = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/Drop.PPM);
        fdef1.shape = shape;
        b2body1.createFixture(fdef1);
    }
}
