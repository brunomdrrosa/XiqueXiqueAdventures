package com.ensinandoJava;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ensinandoJava.service.WeatherService;

public class MainMenuScreen implements Screen {

    private final WeatherService weatherService;
    private final Game game;
    private Stage stage;
    private BitmapFont font;

    public MainMenuScreen(Game game, WeatherService weatherService) {
        this.game = game;
        this.weatherService = weatherService;
    }

    private static void alterarCursorAoClicarNaImagem(Image borderImage) {
        borderImage.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.NotAllowed);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.NotAllowed);
                return true;
            }
        });
    }

    private static void alterarCursorAoClicarNoTexto(TextButton playButton) {
        playButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.NotAllowed);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.NotAllowed);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.NotAllowed);
            }
        });
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Poppins-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 28;
        font = generator.generateFont(parameter);
        generator.dispose();

        FreeTypeFontGenerator boldGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Poppins-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter boldParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        boldParameter.size = 40;
        BitmapFont boldFont = boldGenerator.generateFont(boldParameter);
        boldGenerator.dispose();

        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;

        TextButtonStyle jogarStyle = new TextButtonStyle();
        jogarStyle.font = boldFont;
        jogarStyle.fontColor = Color.WHITE;

        if (!weatherService.isRaining()) {
            LabelStyle labelStyle = new LabelStyle();
            labelStyle.font = font;
            labelStyle.fontColor = Color.WHITE;

            Label weatherLabel = new Label("Não está chovendo em   Xique-Xique BA", labelStyle);

            weatherLabel.setWrap(true);
            weatherLabel.setSize(Gdx.graphics.getWidth() * 0.7f, 0);
            weatherLabel.setAlignment(Align.center);
            weatherLabel.setPosition(
                Gdx.graphics.getWidth() / 2 - weatherLabel.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 + 150
            );

            stage.addActor(weatherLabel);

            String diaChuva = weatherService.getNextRainyDay();
            Label forecastLabel = new Label("Você pode jogar assim que começar a chover, a previsão para a próxima chuva é no dia " + diaChuva + ".", labelStyle);
            forecastLabel.setWrap(true);
            forecastLabel.setSize(Gdx.graphics.getWidth() * 0.7f, 0);
            forecastLabel.setAlignment(Align.center);
            forecastLabel.setHeight(forecastLabel.getPrefHeight());
            forecastLabel.setPosition(
                Gdx.graphics.getWidth() / 2 - forecastLabel.getWidth() / 2,
                weatherLabel.getY() - forecastLabel.getPrefHeight() - 65
            );
            stage.addActor(forecastLabel);

            TextButton playButton = new TextButton("JOGAR", jogarStyle);
            playButton.setSize(200, 60);
            playButton.setPosition(
                Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2,
                forecastLabel.getY() - playButton.getHeight() - 30
            );

            Drawable borderDrawable = new TextureRegionDrawable(new com.badlogic.gdx.graphics.Texture("borda.png"));
            Image borderImage = new Image(borderDrawable);
            borderImage.setSize(playButton.getWidth() + 10, playButton.getHeight() + 10);
            borderImage.setPosition(
                Gdx.graphics.getWidth() / 2 - borderImage.getWidth() / 2,
                forecastLabel.getY() - borderImage.getHeight() - 25
            );

            Image pinImage = new Image(new TextureRegionDrawable(new com.badlogic.gdx.graphics.Texture("pin.png")));
            pinImage.setSize(32, 32);
            pinImage.setPosition(180, 355);

            stage.addActor(pinImage);
            stage.addActor(borderImage);
            stage.addActor(playButton);

            alterarCursorAoClicarNaImagem(borderImage);
            alterarCursorAoClicarNoTexto(playButton);
        } else {
            LabelStyle labelStyle = new LabelStyle();
            labelStyle.font = font;
            labelStyle.fontColor = Color.WHITE;

            Image logoImage = new Image(new TextureRegionDrawable(new com.badlogic.gdx.graphics.Texture("logo.png")));
            logoImage.setSize(200, 200);
            logoImage.setPosition(
                Gdx.graphics.getWidth() / 2 - logoImage.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - logoImage.getHeight() / 2 + 170
            );
            stage.addActor(logoImage);

            Label chuvaLabel = new Label("Neste momento, está chovendo em     Xique-Xique BA", labelStyle);
            chuvaLabel.setWrap(true);
            chuvaLabel.setSize(Gdx.graphics.getWidth() * 0.7f, 0);
            chuvaLabel.setAlignment(Align.center);
            chuvaLabel.setPosition(
                Gdx.graphics.getWidth() / 2 - chuvaLabel.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 + 50
            );

            Image pinImage = new Image(new TextureRegionDrawable(new com.badlogic.gdx.graphics.Texture("pin.png")));
            pinImage.setSize(32, 32);
            pinImage.setPosition(220, 255);

            stage.addActor(pinImage);
            stage.addActor(chuvaLabel);

            Label textoJogarJogo = new Label("Você pode jogar o nosso jogo clicando no botão abaixo", labelStyle);
            textoJogarJogo.setWrap(true);
            textoJogarJogo.setSize(Gdx.graphics.getWidth() * 0.7f, 0);
            textoJogarJogo.setAlignment(Align.center);
            textoJogarJogo.setPosition(
                Gdx.graphics.getWidth() / 2 - textoJogarJogo.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - 50
            );

            stage.addActor(textoJogarJogo);

            TextButton playButton = new TextButton("JOGAR", jogarStyle);
            playButton.setSize(200, 60);
            playButton.setPosition(
                Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2,
                textoJogarJogo.getY() - playButton.getHeight() - 70
            );

            Drawable borderDrawable = new TextureRegionDrawable(new com.badlogic.gdx.graphics.Texture("bordaJogavel.png"));
            Image borderImage = new Image(borderDrawable);
            borderImage.setSize(playButton.getWidth() + 10, playButton.getHeight() + 10);
            borderImage.setPosition(
                Gdx.graphics.getWidth() / 2 - borderImage.getWidth() / 2,
                textoJogarJogo.getY() - borderImage.getHeight() - 65
            );

            playButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                }
            });

            playButton.addListener(event -> {
                if (event.isHandled()) {
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                    game.setScreen(new MainGameScreen(game));
                }
                return false;
            });


            stage.addActor(borderImage);
            stage.addActor(playButton);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.078f, 0.075f, 0.094f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
    }
}
