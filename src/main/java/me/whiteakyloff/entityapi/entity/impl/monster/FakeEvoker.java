package me.whiteakyloff.entityapi.entity.impl.monster;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeEvoker extends FakeSpellCaster
{
    public FakeEvoker(Location location) {
        super(EntityType.EVOKER, location);
    }
}
