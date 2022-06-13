package me.whiteakyloff.entityapi.entity.impl;


import lombok.var;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import me.whiteakyloff.entityapi.entity.FakeLivingEntity;
import me.whiteakyloff.entityapi.entity.listeners.FakeEntityClickListener;
import me.whiteakyloff.entityapi.entity.util.MojangUtil;
import me.whiteakyloff.entityapi.packet.ProtocolPacketFactory;
import me.whiteakyloff.entityapi.packet.scoreboard.WrapperPlayServerScoreboardTeam;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.utility.MinecraftProtocolVersion;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.utility.MinecraftVersion;
import com.comphenix.protocol.wrappers.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.google.common.collect.ImmutableList;

@Getter
@SuppressWarnings("unused")
public class FakePlayer extends FakeLivingEntity
{
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private final UUID uuid;
    private final String name;

    private MojangUtil.Skin mojangSkin;
    private WrappedGameProfile wrappedGameProfile;

    public FakePlayer(Location location) {
        this("Steve", location);
    }
    public FakePlayer(String skin, Location location) {
        this(MojangUtil.getSkinTextures(skin), location);
    }
    
    public FakePlayer(MojangUtil.Skin skin, Location location) {
        super(EntityType.PLAYER, location);

        this.mojangSkin = skin;

        this.uuid = UUID.randomUUID();
        this.name = String.format("§8NPC [%s]", RANDOM.nextInt(0, 99999));

        this.updateSkinPart(PlayerSkinPart.TOTAL);
    }


    public void updateSkinPart(byte skinParts) {
        var beeVersion = MinecraftProtocolVersion.getVersion(MinecraftVersion.BEE_UPDATE);
        var currentVersion = MinecraftProtocolVersion.getVersion(ProtocolLibrary.getProtocolManager().getMinecraftVersion());

        this.sendDataWatcherObject(currentVersion >= beeVersion ? 16 : 13, BYTE_SERIALIZER, skinParts);
    }

    public void setSkin(String skinName) {
        this.setSkin(MojangUtil.getSkinTextures(skinName));
    }

    public void setSkin(MojangUtil.Skin mojangSkin) {
        this.getReceivers().forEach(receiver -> this.setSkin(receiver, mojangSkin));
    }

    public void setSkin(Player player, MojangUtil.Skin mojangSkin) {
        this.mojangSkin = mojangSkin;

        this.sendPlayerInfoPacket(EnumWrappers.PlayerInfoAction.ADD_PLAYER, player);

        Bukkit.getScheduler().runTaskLater(FakeEntityClickListener.JAVA_PLUGIN, () -> this.sendPlayerInfoPacket(EnumWrappers.PlayerInfoAction.REMOVE_PLAYER, player), 30L);
    }

    public void updateSkinPart(PlayerSkinPart... playerSkinParts) {
        var skinParts = (byte) 0x00;

        for (var playerSkinPart : playerSkinParts) {
            skinParts += playerSkinPart.getBitMask();
        }
        this.updateSkinPart(skinParts);
    }

    public void sendSpawnPacket(Player player) {
        var teamName = this.getTeamName();

        this.sendPlayerInfoPacket(EnumWrappers.PlayerInfoAction.ADD_PLAYER, player);
        this.sendTeamPacket(teamName, player, WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED);

        ProtocolPacketFactory.createSpawnNamedEntityPacket(this.getEntityId(), this.uuid, this.getLocation(), this.getDataWatcher()).sendPacket(player);

        this.sendEntityLookPacket(player);
        this.sendHeadRotationPacket(player);
        this.sendTeamPacket(teamName, player, WrapperPlayServerScoreboardTeam.Mode.PLAYERS_ADDED);

        Bukkit.getScheduler().runTaskLater(FakeEntityClickListener.JAVA_PLUGIN, () -> {
            this.sendDataWatcherPacket();
            this.sendPlayerInfoPacket(EnumWrappers.PlayerInfoAction.REMOVE_PLAYER, player);
        }, 100L);
    }

    public void sendDestroyPacket(Player player) {
        super.sendDestroyPacket(player);

        this.sendTeamPacket(getTeamName(), player, WrapperPlayServerScoreboardTeam.Mode.TEAM_REMOVED);
    }

