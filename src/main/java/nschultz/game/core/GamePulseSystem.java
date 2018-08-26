package nschultz.game.core;

import javafx.animation.AnimationTimer;
import nschultz.game.ui.GameCanvas;
import nschultz.game.util.TimeDelayedProcedure;

import java.util.concurrent.TimeUnit;

public final class GamePulseSystem extends AnimationTimer {

    private final TimeDelayedProcedure delay = new TimeDelayedProcedure(
            1, TimeUnit.SECONDS
    );
    private final GameCanvas game;
    public final int FPS_CAP = 60;
    private int fps;
    private int frames;
    private boolean isRunning;

    public GamePulseSystem(final GameCanvas game) {
        this.game = game;
    }

    @Override
    public void handle(final long now) {
        delay.runAfterDelayExact(now, () -> {
            System.out.println("Entities: " + game.entities().size());
            fps = frames;
            frames = 0;
        });

        game.update(now);
        game.render(now);

        frames++;
    }

    @Override
    public void start() {
        super.start();
        isRunning = true;
    }

    @Override
    public void stop() {
        super.stop();
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int fps() {
        return fps;
    }
}
