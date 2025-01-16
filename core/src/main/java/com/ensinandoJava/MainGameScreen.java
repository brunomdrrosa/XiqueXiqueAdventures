package com.ensinandoJava;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** Tela do jogo */
public class MainGameScreen implements Screen {
    private SpriteBatch batchPlayer;
    private SpriteBatch batchRain;
    private Texture playerTexture;
    private Player player;
    private Rain rain;
    private float timeRemaining = 60.0f;
    private float timer = 0.0f;
    private BitmapFont font;
    private OrthographicCamera camera;

    public MainGameScreen(Game game) {
    }

    @Override
    public void show() {
        batchPlayer = new SpriteBatch();
        batchRain = new SpriteBatch();
        playerTexture = new Texture("farmer.png");
        System.out.println("Player texture loaded: " + playerTexture.getWidth() + "x" + playerTexture.getHeight());
        player = new Player(250, 250, playerTexture);
        int numberOfRaindrops = 100;
        rain = new Rain(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), numberOfRaindrops);
        font = new BitmapFont();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateTimer();

        camera.update();

        handleInput();

        batchRain.setProjectionMatrix(camera.combined);
        batchRain.begin();
        rain.update(player);
        rain.draw(batchRain);
        batchRain.end();

        batchPlayer.setProjectionMatrix(camera.combined);
        batchPlayer.begin();
        player.draw(batchPlayer);
        batchPlayer.end();

        batchPlayer.begin();
        font.draw(batchPlayer, "Tempo: " + (int) timeRemaining, Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 20);
        font.draw(batchPlayer, "Gotas restantes: " + rain.getTotalRaindrops(), 40, Gdx.graphics.getHeight() - 20);
        batchPlayer.end();
    }

    private void handleInput() {
        float moveSpeed = 400 * Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.move(0, moveSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.move(0, -moveSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.move(-moveSpeed, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.move(moveSpeed, 0);
        }
    }

    private void updateTimer() {
        timer += Gdx.graphics.getDeltaTime();
        if (timer >= 1.0f) {
            timeRemaining = Math.max(0, timeRemaining - 1.0f);
            timer = 0.0f;
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batchPlayer.dispose();
        batchRain.dispose();
        playerTexture.dispose();
        font.dispose();
    }
}
