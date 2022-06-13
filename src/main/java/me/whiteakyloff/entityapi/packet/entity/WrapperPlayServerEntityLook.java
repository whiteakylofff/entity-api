package me.whiteakyloff.entityapi.packet.entity;

import me.whiteakyloff.entityapi.packet.AbstractPacket;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import org.bukkit.World;
import org.bukkit.entity.Entity;

@SuppressWarnings("unused")
public class WrapperPlayServerEntityLook extends AbstractPacket 
{
	public static final PacketType TYPE = PacketType.Play.Server.ENTITY_LOOK;

	public WrapperPlayServerEntityLook() {
		super(new PacketContainer(TYPE), TYPE);
		this.handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerEntityLook(PacketContainer packet) {
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

	public boolean getOnGround() {
		return this.handle.getBooleans().read(0);
	}

	public void setOnGround(boolean value) {
		this.handle.getBooleans().write(0, value);
	}
}