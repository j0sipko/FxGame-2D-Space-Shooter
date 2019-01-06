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

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;

final class Scaling {

    private final Scale scale = new Scale(1, 1);
    private final Scene scene;

    Scaling(final Scene scene) {
        this.scene = scene;

        // POTENTIAL BUG:
        // Since JavaFx bindings are implemented through weak listeners
        // they might or might not be garbage collected in the future.
        // This could cause the scaling to break.
        // In case this ever occurs, I can do one of the following:
        // 1. Store these listeners as a static reference to keep them from
        // being garbage collected
        // 2. Implement own little event bus system (does not have to be perfect
        // for this kind of task)
        bind();
    }

    private void bind() {
        final ReadOnlyDoubleProperty width = scene.widthProperty();
        scale.xProperty().bind(width.divide(scene.getWidth()));

        final ReadOnlyDoubleProperty height = scene.heightProperty();
        scale.yProperty().bind(height.divide(scene.getHeight()));
    }

    void apply() {
        scene.getRoot().getTransforms().add(scale);
    }
}
