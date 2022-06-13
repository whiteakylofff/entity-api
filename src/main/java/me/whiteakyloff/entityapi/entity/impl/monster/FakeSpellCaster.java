package me.whiteakyloff.entityapi.entity.impl.monster;

import lombok.Getter;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public abstract class FakeSpellCaster extends FakeIllager
{
    @Getter
    private Spell spell;

    public FakeSpellCaster(EntityType entityType, Location location) {
        super(entityType, location);
    }

    public void setSpell(Spell spell) {
        this.spell = spell;

        this.sendDataWatcherObject(13, INT_SERIALIZER, spell.ordinal());
    }

    public enum Spell {
        NONE, SUMMON, ATTACK, WOLOLO
    }
}
