package me.whiteakyloff.entityapi.entity.impl.monster;

import lombok.Getter;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeZombieVillager extends FakeZombie
{
    @Getter
    private boolean converting;

    public FakeZombieVillager(Location location) {
        super(EntityType.ZOMBIE_VILLAGER, location);
    }

    public void setConverting(boolean converting) {
        this.converting = converting;

        this.sendDataWatcherObject(15, BOOLEAN_SERIALIZER, converting);
    }

    public void setProfession(Profession profession) {
        this.sendDataWatcherObject(16, INT_SERIALIZER, profession.ordinal());
    }

    public int getProfession() {
        return this.getDataWatcher().getInteger(16);
    }

    public enum Profession {
        FARMER, LIBRARIAN, PRIEST, BACKSMITH, BUTCHER, NITWIT
    }
}
