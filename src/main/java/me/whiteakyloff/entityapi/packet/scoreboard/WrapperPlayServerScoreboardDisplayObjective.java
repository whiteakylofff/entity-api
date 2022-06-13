package me.whiteakyloff.entityapi.packet.scoreboard;

import me.whiteakyloff.entityapi.packet.AbstractPacket;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

@SuppressWarnings("unused")
public class WrapperPlayServerScoreboardDisplayObjective extends AbstractPacket
{
	public static final PacketType TYPE =
			PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE;

	public WrapperPlayServerScoreboardDisplayObjective() {
		super(new PacketContainer(TYPE), TYPE);
		this.handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerScoreboardDisplayObjective(PacketContainer packet) {
		super(packet, TYPE);
	}

	public int getPosition() {
		return this.handle.getIntegers().read(0);
	}

	public void setPosition(int value) {
		this.handle.getIntegers().write(0, value);
	}

	public String getScoreName() {
		return this.handle.getStrings().read(0);
	}

	public void setScoreName(String value) {
		this.handle.getStrings().write(0, value);
	}
}