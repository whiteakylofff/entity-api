package me.whiteakyloff.entityapi.entity.impl.monster;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeHusk extends FakeZombie
{
    public FakeHusk(Location location) {
        super(EntityType.HUSK, location);
    }
}
