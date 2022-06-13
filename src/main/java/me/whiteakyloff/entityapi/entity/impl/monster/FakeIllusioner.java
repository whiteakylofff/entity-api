package me.whiteakyloff.entityapi.entity.impl.monster;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeIllusioner extends FakeSpellCaster
{
    public FakeIllusioner(Location location) {
        super(EntityType.ILLUSIONER, location);
    }
}
