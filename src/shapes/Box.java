package shapes;

import ui.TTFFontRenderer;
import ui.Texture;

import java.awt.*;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class Box {
    public float x, y, w, h, color[];
    public Texture texture;
    public String type;
    public TTFFontRenderer fontRenderer;
    public int[] screenSize;

    public Box(float x, float y, float w, float h, int[] color, String type)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        this.color = new float[] {color[0] / 255f, color[1] / 255f, color[2] / 255f, color[3] / 255f};

        this.type = type;


    }

    public Box(float x, float y, float w, float h, int[] color, Texture tex, String type)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        //try {
        //    this.fontRenderer = new TTFFontRenderer(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/Comfortaa.ttf")).deriveFont(Font.TRUETYPE_FONT, 40f));
        //} catch (FontFormatException | IOException e) {
        //    e.printStackTrace();
        //}

        this.color = new float[] {color[0] / 255f, color[1] / 255f, color[2] / 255f, color[3] / 255f};
        this.texture = tex;

        this.type = type;
    }

    public void setScreenSize(int[] screenSize)
    {
        this.screenSize = screenSize;
    }

    public void draw()
    {
        glBegin(GL_QUADS);
        {
            glColor4f(this.color[0], this.color[1], this.color[2], this.color[3]);

            glVertex2f(this.x, this.y);

            glVertex2f(this.x + this.w, this.y);

            glVertex2f(this.x + this.w, this.y + this.h);

            glVertex2f(this.x, this.y + this.h);
        }
        glEnd();

    }

    public void drawTex()
    {
        glPushMatrix();
        glEnable(GL_BLEND);

        this.texture.bind();
        glBegin(GL_QUADS);

        glTexCoord2f(1, 1);
        glVertex2f(this.x, this.y);

        glTexCoord2f(0, 1);
        glVertex2f(this.x + this.w, this.y);

        glTexCoord2f(0, 0);
        glVertex2f(this.x + this.w, this.y + this.h);

        glTexCoord2f(1, 0);
        glVertex2f(this.x, this.y + this.h);

        glEnd();
        this.texture.unbind();

        glDisable(GL_BLEND);

        glPopMatrix();

    }

    public void drawId()
    {
        glPushMatrix();
        glScaled(0.002, 0.002, 0.002);
        glRotated(180, 1, 0, 0);

        //fontRenderer.drawString(this.type, (this.x * (this.screenSize[0] / 2 ) + this.w * (this.screenSize[0] / 2)) - fontRenderer.getWidth(this.type), (-this.y * (this.screenSize[1] / 2 )), 0xff000000);

        glPopMatrix();
    }

    public float distanceTo(float[] point)
    {
        float x = point[0] - this.x;
        float y = point[1] - this.y;

        if(x < 0)
            x *= -1;
        if(y < 0)
            y *= -1;

        return (float)Math.sqrt(x * x + y * y);
    }

    public boolean collidesMouse(int sx, int sy, int[] screenSize)
    {
        this.screenSize = screenSize;
        float x = ((float)sx  / screenSize[0] - 0.5f) * 2;
        float y = ((float)sy  / screenSize[1] - 0.5f) * 2;


        if(this.type == "dirt") {
            //System.out.println(x + " " + y + "; " + this.x + " " + this.y);
        }

        boolean s1, s2, s3, s4;

        s1 = this.x > x;
        s2 = this.y > y;

        s3 = this.x < x + this.w;
        s4 = this.y < y + this.h;

        return distanceTo(new float[] {x, y}) < this.h / 1.5;

    }

    public boolean collide(Box obj)
    {
        return distanceTo(new float[] {obj.x, obj.y}) < this.h / 1.5;
    }

    public void moveTo(float mx, float my, int[] screenSize)
    {
        float x = ((float)mx  / screenSize[0] - 0.5f) * 2 - this.w / 2;
        float y = ((float)my  / screenSize[1] - 0.5f) * 2 - this.h / 2;

        if(x + this.w < 1f && x> -1f)
            this.x = x;

        if(y + this.h < 1f && y > -1f)
            this.y = y;
    }

    public void moveToPos(float mx, float my)
    {
        this.x = mx;
        this.y = my;
    }

    public void moveToX(float mx)
    {
        this.x = mx;
    }

    public String getId()
    {
        return this.type;
    }

    public void setColor(float[] color)
    {
        this.color = color;
    }
    public void moveY(float my)
    {
        this.y += my;
    }

    public void moveX(float mx)
    {
        this.x += mx;
    }
}
