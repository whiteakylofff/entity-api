package me.whiteakyloff.entityapi.entity.impl.monster;

import me.whiteakyloff.entityapi.entity.FakeLivingEntity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeGiant extends FakeLivingEntity
{
    public FakeGiant(Location location) {
        super(EntityType.GIANT, location);
    }
}
