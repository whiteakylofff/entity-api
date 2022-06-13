package me.whiteakyloff.entityapi.packet.entity;

import me.whiteakyloff.entityapi.packet.AbstractPacket;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.PacketConstructor;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.UUID;

@SuppressWarnings("unused")
public class WrapperPlayServerSpawnLivingEntity extends AbstractPacket
{
    public static PacketType TYPE =
            PacketType.Play.Server.SPAWN_ENTITY_LIVING;

    private static PacketConstructor entityConstructor;

    public WrapperPlayServerSpawnLivingEntity() {
        super(new PacketContainer(TYPE), TYPE);
        this.handle.getModifier().writeDefaults();
    }

    public WrapperPlayServerSpawnLivingEntity(Entity entity) {
        super(fromEntity(entity), TYPE);
    }

    public WrapperPlayServerSpawnLivingEntity(PacketContainer packet) {
        super(packet, TYPE);
    }

    private static PacketContainer fromEntity(Entity entity) {
        if (entityConstructor == null)
            entityConstructor = ProtocolLibrary.getProtocolManager()
                    .createPacketConstructor(TYPE, entity);
        return entityConstructor.createPacket(entity);
    }

    public int getEntityID() {
        return this.handle.getIntegers().read(0);
    }

    public Entity getEntity(World world) {
        return this.handle.getEntityModifier(world).read(0);
    }

    public Entity getEntity(PacketEvent event) {
        return this.getEntity(event.getPlayer().getWorld());
    }

    public UUID getUniqueId() {
        return this.handle.getUUIDs().read(0);
    }

    public void setUniqueId(UUID value) {
        this.handle.getUUIDs().write(0, value);
    }

    public void setEntityID(int value) {
        this.handle.getIntegers().write(0, value);
    }

    @SuppressWarnings("deprecation")
    public EntityType getType() {
        return EntityType.fromId(this.handle.getIntegers().read(1));
    }

    @SuppressWarnings("deprecation")
    public void setType(EntityType value) {
        this.handle.getIntegers().write(1, (int) value.getTypeId());
    }

    public double getX() {
        return this.handle.getDoubles().read(0);
    }

    public void setX(double value) {
        this.handle.getDoubles().write(0, value);
    }

    public double getY() {
        return this.handle.getDoubles().read(1);
    }

    public void setY(double value) {
        this.handle.getDoubles().write(1, value);
    }

    public double getZ() {
        return this.handle.getDoubles().read(2);
    }

    public void setZ(double value) {
        this.handle.getDoubles().write(2, value);
    }

    public float getYaw() {
        return (this.handle.getBytes().read(0) * 360.F) / 256.0F;
    }

    public void setYaw(float value) {
        this.handle.getBytes().write(0, (byte) (value * 256.0F / 360.0F));
    }

    public float getPitch() {
        return (this.handle.getBytes().read(1) * 360.F) / 256.0F;
    }

    public void setPitch(float value) {
        this.handle.getBytes().write(1, (byte) (value * 256.0F / 360.0F));
    }

    public float getHeadPitch() {
        return (this.handle.getBytes().read(2) * 360.F) / 256.0F;
    }

    public void setHeadPitch(float value) {
        this.handle.getBytes().write(2, (byte) (value * 256.0F / 360.0F));
    }

    public double getVelocityX() {
        return this.handle.getIntegers().read(2) / 8000.0D;
    }

    public void setVelocityX(double value) {
        this.handle.getIntegers().write(2, (int) (value * 8000.0D));
    }

    public double getVelocityY() {
        return this.handle.getIntegers().read(3) / 8000.0D;
    }

    public void setVelocityY(double value) {
        this.handle.getIntegers().write(3, (int) (value * 8000.0D));
    }

    public double getVelocityZ() {
        return this.handle.getIntegers().read(4) / 8000.0D;
    }

    public void setVelocityZ(double value) {
        this.handle.getIntegers().write(4, (int) (value * 8000.0D));
    }

    public WrappedDataWatcher getMetadata() {
        return this.handle.getDataWatcherModifier().read(0);
    }

    public void setMetadata(WrappedDataWatcher value) {
        this.handle.getDataWatcherModifier().write(0, value);
    }
}