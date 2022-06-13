package me.whiteakyloff.entityapi.packet.scoreboard;

import me.whiteakyloff.entityapi.packet.AbstractPacket;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.ScoreboardAction;

@SuppressWarnings("unused")
public class WrapperPlayServerScoreboardScore extends AbstractPacket
{
	public static final PacketType TYPE =
			PacketType.Play.Server.SCOREBOARD_SCORE;

	public WrapperPlayServerScoreboardScore() {
		super(new PacketContainer(TYPE), TYPE);
		this.handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerScoreboardScore(PacketContainer packet) {
		super(packet, TYPE);
	}

	public String getScoreName() {
		return this.handle.getStrings().read(0);
	}

	public void setScoreName(String value) {
		this.handle.getStrings().write(0, value);
	}

	public String getObjectiveName() {
		return this.handle.getStrings().read(1);
	}

	public void setObjectiveName(String value) {
		this.handle.getStrings().write(1, value);
	}

	public int getValue() {
		return this.handle.getIntegers().read(0);
	}

	public void setValue(int value) {
		this.handle.getIntegers().write(0, value);
	}

	public ScoreboardAction getAction() {
		return this.handle.getScoreboardActions().read(0);
	}

	public void setScoreboardAction(ScoreboardAction value) {
		this.handle.getScoreboardActions().write(0, value);
	}
}