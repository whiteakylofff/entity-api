package me.whiteakyloff.entityapi.entity.impl.animal;

import lombok.Getter;

import me.whiteakyloff.entityapi.entity.FakeAgeableEntity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakePig extends FakeAgeableEntity
{
    @Getter
    private boolean hasSaddle;

    public FakePig(Location location) {
        super(EntityType.PIG, location);
    }

    public void setHasSaddle(boolean hasSaddle) {
        this.hasSaddle = hasSaddle;

        this.sendDataWatcherObject(13, BOOLEAN_SERIALIZER, hasSaddle);
    }
}
