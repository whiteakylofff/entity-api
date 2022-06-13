package me.whiteakyloff.entityapi.entity.impl.monster;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeWitherSkeleton extends FakeSkeleton
{
    public FakeWitherSkeleton(Location location) {
        super(EntityType.WITHER_SKELETON, location);
    }
}
