package nschultz.game.util;

import java.lang.ref.WeakReference;

public final class AttemptsToEnsureGc {

    private final int max;

    public AttemptsToEnsureGc(final int max) {
        this.max = max;
    }

    @SuppressWarnings("UnusedAssignment")
    public void run() {
        int tries = 0;
        var obj = new Object();
        final var ref = new WeakReference<>(obj);
        do {
            if (tries >= max) {
                break;
            }
            obj = null;
            System.gc();
            tries++;
        } while (ref.get() != null);
    }
}
