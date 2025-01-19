package com.ensinandoJava.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Raindrop {
    private float x;
    private float y;
    private static final int SIZE = 6;

    public Raindrop(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        float speed = 1.125f;
        y -= speed;

        if (y < 0) {
            resetPosition();
        }
    }

    public void resetPosition() {
        y = 600;
        x = (float) Math.random() * 800;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(x, y, SIZE, SIZE);
    }

    public boolean collidesWithPlayer(Player player) {
        return new Rectangle(x, y, SIZE, SIZE).overlaps(player.getBounds());
    }
}
