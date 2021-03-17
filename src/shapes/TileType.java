package shapes;

import ui.Texture;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class TileType {
    public ArrayList<String> tilesName = new ArrayList<>();
    public ArrayList<Texture> textures = new ArrayList<>();
    public float tileSize = 0.2f;

    public TileType()
    {
        tilesName.add("assests/dirt1.png");
        tilesName.add("assests/land1.png");

        glPushMatrix();
        for(int i = 0; i < tilesName.size(); i++) {

            textures.add(new Texture(tilesName.get(i)));

        }
        glPopMatrix();
    }
}
