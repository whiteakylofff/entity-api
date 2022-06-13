package me.whiteakyloff.entityapi.packet.entity;

import me.whiteakyloff.entityapi.packet.AbstractPacket;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

import org.bukkit.World;
import org.bukkit.util.Vector;
import org.bukkit.entity.Entity;

import java.util.UUID;

@SuppressWarnings("unused")
public class WrapperPlayServerNamedEntitySpawn extends AbstractPacket
{
	public static final PacketType TYPE =
			PacketType.Play.Server.NAMED_ENTITY_SPAWN;

	public WrapperPlayServerNamedEntitySpawn() {
		super(new PacketContainer(TYPE), TYPE);
		this.handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerNamedEntitySpawn(PacketContainer packet) {
		super(packet, TYPE);
	}

	public int getEntityID() {
		return this.handle.getIntegers().read(0);
	}


	public void setEntityID(int value) {
		this.handle.getIntegers().write(0, value);
	}

	public Entity getEntity(World world) {
		return this.handle.getEntityModifier(world).read(0);
	}

	public Entity getEntity(PacketEvent event) {
		return this.getEntity(event.getPlayer().getWorld());
	}

	public UUID getPlayerUUID() {
		return this.handle.getUUIDs().read(0);
	}

	public void setPlayerUUID(UUID value) {
		this.handle.getUUIDs().write(0, value);
	}

	public Vector getPosition() {
		return new Vector(getX(), getY(), getZ());
	}

	public void setPosition(Vector position) {
		this.setX(position.getX());
		this.setY(position.getY());
		this.setZ(position.getZ());
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

	public WrappedDataWatcher getMetadata() {
		return this.handle.getDataWatcherModifier().read(0);
	}

	public void setMetadata(WrappedDataWatcher value) {
		this.handle.getDataWatcherModifier().write(0, value);
	}
}