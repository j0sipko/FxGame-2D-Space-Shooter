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
package nschultz.game.util;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public final class SpriteSheet {

    private static final SpriteSheet sheet = new SpriteSheet(new Image(
            SpriteSheet.class.getResource("/spritesheet.png").toExternalForm())
    );

    public static SpriteSheet instance() {
        return sheet;
    }

    private final Image spriteSheet;

    private SpriteSheet(final Image spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public Image sprite(final int row, final int col,
                        final int width, final int height) {
        return new WritableImage(
                spriteSheet.getPixelReader(),
                (height * col) - height,
                (width * row) - width,
                width,
                height
        );
    }
}