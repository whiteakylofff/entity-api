package me.whiteakyloff.entityapi.entity.impl;

import me.whiteakyloff.entityapi.entity.FakeAgeableEntity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeVillager extends FakeAgeableEntity
{
    public FakeVillager(Location location) {
        super(EntityType.VILLAGER, location);
    }

    public void setProfession(Profession profession) {
        this.sendDataWatcherObject(13, INT_SERIALIZER, profession.ordinal());
    }

    public int getProfession() {
        return this.getDataWatcher().getInteger(13);
    }

    public enum Profession {
        FARMER, LIBRARIAN, PRIEST, BACKSMITH, BUTCHER, NITWIT
    }
}
