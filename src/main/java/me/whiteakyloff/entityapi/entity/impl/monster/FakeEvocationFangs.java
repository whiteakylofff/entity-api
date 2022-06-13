package me.whiteakyloff.entityapi.entity.impl.monster;

import me.whiteakyloff.entityapi.entity.FakeEntity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeEvocationFangs extends FakeEntity
{
    public FakeEvocationFangs(Location location) {
        super(EntityType.EVOKER_FANGS, location);
    }
}
