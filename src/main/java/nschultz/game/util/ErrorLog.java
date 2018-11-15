package nschultz.game.util;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ErrorLog implements Log {

    private String error;

    ErrorLog(final String error) {
        this.error = Objects.requireNonNull(error);
    }

    @Override
    public void log() {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, error);
    }
}
