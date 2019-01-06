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
package nschultz.game.ui;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;

public final class MovingParticle {

    private final Point2D velocity;
    private final Color color;
    private double x;
    private double y;

    MovingParticle(final double x, final double y, final Point2D velocity,
                   final Color color) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.color = color;
    }

    public boolean isDead() {
        return x <= 0;
    }

    public void update() {
        x -= velocity.getX();
        y += velocity.getY();
    }

    public void render(final GraphicsContext brush) {
        brush.setGlobalBlendMode(BlendMode.ADD);
        brush.setFill(color);
        brush.fillOval(x, y, 2, 2);

        // reset
        brush.setGlobalBlendMode(BlendMode.SRC_OVER);
    }
}
