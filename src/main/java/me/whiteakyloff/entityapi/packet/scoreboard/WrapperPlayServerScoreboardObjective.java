package me.whiteakyloff.entityapi.packet.scoreboard;

import me.whiteakyloff.entityapi.packet.AbstractPacket;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.IntEnum;

@SuppressWarnings("unused")
public class WrapperPlayServerScoreboardObjective extends AbstractPacket
{
	public static final PacketType TYPE =
			PacketType.Play.Server.SCOREBOARD_OBJECTIVE;

	public WrapperPlayServerScoreboardObjective() {
		super(new PacketContainer(TYPE), TYPE);
		this.handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerScoreboardObjective(PacketContainer packet) {
		super(packet, TYPE);
	}

	public String getName() {
		return this.handle.getStrings().read(0);
	}

	public void setName(String value) {
		this.handle.getStrings().write(0, value);
	}

	public String getDisplayName() {
		return this.handle.getStrings().read(1);
	}

	public void setDisplayName(String value) {
		this.handle.getStrings().write(1, value);
	}

	public HealthDisplay getHealthDisplay() {
		return this.handle.getEnumModifier(HealthDisplay.class, 2).read(0);
	}

	public void setHealthDisplay(HealthDisplay value) {
		this.handle.getEnumModifier(HealthDisplay.class, 2).write(0, value);
	}

	public int getMode() {
		return this.handle.getIntegers().read(0);
	}

	public void setMode(int value) {
		this.handle.getIntegers().write(0, value);
	}

	public enum HealthDisplay {
		INTEGER, HEARTS
	}

	public static class Mode extends IntEnum
	{
		public static final int ADD_OBJECTIVE = 0;
		public static final int REMOVE_OBJECTIVE = 1;
		public static final int UPDATE_VALUE = 2;

		private static final Mode INSTANCE = new Mode();

		public static Mode getInstance() {
			return INSTANCE;
		}
	}
}