package nschultz.game.entities.enemies;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nschultz.game.entities.Player;
import nschultz.game.ui.GameCanvas;

public final class HomingEnemy extends Enemy {

    private Player player;
    private double followingPower = 0.01;

    private final Image image;

    public HomingEnemy(final Point2D position, final GameCanvas game) {
        super(position, new Dimension2D(32, 32), game);

        game.entities().stream()
                .filter(entity -> entity instanceof Player)
                .forEach(entity -> player = (Player) entity);

        image = new Image(
                getClass().getResource("/homing_enemy.png").toExternalForm()
        );
    }

    @Override
    public void update(final long now) {
        super.update(now);
        followPlayer();
        followingPower += 0.0001;
    }

    private void followPlayer() {
        final double diffX = player.xPosition() - xPosition();
        final double diffY = player.yPosition() - yPosition();
        // interpolation
        moveToX(xPosition() + diffX * followingPower);
        moveToY(yPosition() + diffY * followingPower);
    }

    @Override
    public void render(final GraphicsContext brush, final long now) {
        brush.drawImage(image, xPosition(), yPosition(), width(), height());
    }
}
