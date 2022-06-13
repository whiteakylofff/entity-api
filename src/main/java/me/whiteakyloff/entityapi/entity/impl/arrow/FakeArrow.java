package me.whiteakyloff.entityapi.entity.impl.arrow;

import lombok.Getter;

import me.whiteakyloff.entityapi.entity.FakeEntity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeArrow extends FakeEntity
{
    @Getter
    private boolean critical, noClip;

    public FakeArrow(Location location) {
        this(EntityType.ARROW, location);
    }

    public FakeArrow(EntityType entityType, Location location) {
        super(entityType, location);
    }

    public void setCritical(boolean critical) {
        this.critical = critical;

        this.sendDataWatcherObject(6, BYTE_SERIALIZER, this.generateBitMask());
    }

    public void setNoClip(boolean noClip) {
        this.noClip = noClip;

        this.sendDataWatcherObject(6, BYTE_SERIALIZER, this.generateBitMask());
    }

    private byte generateBitMask() {
        return (byte) ((this.critical ? 0x01 : 0) + (this.noClip ? 0x02 : 0));
    }
}
