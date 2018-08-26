package nschultz.game.entities.enemies;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import nschultz.game.entities.Entity;
import nschultz.game.entities.Player;
import nschultz.game.ui.GameCanvas;

public abstract class Enemy extends Entity {

    private final GameCanvas game;

    Enemy(final Point2D position, final Dimension2D size,
          final GameCanvas game) {

        super(position, size, game);
        this.game = game;
    }

    @Override
    public void update(final long now) {
        checkCollisionWithPlayer();
    }

    private void checkCollisionWithPlayer() {
        game.entities().stream()
                .filter(entity -> entity instanceof Player)
                .forEach(player -> {
                    if (hitBox().intersects(
                            player.xPosition(), player.yPosition(),
                            player.width(), player.height())) {
                        player.kill();
                    }
                });
    }
}
