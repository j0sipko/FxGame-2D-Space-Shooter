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
package nschultz.game.entities;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import nschultz.game.ui.GameCanvas;

public final class Bullet extends Entity {

    private final GameCanvas game;

    Bullet(final Point2D position, final GameCanvas game) {
        super(position, new Dimension2D(4, 4), game);
        this.game = game;
    }

    @Override
    public void update(final long now) {
        checkCollisionWithEnemies();
        final double velocity = 10;
        moveRight(velocity);
        killIfOutOfBounds();
    }

    private void killIfOutOfBounds() {
        if (xPosition() >= gameWidth())
            kill();
    }

    private void checkCollisionWithEnemies() {
        game.entities().stream()
                .filter(entity -> entity instanceof SimpleEnemy)
                .forEach(entity -> {
                    final SimpleEnemy enemy = (SimpleEnemy) entity;
                    if (hitBox().intersects(
                            enemy.xPosition(), enemy.yPosition(),
                            enemy.width(), enemy.height())) {

                        kill();
                        enemy.kill();
                        game.increaseScore();
                    }
                });

        game.entities().stream()
                .filter(entity -> entity instanceof DisappearingEnemy)
                .forEach(entity -> {
                    final DisappearingEnemy enemy = (DisappearingEnemy) entity;
                    if (hitBox().intersects(
                            enemy.xPosition(), enemy.yPosition(),
                            enemy.width(), enemy.height())) {

                        kill();
                        enemy.kill();
                        game.increaseScore();
                    }
                });

        game.entities().stream()
                .filter(entity -> entity instanceof GrowingEnemy)
                .forEach(entity -> {
                    final GrowingEnemy enemy = (GrowingEnemy) entity;
                    if (hitBox().intersects(
                            enemy.xPosition(), enemy.yPosition(),
                            enemy.width(), enemy.height())) {

                        kill();
                        enemy.kill();
                        game.increaseScore();
                    }
                });

        game.entities().stream()
                .filter(entity -> entity instanceof StoppingEnemy)
                .forEach(entity -> {
                    final StoppingEnemy enemy = (StoppingEnemy) entity;
                    if (hitBox().intersects(
                            enemy.xPosition(), enemy.yPosition(),
                            enemy.width(), enemy.height())) {

                        kill();
                        enemy.kill();
                        game.increaseScore();
                    }
                });

        game.entities().stream()
                .filter(entity -> entity instanceof ChargingEnemy)
                .forEach(entity -> {
                    final ChargingEnemy enemy = (ChargingEnemy) entity;
                    if (hitBox().intersects(
                            enemy.xPosition(), enemy.yPosition(),
                            enemy.width(), enemy.height())) {

                        kill();
                        enemy.kill();
                        game.increaseScore();
                    }
                });

        game.entities().stream()
                .filter(entity -> entity instanceof JumpingEnemy)
                .forEach(entity -> {
                    final JumpingEnemy enemy = (JumpingEnemy) entity;
                    if (hitBox().intersects(
                            enemy.xPosition(), enemy.yPosition(),
                            enemy.width(), enemy.height())) {

                        kill();
                        enemy.kill();
                        game.increaseScore();
                    }
                });

        game.entities().stream()
                .filter(entity -> entity instanceof ReturningEnemy)
                .forEach(entity -> {
                    final ReturningEnemy enemy = (ReturningEnemy) entity;
                    if (hitBox().intersects(
                            enemy.xPosition(), enemy.yPosition(),
                            enemy.width(), enemy.height())) {

                        kill();
                        enemy.kill();
                        game.increaseScore();
                    }
                });
    }


    @Override
    public void render(final GraphicsContext brush, final long now) {
        brush.setFill(Color.WHITE);
        brush.fillRect(xPosition(), yPosition(), width(), height());
    }
}
