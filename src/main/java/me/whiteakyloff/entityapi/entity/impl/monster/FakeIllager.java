package me.whiteakyloff.entityapi.entity.impl.monster;

import lombok.Getter;

import me.whiteakyloff.entityapi.entity.FakeLivingEntity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public abstract class FakeIllager extends FakeLivingEntity
{
    @Getter
    private boolean aggressive;

    public FakeIllager(EntityType entityType, Location location) {
        super(entityType, location);
    }

    public void setAggressive(boolean aggressive) {
        this.aggressive = aggressive;

        this.sendDataWatcherObject(12, BYTE_SERIALIZER, this.generateBitMask());
    }

    private byte generateBitMask() {
        return (byte) (this.aggressive ? 0x01 : 0);
    }
}