    private void sendPlayerInfoPacket(EnumWrappers.PlayerInfoAction action, Player player) {
        this.wrappedGameProfile = new WrappedGameProfile(this.uuid, this.name);
        if (this.mojangSkin != null) {
            this.wrappedGameProfile.getProperties()
                    .put("textures", new WrappedSignedProperty("textures", this.mojangSkin.getValue(), this.mojangSkin.getSignature()));
        }
        var playerInfoData = new PlayerInfoData(wrappedGameProfile, 0,
                EnumWrappers.NativeGameMode.ADVENTURE, WrappedChatComponent.fromText(name));

        ProtocolPacketFactory.createPlayerInfoPacket(action, playerInfoData).sendPacket(player);
    }

    private void sendTeamPacket(String teamName, Player player, int mode) {
        var scoreboardTeam = new WrapperPlayServerScoreboardTeam();
        var version = ProtocolLibrary.getProtocolManager().getProtocolVersion(player);
        var aquaticVersion = MinecraftProtocolVersion.getVersion(MinecraftVersion.AQUATIC_UPDATE);

        scoreboardTeam.setName(teamName);

        if (version >= aquaticVersion) {
            scoreboardTeam.getHandle().getIntegers().write(0, mode);
            scoreboardTeam.getHandle().getStrings().write(1, "never");
            scoreboardTeam.getHandle().getStrings().write(2, "never");

            if (mode == WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED || mode == WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED) {
                scoreboardTeam.getHandle().getIntegers().write(1, 0);
                scoreboardTeam.getHandle().getChatComponents().write(0, WrappedChatComponent.fromText(ChatColor.stripColor(teamName)));
                scoreboardTeam.getHandle().getChatComponents().write(1, WrappedChatComponent.fromText(this.getGlowingColor() == null ? "§8" : this.getGlowingColor().toString()));
                scoreboardTeam.getHandle().getEnumModifier(ChatColor.class, MinecraftReflection.getMinecraftClass("EnumChatFormat")).write(0, this.getGlowingColor() == null ? ChatColor.RESET : this.getGlowingColor());
            } else {
                scoreboardTeam.setPlayers(ImmutableList.of(this.name));
            }
        } else {
            scoreboardTeam.setMode(mode);
            scoreboardTeam.setCollisionRule("never");
            scoreboardTeam.setNameTagVisibility("never");

            if (mode == WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED || mode == WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED) {
                scoreboardTeam.setPackOptionData(0);
                scoreboardTeam.setDisplayName(teamName);
                scoreboardTeam.setColor(this.getGlowingColor() == null ? 0 : this.getGlowingColor().ordinal());
                scoreboardTeam.setPrefix(this.getGlowingColor() == null ? "§8" : this.getGlowingColor().toString());
            } else {
                scoreboardTeam.setPlayers(ImmutableList.of(this.name));
            }
        }
        scoreboardTeam.sendPacket(player);
    }

    @Override
    public void setGlowingColor(ChatColor glowingColor) {
        super.setGlowingColor(glowingColor);

        this.getReceivers().forEach(receiver -> this.sendTeamPacket(this.getTeamName(), receiver, WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED));
    }

    protected synchronized String getTeamName() {
        var teamName = (this.name + "_TEAM");

        if (teamName.length() > 16) {
            teamName = teamName.substring(0, 16);
        }
        return teamName;
    }

    @RequiredArgsConstructor
    public enum PlayerSkinPart {
        CAPE((byte) 0x01), JACKET((byte) 0x02), RIGHT_HAND((byte) 0x04), LEFT_HAND((byte) 0x08), RIGHT_LEG((byte) 0x10), LEFT_LEG((byte) 0x20), HAT((byte) 0x40),

        TOTAL((byte) (CAPE.getBitMask() + JACKET.getBitMask() + RIGHT_HAND.getBitMask() + LEFT_HAND.getBitMask() + RIGHT_LEG.getBitMask() + LEFT_LEG.getBitMask() + HAT.getBitMask()));

        @Getter
        private final byte bitMask;
    }
}