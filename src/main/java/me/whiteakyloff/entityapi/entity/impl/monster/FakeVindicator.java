package me.whiteakyloff.entityapi.entity.impl.monster;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeVindicator extends FakeIllager
{
    public FakeVindicator(Location location) {
        super(EntityType.VINDICATOR, location);
    }
}
