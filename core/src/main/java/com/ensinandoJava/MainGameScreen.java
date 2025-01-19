package com.ensinandoJava;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ensinandoJava.entities.Fence;
import com.ensinandoJava.entities.Player;
import com.ensinandoJava.entities.Rain;

public class MainGameScreen implements Screen {
    private static final int NUMERO_GOTAS_CHUVA = 1000;
    private final Game game;

    private SpriteBatch batchPlayer;
    private SpriteBatch batchRain;
    private SpriteBatch batchBackground;
    private SpriteBatch batchGotas;
    private SpriteBatch batchAmpulheta;
    private Player player;
    private Fence[] fences;
    private Rain rain;
    private float timeRemaining = 60.0f;
    private float timer = 0.0f;
    private BitmapFont font;
    private BitmapFont fontGameOver;
    private BitmapFont fontWinner;
    private BitmapFont fontBackButton;
    private OrthographicCamera camera;
    private long lastFrameChangeTime;
    private boolean toggleFrame = false;
    private boolean winner = false;
    private Stage stage;
    private Texture botaoGotas;
    private Texture botaoAmpulheta;
    private Texture grassBackground;
    private Texture playerTextureSA;
    private Texture playerTextureSD;
    private Texture playerTextureSA50;
    private Texture playerTextureSA75;
    private Texture playerTextureSA100;
    private Texture playerTextureSD50;
    private Texture playerTextureSD75;
    private Texture playerTextureSD100;
    private Texture playerTextureWA;
    private Texture playerTextureWD;
    private Texture playerTextureWA50;
    private Texture playerTextureWA75;
    private Texture playerTextureWA100;
    private Texture playerTextureWD50;
    private Texture playerTextureWD75;
    private Texture playerTextureWD100;
    private Texture playerTextureAA;
    private Texture playerTextureAA50;
    private Texture playerTextureAA75;
    private Texture playerTextureAA100;
    private Texture playerTextureAD;
    private Texture playerTextureAD50;
    private Texture playerTextureAD75;
    private Texture playerTextureAD100;
    private Texture playerTextureDD;
    private Texture playerTextureDD50;
    private Texture playerTextureDD75;
    private Texture playerTextureDD100;
    private Texture playerTextureDA;
    private Texture playerTextureDA50;
    private Texture playerTextureDA75;
    private Texture playerTextureDA100;
    private Texture currentTexture;
    private int remainingRaindropsAtGameOver = -1;
    private boolean isTimerActive = true;

    public MainGameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batchPlayer = new SpriteBatch();
        batchRain = new SpriteBatch();
        batchBackground = new SpriteBatch();
        batchGotas = new SpriteBatch();
        batchAmpulheta = new SpriteBatch();
        adicionarTexturasDoJogador();

