package me.whiteakyloff.entityapi.entity.impl.monster;

import lombok.Getter;

import me.whiteakyloff.entityapi.entity.FakeAgeableEntity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeZombie extends FakeAgeableEntity
{
    @Getter
    private boolean handsUp;

    public FakeZombie(Location location) {
        this(EntityType.ZOMBIE, location);
    }

    public FakeZombie(EntityType entityType, Location location) {
        super(entityType, location);
    }

    public void setHandsUp(boolean handsUp) {
        this.handsUp = handsUp;

        this.sendDataWatcherObject(14, BOOLEAN_SERIALIZER, handsUp);
    }
}
