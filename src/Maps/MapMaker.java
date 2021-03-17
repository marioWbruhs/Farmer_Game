package Maps;

import events.Keyboard;
import events.Mouse;
import org.lwjgl.glfw.GLFWKeyCallback;
import shapes.Box;
import shapes.TileType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class MapMaker {
    public Mouse mouse;
    public String mapName;
    public File file;
    public ArrayList<Box> tiles;
    public TileType tileTypes;
    public int tileID = 0;
    public FileWriter fileWriter;
    public static GLFWKeyCallback keyboard;
    public float movedNow[] = {0f, 0f};
    public float moved[] = {0f, 0f};
    public float speed;
    public boolean canMove;
    public long moveDelay = System.currentTimeMillis();

    public MapMaker(long window, int[] screenSize) throws IOException {

        this.mouse = new Mouse();
        this.mouse.setupMouse(window, screenSize);

        this.mapName = "map0.mp";

        this.tiles = new ArrayList<>();

        try {
            createMap();

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        tileTypes = new TileType();

        glfwSetKeyCallback(window, keyboard = new Keyboard());

        this.speed = tiles.size();

    }

    public void createMap() throws IOException {
        int i = 0;

        this.file = new File(this.mapName);

        while(this.file.exists())
        {
            i++;
            this.mapName = "map" + i + ".mp";
            this.file = new File(this.mapName);
            System.out.println("Tried making a map with the index of " + i);
        }

        this.file.createNewFile();
        this.fileWriter = new FileWriter(this.file.getAbsolutePath());

        System.out.println("Created a map named: " + this.file.getName());
    }

    public void update() throws IOException {
        movedNow = new float[] {0f, 0f};
        this.speed = tileTypes.tileSize;
        if(this.mouse.isButtonDown(0))
        {
            Box box = new Box(mouse.getX(), mouse.getY(), tileTypes.tileSize, tileTypes.tileSize, new int[] {0, 0, 0, 255}, tileTypes.textures.get(this.tileID), tileTypes.tilesName.get(this.tileID));

            box.x -= box.w / 2;
            box.y -= box.h / 2;

            box = getRoundedBox(box, tileTypes.tileSize);


            tiles.add(box);

            addBoxToMap(box);

            System.out.println("added a new tile at pos: " + box.x + " " + box.y);
        }

        if(canMove) {
            if (Keyboard.isKeyDown(GLFW_KEY_W)) {
                movedNow[1] -= this.speed;
            }
            if (Keyboard.isKeyDown(GLFW_KEY_A)) {
                movedNow[0] += this.speed;
            }
            if (Keyboard.isKeyDown(GLFW_KEY_S)) {
                movedNow[1] += this.speed;
            }
            if (Keyboard.isKeyDown(GLFW_KEY_D)) {
                movedNow[0] -= this.speed;
            }

            canMove = false;
        }

        System.out.println((moveDelay <= System.currentTimeMillis() - 1000L) + " " + this.speed);

        if(moveDelay + 500L <= System.currentTimeMillis())
        {
            moveDelay = System.currentTimeMillis();
            canMove = true;
        }

        moved = new float[] {movedNow[0], movedNow[1]};

        for(Box tile : tiles)
        {
            tile.moveX(movedNow[0]);
            tile.moveY(movedNow[1]);
            tile.drawTex();
        }
    }

    private void addBoxToMap(Box box) throws IOException {
        float x, y, w, h;
        String id, textureName;

        x = box.x;
        y = box.y;
        w = box.w;
        h = box.h;
        id = box.type;
        textureName = box.texture.getName();

        this.fileWriter.write(x + " " + y + " " + w + " " + h + " " + id + " " + textureName + ";");
    }

    public Box getRoundedBox(Box box, float val)
    {
        Box nBox = box;

        nBox.x = box.x - box.x % val;
        nBox.y = box.y - box.y % val;

        return nBox;

    }

}
