package com.ensinandoJava;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    private SpriteBatch batch;
    private WeatherService weatherService;

    @Override
    public void create() {
        batch = new SpriteBatch();
        weatherService = new WeatherService();
        this.setScreen(new MainMenuScreen(this, weatherService));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
