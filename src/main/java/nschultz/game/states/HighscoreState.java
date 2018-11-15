package nschultz.game.states;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import nschultz.game.ui.GameCanvas;
import nschultz.game.util.Highscore;
import nschultz.game.util.NumberNegation;

public class HighscoreState extends GameState {

    private final int highScore = new Highscore().score();
    private double delta = 0.008;
    private double alpha = 1.0;

    HighscoreState(final GameCanvas game) {
        super(game);
    }

    @Override
    public void onKeyInput(final KeyEvent event, final boolean isPressed) {
        if (isPressed) {
            if (event.getCode() == KeyCode.ENTER) {
                game().switchGameState(new MenuState(game()));
            }
        }
    }

    @Override
    public void update(final long now) {
        alpha -= delta;
        if (alpha <= 0.4 || alpha >= 1) {
            delta = new NumberNegation(delta).value();
        }
    }

    @Override
    public void render(final GraphicsContext brush, final long now) {
        final double w = game().resolution().getWidth();
        final double h = game().resolution().getHeight();

        brush.setFill(Color.BLACK);
        brush.fillRect(0, 0, w, h);

        brush.setFill(Color.YELLOW);
        brush.setEffect(new Glow(1.0));
        brush.setFont(Font.font(64));
        brush.setTextAlign(TextAlignment.CENTER);
        brush.setTextBaseline(VPos.CENTER);
        brush.fillText("You current high score is: ", w / 2, h / 4);
        brush.setFill(Color.LIGHTYELLOW);

        brush.setGlobalAlpha(alpha);
        brush.fillText(String.valueOf(highScore), w / 2, h / 2);

        // reset for other render() users
        brush.setGlobalAlpha(1);
        brush.setEffect(null);
        brush.setTextAlign(TextAlignment.LEFT);
        brush.setTextBaseline(VPos.BASELINE);
    }
}

