package com.ensinandoJava.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Fence {
    private static final List<Rectangle> allBounds = new ArrayList<>();
    private static final float SCALE = 2.0f;
    private static boolean isFirstFence = true;
    private final Texture texture;
    private final Rectangle bounds;
    private float x;
    private float y;

    public Fence(String texturePath, int screenWidth, int screenHeight, float playerStartY) {
        texture = new Texture(texturePath);

        float adjustedWidth = texture.getWidth() * SCALE;
        float adjustedHeight = texture.getHeight() * SCALE;

        if (isFirstFence) {
            x = 0;
            isFirstFence = false;
        } else {
            x = MathUtils.random(0, screenWidth - adjustedWidth);
        }

        y = MathUtils.random(100, screenHeight - adjustedHeight);

        bounds = new Rectangle(x, y, adjustedWidth, adjustedHeight);

        boolean collides;
        do {
            collides = false;
            for (Rectangle otherBounds : allBounds) {
                if (bounds.overlaps(otherBounds)) {
                    collides = true;
                    adjustPreviousFencePosition(otherBounds, playerStartY);
                    break;
                }
            }
        } while (collides);

        allBounds.add(bounds);
    }

    public static void resetFences() {
        allBounds.clear();
        isFirstFence = true;
    }

    private void adjustPreviousFencePosition(Rectangle otherBounds, float playerY) {
        this.x = otherBounds.x + otherBounds.width + 10;
        this.y = MathUtils.random(playerY + 100, otherBounds.y + otherBounds.height - texture.getHeight());

        bounds.setPosition(x, y);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, texture.getWidth() * SCALE, texture.getHeight() * SCALE);
    }

    public Rectangle getBounds() {
        return bounds;
    }

}
