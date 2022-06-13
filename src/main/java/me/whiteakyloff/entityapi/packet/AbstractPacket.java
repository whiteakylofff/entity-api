package me.whiteakyloff.entityapi.packet;

import lombok.Getter;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

import lombok.var;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractPacket
{
	@Getter
	protected PacketContainer handle;

	protected AbstractPacket(PacketContainer handle, PacketType type) {
		if (handle == null) {
			throw new IllegalArgumentException("Packet handle cannot be NULL.");
		}
		if (!Objects.equal(handle.getType(), type)) {
			throw new IllegalArgumentException(handle.getHandle() + " is not a packet of type " + type);
		}
		this.handle = handle;
	}

	public void sendPacket(Player receiver) {
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, this.handle);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Cannot send packet.", e);
		}
	}

	public void receivePacket(Player sender) {
		try {
			ProtocolLibrary.getProtocolManager().recieveClientPacket(sender, this.handle);
		} catch (Exception e) {
			throw new RuntimeException("Cannot recieve packet.", e);
		}
	}

	public void broadcastPacket() {
		var protocolManager = ProtocolLibrary.getProtocolManager();
		try {
			for (Player player : Bukkit.getOnlinePlayers()) {
				protocolManager.sendServerPacket(player, this.handle);
			}
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Cannot send packet.", e);
		}
	}
}
