package ui.menus;

import events.Mouse;
import events.states.State;
import ui.Button;
import ui.TTFFontRenderer;

import java.util.ArrayList;

public class MainMenu {
    private ArrayList<Button> buttons;
    private int[] screenSize;
    private TTFFontRenderer fontRenderer;
    public State states;
    private Mouse mouse;

    public MainMenu(int[] screenSize)
    {
        this.screenSize = screenSize;

        this.buttons = new ArrayList<>();

        addButtons();

        this.mouse = new Mouse();


    }

    public void addButtons()
    {
        this.buttons.add(new Button(-0.3f, -0.085f, 0.6f, .170f, new int[] {70, 160, 70, 255}, "PLAY GAME"));
        this.buttons.add(new Button(-0.3f, -0.7f, 0.6f, .170f, new int[] {156, 70, 70, 255}, "QUIT GAME"));
        this.buttons.add(new Button(-0.3f, -0.35f, 0.6f, .170f, new int[] {156, 161, 161, 255}, "MAP EDITOR"));
    }

    public void update(State states)
    {
        this.states = states;
        for(Button button : this.buttons)
        {
            button.setCenteredX();

            if(button.isPressed(mouse.getX(), mouse.getY(), this.screenSize) && mouse.isButtonDown(0))
            {
                if(button.getId().equalsIgnoreCase("PLAY GAME"))
                {
                    this.states.reverseState(states.MAIN_MENU);
                    this.states.reverseState(states.IN_GAME);
                }
                if(button.getId().equalsIgnoreCase("QUIT GAME"))
                {
                    System.exit(0);
                }
                if(button.getId().equalsIgnoreCase("MAP EDITOR"))
                {
                    this.states.reverseState(states.MAIN_MENU);
                    this.states.reverseState(states.MAP_EDITOR);
                }
            }
            button.draw(this.screenSize);
        }
    }

    public State getStates()
    {
        return this.states;
    }
}
