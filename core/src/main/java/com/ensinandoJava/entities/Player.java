package com.ensinandoJava.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private static final int WIDTH = 64;
    private static final int HEIGHT = 64;
    private final Rectangle bounds;
    private float x;
    private float y;
    private Texture texture;

    public Player(float startX, float startY, Texture texture) {
        this.x = startX;
        this.y = startY;
        this.texture = texture;
        bounds = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void move(float dx, float dy, Fence[] fences) {
        float newX = this.x + dx;
        float newY = this.y + dy;

        Rectangle newBounds = new Rectangle(newX, newY, WIDTH, HEIGHT);

        boolean collidesWithFence = false;
        for (Fence fence : fences) {
            if (newBounds.overlaps(fence.getBounds())) {
                collidesWithFence = true;
                break;
            }
        }

        if (!collidesWithFence) {
            this.x = newX;
            this.y = newY;
        }

        if (this.x < 0) this.x = 0;
        if (this.x + WIDTH > Gdx.graphics.getWidth()) this.x = Gdx.graphics.getWidth() - WIDTH;
        if (this.y < 0) this.y = 0;
        if (this.y + HEIGHT > Gdx.graphics.getHeight()) this.y = Gdx.graphics.getHeight() - HEIGHT;

        bounds.setPosition(x, y);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, WIDTH, HEIGHT);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getY() {
        return y;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

}
