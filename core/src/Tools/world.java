package Tools;
import Sprites.Gravity;
import com.badlogic.drop.Drop;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.*;
//import com.badlogic.gdx.physics.box2d;

public class world {
    public world(World world, TiledMap map){
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
//create ground bodies/fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2) / Drop.PPM , (rect.getY() + rect.getHeight()/2)/Drop.PPM);
            body = world. createBody(bdef);
            shape.setAsBox((rect.getWidth() /2)/Drop.PPM, rect.getHeight() /2/Drop.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth()/2 , rect.getY() + rect.getHeight()/2);
            body = world. createBody(bdef);
            shape.setAsBox(rect.getWidth() /2, rect.getHeight() /2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Gravity(world, map, rect);
        }

    }
}
