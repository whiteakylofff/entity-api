package me.whiteakyloff.entityapi.entity;

import lombok.Getter;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@Getter
@SuppressWarnings("unused")
public class FakeAgeableEntity extends FakeLivingEntity
{
    @Getter
    protected boolean baby;

    public FakeAgeableEntity(EntityType entityType, Location location) {
        super(entityType, location);
    }

    public void setBaby(boolean baby) {
        this.baby = baby;

        this.sendDataWatcherObject(12, BOOLEAN_SERIALIZER, baby);
    }
}
