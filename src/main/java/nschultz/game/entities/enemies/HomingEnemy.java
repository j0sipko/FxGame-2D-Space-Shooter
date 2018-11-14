package nschultz.game.entities.enemies;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nschultz.game.entities.Entity;
import nschultz.game.ui.GameCanvas;

import java.util.Objects;

public final class HomingEnemy extends Enemy {

    private final Entity target;
    private double followingPower = 0.01;

    private final Image image;

    public HomingEnemy(final Point2D position, final GameCanvas game,
                       final Entity target) {

        super(position, new Dimension2D(32, 32), game);
        this.target = Objects.requireNonNull(target);
        image = new Image(
                getClass().getResource("/homing_enemy.png").toExternalForm()
        );
    }

    @Override
    public void update(final long now) {
        super.update(now);
        followTarget();
        followingPower += 0.0001;
    }

    private void followTarget() {
        final double diffX = target.xPosition() - xPosition();
        final double diffY = target.yPosition() - yPosition();
        // interpolation
        moveToX(xPosition() + diffX * followingPower);
        moveToY(yPosition() + diffY * followingPower);
    }

    @Override
    public void render(final GraphicsContext brush, final long now) {
        brush.drawImage(image, xPosition(), yPosition(), width(), height());
    }
}
