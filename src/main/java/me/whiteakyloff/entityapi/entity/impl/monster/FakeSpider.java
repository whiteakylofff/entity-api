package me.whiteakyloff.entityapi.entity.impl.monster;

import lombok.Getter;

import me.whiteakyloff.entityapi.entity.FakeLivingEntity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeSpider extends FakeLivingEntity
{
    @Getter
    private boolean climbing;

    public FakeSpider(Location location) {
        super(EntityType.SPIDER, location);
    }

    public void setClimbing(boolean climbing) {
        this.climbing = climbing;

        this.sendDataWatcherObject(12, BYTE_SERIALIZER, this.generateBitMask());
    }

    private byte generateBitMask() {
        return (byte) (this.climbing ? 0x01 : 0);
    }
}
