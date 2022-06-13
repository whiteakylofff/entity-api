package me.whiteakyloff.entityapi.packet.entity;

import me.whiteakyloff.entityapi.packet.AbstractPacket;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;

import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.List;

@SuppressWarnings("unused")
public class WrapperPlayServerEntityMetadata extends AbstractPacket 
{
	public static final PacketType TYPE =
			PacketType.Play.Server.ENTITY_METADATA;

	public WrapperPlayServerEntityMetadata() {
		super(new PacketContainer(TYPE), TYPE);
		this.handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerEntityMetadata(PacketContainer packet) {
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

	public List<WrappedWatchableObject> getMetadata() {
		return this.handle.getWatchableCollectionModifier().read(0);
	}

	public void setMetadata(List<WrappedWatchableObject> value) {
		this.handle.getWatchableCollectionModifier().write(0, value);
	}
}