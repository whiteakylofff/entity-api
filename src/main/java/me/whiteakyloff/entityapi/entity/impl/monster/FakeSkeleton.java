package me.whiteakyloff.entityapi.entity.impl.monster;

import lombok.Getter;

import me.whiteakyloff.entityapi.entity.FakeLivingEntity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeSkeleton extends FakeLivingEntity
{
    @Getter
    private boolean swingingArms;

    public FakeSkeleton(Location location) {
        super(EntityType.SKELETON, location);
    }

    public void setSwingingArms(boolean swingingArms) {
        this.swingingArms = swingingArms;

        this.sendDataWatcherObject(12, BOOLEAN_SERIALIZER, swingingArms);
    }
}
