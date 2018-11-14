package nschultz.game.util;

import nschultz.game.entities.Entity;
import nschultz.game.entities.enemies.Enemy;

import java.util.List;
import java.util.Objects;

public final class IsLevelCompleted implements Result<Boolean> {

    private final List<Entity> entities;

    public IsLevelCompleted(final List<Entity> entities) {
        this.entities = Objects.requireNonNull(entities);
    }

    @Override
    public Boolean value() {
        return entities.stream().noneMatch(entity -> entity instanceof Enemy);
    }
}
