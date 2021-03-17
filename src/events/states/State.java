package events.states;

import java.util.ArrayList;

public class State {
    public ArrayList<Boolean> states = new ArrayList<>();

    public int IN_GAME = 0, MAIN_MENU = 1, MAP_EDITOR = 2;


    public State()
    {
        states.add(false); //in game
        states.add(true);  //main menu
        states.add(false); //map editor

    }

    public void changeState(int state, boolean val)
    {
        states.set(state, val);
    }

    public void reverseState(int state)
    {
        states.set(state, !states.get(state));
    }

    public boolean isState(int state)
    {
        return states.get(state);
    }

    public int getState()
    {
        int i = 0;

        for(Boolean bool : states)
        {
            if(bool)
            {
                return i;
            }
            i++;
        }

        return -1;
    }

    public void setStates(State states)
    {
        int i = 0;
        for(boolean state : this.states)
        {
            this.states.set(i, state);

            i++;
        }
    }
}
