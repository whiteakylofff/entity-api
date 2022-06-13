package me.whiteakyloff.entityapi.entity.impl;

import lombok.Getter;

import me.whiteakyloff.entityapi.entity.FakeEntity;

import com.comphenix.protocol.wrappers.Vector3F;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeArmorStand extends FakeEntity
{
    @Getter
    private boolean marker, small, basePlate, arms;

    public FakeArmorStand(Location location) {
        super(EntityType.ARMOR_STAND, location);
    }

    public void setSmall(boolean small) {
        this.small = small;

        this.sendDataWatcherObject(11, BYTE_SERIALIZER, this.generateBitMask());
    }

    public void setArms(boolean arms) {
        this.arms = arms;

        this.sendDataWatcherObject(11, BYTE_SERIALIZER, this.generateBitMask());
    }

    public void setBasePlate(boolean basePlate) {
        this.basePlate = basePlate;

        this.sendDataWatcherObject(11, BYTE_SERIALIZER, this.generateBitMask());
    }

    public void setMarker(boolean marker) {
        this.marker = marker;

        this.sendDataWatcherObject(11, BYTE_SERIALIZER, this.generateBitMask());
    }

    public void setHeadRotation(Vector3F vector3F) {
        this.sendDataWatcherObject(12, ROTATION_SERIALIZER, vector3F);
    }

    public void setBodyRotation(Vector3F vector3F) {
        this.sendDataWatcherObject(13, ROTATION_SERIALIZER, vector3F);
    }

    public void setLeftArmRotation(Vector3F vector3F) {
        this.sendDataWatcherObject(14, ROTATION_SERIALIZER, vector3F);
    }

    public void setRightArmRotation(Vector3F vector3F) {
        this.sendDataWatcherObject(15, ROTATION_SERIALIZER, vector3F);
    }

    public void setLeftLegRotation(Vector3F vector3F) {
        this.sendDataWatcherObject(16, ROTATION_SERIALIZER, vector3F);
    }

    public void setRightLegRotation(Vector3F vector3F) {
        this.sendDataWatcherObject(17, ROTATION_SERIALIZER, vector3F);
    }

    private byte generateBitMask() {
        return (byte) ((this.small ? 0x01 : 0) + (this.arms ? 0x04 : 0) + (!this.basePlate ? 0x08 : 0) + (this.marker ? 0x10 : 0));
    }
}
