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
package nschultz.game.entities.enemies;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nschultz.game.ui.GameCanvas;
import nschultz.game.util.TimeDelayedProcedure;

import java.util.concurrent.TimeUnit;

public final class SimpleEnemy extends Enemy {

    private final double velocity;
    private final Image[] image = new Image[2];
    private final TimeDelayedProcedure imageDelay = new TimeDelayedProcedure(
            800, TimeUnit.MILLISECONDS
    );
    private int imageIndex = 0;

    public SimpleEnemy(final Point2D position, final double velocity,
                       final GameCanvas game) {

        super(position, new Dimension2D(16, 16), game);
        this.velocity = velocity;

        image[0] = new Image(getClass().getResource("/enemy_animation1.png").toExternalForm());
        image[1] = new Image(getClass().getResource("/enemy_animation2.png").toExternalForm());
    }

    @Override
    public void update(final long now) {
        super.update(now);
        moveLeft(velocity);
        killIfOutOfBounds();

        imageDelay.runAfterDelay(now, () -> {
            if (imageIndex == 0) {
                imageIndex = 1;
            } else {
                imageIndex = 0;
            }
        });
    }

    private void killIfOutOfBounds() {
        if (xPosition() <= 0)
            kill();
    }

    @Override
    public void render(final GraphicsContext brush, final long now) {
        brush.drawImage(image[imageIndex], xPosition(), yPosition(), width(), height());
    }
}
