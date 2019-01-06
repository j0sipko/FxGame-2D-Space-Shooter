/*
 *
 * The MIT License
 *
 * Copyright 2019 Niklas Schultz.
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
import nschultz.game.util.NumberNegation;
import nschultz.game.io.SpriteSheet;

public final class DisappearingEnemy extends Enemy {

    private static final Image sprite = SpriteSheet.instance().sprite(
            3, 1, 16, 16
    );

    private final double velocity;
    private double alpha = 1.0;
    private double delta = 0.01;

    public DisappearingEnemy(final Point2D position, final double velocity,
                             final GameCanvas game) {

        super(position, new Dimension2D(16, 16), game);
        this.velocity = velocity;
    }

    @Override
    public void update(final long now) {
        super.update(now);
        moveLeft(velocity);
        killIfOutOfBounds();

        alpha -= delta;
        if (alpha <= 0 || alpha >= 1)
            delta = new NumberNegation(delta).value();
    }

    private void killIfOutOfBounds() {
        if (xPosition() <= 0)
            kill();
    }

    @Override
    public void render(final GraphicsContext brush, final long now) {
        brush.setGlobalAlpha(alpha);
        brush.drawImage(sprite, xPosition(), yPosition(), width(), height());

        brush.setGlobalAlpha(1);
    }
}
