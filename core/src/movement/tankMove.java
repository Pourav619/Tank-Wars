package movement;

import java.util.ArrayList;

class tankMove1 {
    private void leftMove1() {};
    private void rightMove1(){};
    private void angle1(){};
    private void power1(){};

}

class tankMove2 extends tankMove1 {
}

class health{
    private String playerHealth1;
    private String playerHealth2;
    private void health1(){};
    private void health2(){};
    private void damage(){};
}

interface weaponDrops{
    void nuclearBomb();
}

class weapons implements weaponDrops{
    private void tankMissile1(){};
    private void tankMissile2(){};
    private void tankMissile3(){};

    @Override
    public void nuclearBomb() {};

}

class StoredGames{
    ArrayList<Object> listOfGames= new ArrayList<Object>();
}