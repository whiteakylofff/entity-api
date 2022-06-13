package me.whiteakyloff.entityapi.entity.impl.monster;

import lombok.Getter;

import me.whiteakyloff.entityapi.entity.FakeLivingEntity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeVex extends FakeLivingEntity
{
    @Getter
    private boolean inAttack;

    public FakeVex(Location location) {
        super(EntityType.VEX, location);
    }

    public void setInAttack(boolean inAttack) {
        this.inAttack = inAttack;

        this.sendDataWatcherObject(12, BYTE_SERIALIZER, this.generateBitMask());
    }

    private byte generateBitMask() {
        return (byte) (this.inAttack ? 0x01 : 0);
    }
}