        currentTexture = playerTextureSA;
        player = new Player(300, 10, currentTexture);
        fences = new Fence[5];
        adicionarTexturasDasCercas();
        rain = new Rain(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), NUMERO_GOTAS_CHUVA);
        font = new BitmapFont();
        fontGameOver = new BitmapFont();
        fontWinner = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Poppins-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);

        FreeTypeFontGenerator.FreeTypeFontParameter parameterGameOver = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterGameOver.size = 48;
        parameterGameOver.color = Color.RED;
        parameterGameOver.borderWidth = 3;
        parameterGameOver.borderColor = Color.WHITE;

        fontGameOver = generator.generateFont(parameterGameOver);

        FreeTypeFontGenerator.FreeTypeFontParameter parameterWinner = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterWinner.size = 30;
        parameterWinner.color = Color.WHITE;
        parameterWinner.borderWidth = 3;
        parameterWinner.borderColor = Color.BLACK;
        fontWinner = generator.generateFont(parameterWinner);

        Drawable bordasGotas = new TextureRegionDrawable(new com.badlogic.gdx.graphics.Texture("gotas.png"));
        Image fundoGotas = new Image(bordasGotas);
        fundoGotas.setSize(200, 115);
        fundoGotas.setPosition(
            50, 50
        );

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        lastFrameChangeTime = TimeUtils.nanoTime();

        grassBackground = new Texture("grass.png");
        botaoGotas = new Texture("gotas.png");
        botaoAmpulheta = new Texture("tempo.png");

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        fontBackButton = new BitmapFont();
        FreeTypeFontGenerator.FreeTypeFontParameter parameterBack = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterBack.size = 24;
        parameterBack.color = Color.WHITE;
        parameterBack.borderWidth = 3;
        parameterBack.borderColor = Color.BLACK;
        fontBackButton = generator.generateFont(parameterBack);
        generator.dispose();

    }

    private void adicionarTexturasDasCercas() {
        fences[0] = new Fence("fence1.png", Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), player.getY());
        fences[1] = new Fence("fence2.png", Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), player.getY());
        fences[2] = new Fence("fence3.png", Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), player.getY());
        fences[3] = new Fence("fence2.png", Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), player.getY());
        fences[4] = new Fence("fence1.png", Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), player.getY());
    }

    private void adicionarTexturasDoJogador() {
        playerTextureWA = new Texture("farmerWA.png");
        playerTextureWD = new Texture("farmerWD.png");
        playerTextureSA = new Texture("farmerSA.png");
        playerTextureSD = new Texture("farmerSD.png");
        playerTextureWA50 = new Texture("farmerWA50.png");
        playerTextureWD50 = new Texture("farmerWD50.png");
        playerTextureSA50 = new Texture("farmerSA50.png");
        playerTextureSD50 = new Texture("farmerSD50.png");
        playerTextureWA75 = new Texture("farmerWA75.png");
        playerTextureWD75 = new Texture("farmerWD75.png");
        playerTextureSA75 = new Texture("farmerSA75.png");
        playerTextureSD75 = new Texture("farmerSD75.png");
        playerTextureWA100 = new Texture("farmerWA100.png");
        playerTextureWD100 = new Texture("farmerWD100.png");
        playerTextureSD100 = new Texture("farmerSD100.png");
        playerTextureSA100 = new Texture("farmerSA100.png");
        playerTextureAA = new Texture("farmerAA.png");
        playerTextureAA50 = new Texture("farmerAA50.png");
        playerTextureAA75 = new Texture("farmerAA75.png");
        playerTextureAA100 = new Texture("farmerAA100.png");
        playerTextureAD = new Texture("farmerAD.png");
        playerTextureAD50 = new Texture("farmerAD50.png");
        playerTextureAD75 = new Texture("farmerAD75.png");
        playerTextureAD100 = new Texture("farmerAD100.png");
        playerTextureDA = new Texture("farmerDA.png");
        playerTextureDA50 = new Texture("farmerDA50.png");
        playerTextureDA75 = new Texture("farmerDA75.png");
        playerTextureDA100 = new Texture("farmerDA100.png");
        playerTextureDD = new Texture("farmerDD.png");
        playerTextureDD50 = new Texture("farmerDD50.png");
        playerTextureDD75 = new Texture("farmerDD75.png");
        playerTextureDD100 = new Texture("farmerDD100.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (rain.getTotalRaindrops() == 0) {
            winner = true;
            isTimerActive = false;
        }

        updateTimer();
        camera.update();

        handleInput();

        batchBackground.setProjectionMatrix(camera.combined);
        batchBackground.begin();
        batchBackground.draw(grassBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batchBackground.end();

        batchRain.begin();
        for (Fence fence : fences) {
            fence.draw(batchRain);
        }
        batchRain.end();

        batchRain.setProjectionMatrix(camera.combined);
        batchRain.begin();
        rain.update(player);
        rain.draw();
        batchRain.end();

        batchPlayer.setProjectionMatrix(camera.combined);
        batchPlayer.begin();
        player.draw(batchPlayer);
        batchPlayer.end();

        batchGotas.begin();
        batchGotas.draw(botaoGotas, 485, 400, 120, 50);
        batchGotas.end();

        batchAmpulheta.begin();
        batchAmpulheta.draw(botaoAmpulheta, 485, 35, 130, 70);
        batchAmpulheta.end();

        batchPlayer.begin();

        if (timeRemaining <= 0 && remainingRaindropsAtGameOver == -1) {
            remainingRaindropsAtGameOver = rain.getTotalRaindrops();
            rain.clearRain();
        }

        if (remainingRaindropsAtGameOver != -1 && timeRemaining <= 0) {
            font.draw(batchPlayer, "" + remainingRaindropsAtGameOver, Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 50);
            fontGameOver.draw(batchPlayer, "Game Over", 170, 260);
            Fence.resetFences();
            adicionarBotaoJogarNovamente();
        } else if (winner) {
            font.draw(batchPlayer, "" + rain.getTotalRaindrops(), Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 50);
            fontWinner.draw(batchPlayer, "Você salvou Xique-Xique", 100, 260);
            Fence.resetFences();
            adicionarBotaoJogarNovamente();
        } else {
            font.draw(batchPlayer, "" + rain.getTotalRaindrops(), Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 50);
        }

        int minutes = (int) timeRemaining / 60;
        int seconds = (int) timeRemaining % 60;
        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        font.draw(batchPlayer, timeFormatted, Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 403);

        batchPlayer.end();
    }

    private void adicionarBotaoJogarNovamente() {
        fontBackButton.draw(batchPlayer, "Jogar novamente", 190, 100);
        TextButton.TextButtonStyle jogarStyle = new TextButton.TextButtonStyle();
        jogarStyle.font = fontBackButton;
        jogarStyle.fontColor = Color.WHITE;
        TextButton jogarNovamente = new TextButton("", jogarStyle);
        jogarNovamente.setSize(310, 60);
        jogarNovamente.setPosition(
            Gdx.graphics.getWidth() / 2 - jogarNovamente.getWidth() / 2,
            200 - jogarNovamente.getHeight() - 70
        );

        jogarNovamente.addListener(event -> {
            if (event.isHandled()) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                game.setScreen(new MainGameScreen(game));
            }
            return false;
        });

        stage.addActor(jogarNovamente);
    }


    private void handleInput() {
        if (remainingRaindropsAtGameOver != -1 || winner) {
            return;
        }

        float moveSpeed = 200 * Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.move(0, moveSpeed, fences);
            animateUpMovement();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.move(0, -moveSpeed, fences);
            animateDownMovement();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.move(-moveSpeed, 0, fences);
            animateLeftMovement();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.move(moveSpeed, 0, fences);
            animateRightMovement();
        }

        player.setTexture(currentTexture);
    }

    private void updatePlayerTexture() {
        int collectedRaindrops = NUMERO_GOTAS_CHUVA - rain.getTotalRaindrops();
        float percentage = (float) collectedRaindrops / NUMERO_GOTAS_CHUVA;
        if (percentage >= 1.0f) {
            currentTexture = toggleFrame ? playerTextureSA100 : playerTextureSD100;
        } else if (percentage >= 0.75f) {
            currentTexture = toggleFrame ? playerTextureSA75 : playerTextureSD75;
        } else if (percentage >= 0.5f) {
            currentTexture = toggleFrame ? playerTextureSA50 : playerTextureSD50;
        } else {
            currentTexture = toggleFrame ? playerTextureSA : playerTextureSD;
        }
    }

    private void updatePlayerTextureUp() {
        int collectedRaindrops = NUMERO_GOTAS_CHUVA - rain.getTotalRaindrops();
        float percentage = (float) collectedRaindrops / NUMERO_GOTAS_CHUVA;
        if (percentage >= 1.0f) {
            currentTexture = toggleFrame ? playerTextureWA100 : playerTextureWD100;
        } else if (percentage >= 0.75f) {
            currentTexture = toggleFrame ? playerTextureWA75 : playerTextureWD75;
        } else if (percentage >= 0.5f) {
            currentTexture = toggleFrame ? playerTextureWA50 : playerTextureWD50;
        } else {
            currentTexture = toggleFrame ? playerTextureWA : playerTextureWD;
        }
    }

    private void updatePlayerTextureLeft() {
        int collectedRaindrops = NUMERO_GOTAS_CHUVA - rain.getTotalRaindrops();
        float percentage = (float) collectedRaindrops / NUMERO_GOTAS_CHUVA;
        if (percentage >= 1.0f) {
            currentTexture = toggleFrame ? playerTextureAA100 : playerTextureAD100;
        } else if (percentage >= 0.75f) {
            currentTexture = toggleFrame ? playerTextureAA75 : playerTextureAD75;
        } else if (percentage >= 0.5f) {
            currentTexture = toggleFrame ? playerTextureAA50 : playerTextureAD50;
        } else {
            currentTexture = toggleFrame ? playerTextureAA : playerTextureAD;
        }
    }

    private void updatePlayerTextureRight() {
        int collectedRaindrops = NUMERO_GOTAS_CHUVA - rain.getTotalRaindrops();
        float percentage = (float) collectedRaindrops / NUMERO_GOTAS_CHUVA;
        if (percentage >= 1.0f) {
            currentTexture = toggleFrame ? playerTextureDA100 : playerTextureDD100;
        } else if (percentage >= 0.75f) {
            currentTexture = toggleFrame ? playerTextureDA75 : playerTextureDD75;
        } else if (percentage >= 0.5f) {
            currentTexture = toggleFrame ? playerTextureDA50 : playerTextureDD50;
        } else {
            currentTexture = toggleFrame ? playerTextureDA : playerTextureDD;
        }
    }

    private void animateUpMovement() {
        long currentTime = TimeUtils.nanoTime();
        if (currentTime - lastFrameChangeTime > 150_000_000) {
            toggleFrame = !toggleFrame;
            updatePlayerTextureUp();
            lastFrameChangeTime = currentTime;
        }
    }

    private void animateDownMovement() {
        long currentTime = TimeUtils.nanoTime();
        if (currentTime - lastFrameChangeTime > 150_000_000) {
            toggleFrame = !toggleFrame;
            updatePlayerTexture();
            lastFrameChangeTime = currentTime;
        }
    }

    private void animateLeftMovement() {
        long currentTime = TimeUtils.nanoTime();
        if (currentTime - lastFrameChangeTime > 150_000_000) {
            toggleFrame = !toggleFrame;
            updatePlayerTextureLeft();
            lastFrameChangeTime = currentTime;
        }
    }

    private void animateRightMovement() {
        long currentTime = TimeUtils.nanoTime();
        if (currentTime - lastFrameChangeTime > 150_000_000) {
            toggleFrame = !toggleFrame;
            updatePlayerTextureRight();
            lastFrameChangeTime = currentTime;
        }
    }

    private void updateTimer() {
        if (isTimerActive) {
            timer += Gdx.graphics.getDeltaTime();
            if (timer >= 1.0f) {
                timeRemaining = Math.max(0, timeRemaining - 1.0f);
                timer = 0.0f;
            }
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
        playerTextureWA.dispose();
        playerTextureWD.dispose();
        playerTextureSA.dispose();
        playerTextureSD.dispose();
        playerTextureWA50.dispose();
        playerTextureWA75.dispose();
        playerTextureWA100.dispose();
        playerTextureWD50.dispose();
        playerTextureWD75.dispose();
        playerTextureWD100.dispose();
        playerTextureSA50.dispose();
        playerTextureSA75.dispose();
        playerTextureSA100.dispose();
        playerTextureSD50.dispose();
        playerTextureSD75.dispose();
        playerTextureSD100.dispose();
        font.dispose();
    }
}
