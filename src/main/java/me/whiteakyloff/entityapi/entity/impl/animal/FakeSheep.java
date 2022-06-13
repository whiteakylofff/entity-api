package me.whiteakyloff.entityapi.entity.impl.animal;

import lombok.Getter;

import me.whiteakyloff.entityapi.entity.FakeAgeableEntity;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeSheep extends FakeAgeableEntity
{
    @Getter
    private DyeColor color;

    @Getter
    private boolean sheared;

    public FakeSheep(Location location) {
        super(EntityType.SHEEP, location);
    }

    public void setColor(DyeColor color) {
        this.color = color;

        this.sendDataWatcherObject(13, BYTE_SERIALIZER, this.generateBitMask());
    }

    public void setSheared(boolean sheared) {
        this.sheared = sheared;

        this.sendDataWatcherObject(13, BYTE_SERIALIZER, this.generateBitMask());
    }

    @SuppressWarnings("all")
    private byte generateBitMask() {
        return (byte) ((this.sheared ? 0x10 : 0) + (this.color != null ? this.color.getWoolData() : 0));
    }
}
