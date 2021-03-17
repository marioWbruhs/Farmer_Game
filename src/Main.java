import Maps.MapMaker;
import events.states.State;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import shapes.Box;
import ui.menus.MainMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {
    private long window;
    public State stateManager;
    private MapMaker mapEditor;
    private MainMenu mainMenu;
    public int[] screenSize = {1080, 720};

    private ArrayList<Box> backgrounds = new ArrayList<>();

    public void run() throws IOException {
        init();

        loop();
    }


    public void init() {
        if(!glfwInit())
        {
            throw new IllegalStateException("couldnt load glfw |:<");
        }


        GLFWVidMode vidmode = Objects.requireNonNull(glfwGetVideoMode(glfwGetPrimaryMonitor()));

        this.screenSize[0] = 1920;
        this.screenSize[1] = 1080;

        //glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);

        this.window = glfwCreateWindow(this.screenSize[0], this.screenSize[1], "Farmer Game", 0, 0);

        glfwMakeContextCurrent(this.window);

        glfwSetWindowSizeCallback(this.window, this::windowSizeChanged);

        GL.createCapabilities();

        glEnable(GL_TEXTURE_2D);

        createConstants();


    }

    private void windowSizeChanged(long window, int width, int height) {
        this.screenSize[0] = width;
        this.screenSize[1] = height;

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0,width,height,0,0,0);
        glMatrixMode(GL_MODELVIEW);
    }

    public void createConstants()
    {
        try {
            mapEditor = new MapMaker(this.window, this.screenSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stateManager = new State();

        mainMenu = new MainMenu(this.screenSize);

        backgrounds.add(new Box(-1f, -1f, 2f, 2f, new int[] {255, 255, 255, 255}, "InGameBackground"));
        backgrounds.add(new Box(-1f, -1f, 2f, 2f, new int[] {136, 148, 148, 255}, "MenuBackground"));
        backgrounds.add(new Box(-1f, -1f, 2f, 2f, new int[] {255, 255, 255, 255}, "MapMakerBackground"));
    }


    public void loop() {
        while(!glfwWindowShouldClose(this.window))
        {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glfwPollEvents();

            backgrounds.get(stateManager.getState()).draw();

            if(stateManager.isState(stateManager.IN_GAME))
            {
                //in game loop
            }
            else if(stateManager.isState(stateManager.MAIN_MENU))
            {
                //main menu loop

                mainMenu.update(stateManager);

                stateManager.setStates(mainMenu.getStates());
            }
            else if(stateManager.isState(stateManager.MAP_EDITOR))
            {
                //map editor

                try {
                    mapEditor.update();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            glfwSwapBuffers(this.window);
        }
    }


    public static void main(String[] args) throws IOException {
        new Main().run();
    }
}
