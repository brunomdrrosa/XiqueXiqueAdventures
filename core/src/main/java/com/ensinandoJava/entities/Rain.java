package com.ensinandoJava.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class Rain {
    private final Array<Raindrop> raindrops;
    private final ShapeRenderer shapeRenderer;

    public Rain(int windowWidth, int windowHeight, int numberOfRaindrops) {
        raindrops = new Array<>();
        shapeRenderer = new ShapeRenderer();
        generateRain(windowWidth, windowHeight, numberOfRaindrops);
    }

    public void generateRain(int windowWidth, int windowHeight, int numberOfRaindrops) {
        for (int i = 0; i < numberOfRaindrops; i++) {
            float x = (float) Math.random() * windowWidth;
            float y = (float) Math.random() * windowHeight;
            raindrops.add(new Raindrop(x, y));
        }
    }

    public void update(Player player) {
        Array<Raindrop> toRemove = new Array<>();

        for (Raindrop raindrop : raindrops) {
            if (raindrop.collidesWithPlayer(player)) {
                toRemove.add(raindrop);
            } else {
                raindrop.update();
            }
        }

        raindrops.removeAll(toRemove, true);
    }

    public void draw() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Raindrop raindrop : raindrops) {
            raindrop.draw(shapeRenderer);
        }
        shapeRenderer.end();
    }

    public void clearRain() {
        raindrops.clear();
    }

    public int getTotalRaindrops() {
        return raindrops.size;
    }
}
