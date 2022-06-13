package me.whiteakyloff.entityapi.entity.impl.animal;

import lombok.Getter;

import me.whiteakyloff.entityapi.entity.FakeLivingEntity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeBat extends FakeLivingEntity
{
    @Getter
    private boolean hanging;

    public FakeBat(Location location) {
        super(EntityType.BAT, location);
    }

    public void setHanging(boolean hanging) {
        this.hanging = hanging;

        this.sendDataWatcherObject(12, BYTE_SERIALIZER, this.generateBitMask());
    }

    private byte generateBitMask() {
        return (byte) (this.hanging ? 0x01 : 0);
    }
}
