package nschultz.game.util;

import nschultz.game.ui.GameCanvas;

import java.util.Objects;

public final class IsNewHighscore implements Result<Boolean> {

    private final GameCanvas game;

    public IsNewHighscore(final GameCanvas game) {
        this.game = Objects.requireNonNull(game);
    }

    @Override
    public Boolean value() {
        return game.score() > new Highscore().score();
    }
}
