/*
 *
 * The MIT License
 *
 * Copyright 2018 Niklas Schultz.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */
package nschultz.game.ui;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import nschultz.game.core.GamePulseSystem;
import nschultz.game.entities.Entity;
import nschultz.game.entities.Player;
import nschultz.game.states.*;
import nschultz.game.states.settings.SettingsState;
import nschultz.game.states.settings.VideoSettingsState;
import nschultz.game.util.NumberNegation;
import nschultz.game.util.TimeDelayedProcedure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public final class GameCanvas extends Canvas {

    private final GamePulseSystem pulseSystem = new GamePulseSystem(this);
    private final GraphicsContext brush = getGraphicsContext2D();
    private final List<Entity> entities = new ArrayList<>();
    private final List<Entity> copyOfEntities = new ArrayList<>();
    private final List<MovingParticle> stars = new ArrayList<>();
    private final Random starRandom = new Random(64);
    private final TimeDelayedProcedure starDelay = new TimeDelayedProcedure(
            250, TimeUnit.MILLISECONDS
    );
    private final Dimension2D resolution;

    private Player player;
    private int score = 0;
    private CurrentGameState currentGameState = new CurrentGameState(
            new MenuState(this)
    );

    private double startingWidth;
    private double startingHeight;
    private boolean shouldRenderLevelChange;
    private boolean starsActive = false;

    private double alpha = 0.0;
    private double delta = 0.009;
    private boolean fadedIn = false;

    GameCanvas(final Dimension2D resolution) {
        this.resolution = resolution;
        setFocusTraversable(true);

        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.P) {
                pause();
            }
            player.onKeyInput(event, true);
            currentGameState.onKeyInput(event, true);
        });
        setOnKeyReleased(event -> {
            player.onKeyInput(event, false);
            currentGameState.onKeyInput(event, false);
        });
    }

    private boolean isPlayableState(final GameState state) {
        return !(state instanceof MenuState) &&
                !(state instanceof VictoryState) &&
                !(state instanceof GameOverState) &&
                !(state instanceof SettingsState) &&
                !(state instanceof VideoSettingsState) &&
                !(state instanceof CreditsState);
    }

    void startPulseSystem() {
        if (pulseSystem.isRunning())
            return;

        startingWidth = getWidth();
        startingHeight = getHeight();
        player = new Player(new Point2D(startingWidth / 2, (startingHeight / 2) + 64), this);
        entities.add(player);
        System.gc();
        pulseSystem.start();
    }

    public void update(final long now) {
        entities.addAll(copyOfEntities);
        copyOfEntities.clear();

        currentGameState.update(now);
        if (starsActive) {
            starDelay.runAfterDelayExact(now, () ->
                    stars.add(new MovingParticle(
                            startingWidth, starRandom.nextInt((int) startingHeight),
                            new Point2D(starRandom.nextInt(2) + 2, 0), Color.WHITE))
            );
        }
        stars.forEach(MovingParticle::update);
        stars.removeIf(MovingParticle::isDead);
    }

    public void render(final long now) {
        currentGameState.render(brush, now);
        stars.forEach(particle -> particle.render(brush));
        if (isPlayableState(currentGameState.value())) {
            renderCurrentLevel();
            renderScore();
        }
        renderFPSCounter();
        renderLevelChange();
    }

    private void renderLevelChange() {
        if (shouldRenderLevelChange) {
            alpha += delta;
            if (alpha >= 0.9) {
                fadedIn = true;
            }
            if (fadedIn) {
                delta = new NumberNegation(delta).doubleValue();
                fadedIn = false;
            }

            if (alpha <= 0) {
                shouldRenderLevelChange = false;
                fadedIn = false;
                alpha = 0.0;
                delta = 0.005;
                return;
            }

            brush.setTextBaseline(VPos.CENTER);
            brush.setTextAlign(TextAlignment.CENTER);
            final double maxWidth = 800;
            brush.setGlobalAlpha(alpha);
            brush.setFont(Font.font(64));
            brush.setFill(Color.WHITE);
            brush.fillText(currentGameState.value().toString(),
                    (startingWidth / 2),
                    startingHeight / 2, maxWidth
            );
            brush.setGlobalAlpha(1);
        }
        brush.setTextBaseline(VPos.BASELINE);
        brush.setTextAlign(TextAlignment.LEFT);
    }

    private void renderScore() {
        brush.setFont(Font.font(14));
        brush.setFill(Color.LIGHTGREEN);
        brush.fillText("Score: " + score, 32, 64);
    }

    private void renderCurrentLevel() {
        brush.setFont(Font.font(14));
        brush.setFill(Color.CYAN);
        brush.fillText(currentGameState.toString(), 32, 48);
    }

    private void renderFPSCounter() {
        brush.setFont(Font.font(14));
        brush.setFill(pulseSystem.isLagging() ? Color.RED : Color.YELLOW);
        brush.fillText("FPS/UPS: " + pulseSystem.fps(), 32, 32);
    }

    public List<Entity> entities() {
        return entities;
    }

    public void spawn(final Entity entity) {
        copyOfEntities.add(entity);
    }

    public Dimension2D resolution() {
        return resolution;
    }

    public void switchGameState(final GameState newState) {
        shouldRenderLevelChange = false;
        currentGameState = currentGameState.switchTo(newState);
        if (isPlayableState(newState)) {
            shouldRenderLevelChange = true;
        }
    }

    public void restart() {
        player = new Player(new Point2D(startingWidth / 2, (startingHeight / 2) + 64), this);
        entities.add(player);

        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.P) {
                pause();
            }
            player.onKeyInput(event, true);
            currentGameState.onKeyInput(event, true);
        });
        setOnKeyReleased(event -> {
            player.onKeyInput(event, false);
            currentGameState.onKeyInput(event, false);
        });
        score = 0;
    }

    void pause() {
        if (isPlayableState(currentGameState.value())) {
            if (pulseSystem.isRunning()) {
                pulseSystem.stop();
            } else {
                pulseSystem.start();
            }
        }
    }

    public double startingWidth() {
        return startingWidth;
    }

    public double startingHeight() {
        return startingHeight;
    }

    public void increaseScore() {
        score++;
    }

    public int score() {
        return score;
    }

    public void disableStars() {
        starsActive = false;
    }

    public void enableStars() {
        starsActive = true;
    }
}
