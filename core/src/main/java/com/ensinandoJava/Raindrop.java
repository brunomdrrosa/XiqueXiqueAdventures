package com.ensinandoJava;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Raindrop {
    private float x, y;
    private float speed = 2.0f;
    private static final int SIZE = 5;

    public Raindrop(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        y -= speed; // O valor negativo faz com que a chuva vá para baixo (de cima para baixo)

        if (y < 0) { // Se a gota sair pela parte inferior da tela, ela volta para o topo
            resetPosition();
        }
    }

    public void resetPosition() {
        y = 600; // Define o topo da tela para a nova posição
        x = (float) Math.random() * 800; // Posiciona aleatoriamente a gota na largura da tela
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(x, y, SIZE, SIZE);
    }

    public boolean collidesWithPlayer(Player player) {
        return new Rectangle(x, y, SIZE, SIZE).overlaps(player.getBounds());
    }
}
