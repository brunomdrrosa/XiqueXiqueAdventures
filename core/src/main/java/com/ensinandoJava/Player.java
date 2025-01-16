package com.ensinandoJava;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private float x, y;
    private int width = 64, height = 64;
    private final Texture texture;
    private Rectangle bounds;

    public Player(float startX, float startY, Texture texture) {
        this.x = startX;
        this.y = startY;
        this.texture = texture;
        bounds = new Rectangle(x, y, width, height);
    }

    public void move(float dx, float dy) {
        this.x += dx;
        this.y += dy;
        bounds.setPosition(x, y);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        bounds.setPosition(this.x, y);
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        bounds.setPosition(x, this.y);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
