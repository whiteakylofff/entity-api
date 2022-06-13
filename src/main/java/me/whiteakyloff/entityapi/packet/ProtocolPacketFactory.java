package me.whiteakyloff.entityapi.packet;

import lombok.var;
import lombok.experimental.UtilityClass;

import me.whiteakyloff.entityapi.entity.animation.FakeEntityAnimation;
import me.whiteakyloff.entityapi.packet.entity.*;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.utility.MinecraftProtocolVersion;
import com.comphenix.protocol.utility.MinecraftVersion;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.UUID;

import com.google.common.collect.ImmutableList;

@UtilityClass
@SuppressWarnings("all")
public class ProtocolPacketFactory
{
    public WrapperPlayServerEntityDestroy createDestroyEntityPacket(int... entityIds) {
        var destroyPacket = new WrapperPlayServerEntityDestroy();

        destroyPacket.setEntityIds(entityIds);

        return destroyPacket;
    }

    public WrapperPlayServerMount createMountPacket(int entityId, int... entityIds) {
        var mountPacket = new WrapperPlayServerMount();

        mountPacket.setEntityID(entityId);
        mountPacket.setPassengerIds(entityIds);

        return mountPacket;
    }

    public WrapperPlayServerEntityLook createEntityLookPacket(int entityId, Location location) {
        var lookPacket = new WrapperPlayServerEntityLook();

        lookPacket.setEntityID(entityId);
        lookPacket.setYaw(location.getYaw());
        lookPacket.setPitch(location.getPitch());

        return lookPacket;
    }

    public WrapperPlayServerEntityVelocity createEntityVelocityPacket(int entityId, Vector vector) {
        var velocityPacket = new WrapperPlayServerEntityVelocity();

        velocityPacket.setEntityID(entityId);

        velocityPacket.setVelocityX(vector.getX());
        velocityPacket.setVelocityY(vector.getY());
        velocityPacket.setVelocityZ(vector.getZ());

        return velocityPacket;
    }

    public WrapperPlayServerEntityHeadRotation createEntityHeadRotationPacket(int entityId, Location location) {
        var rotationPacket = new WrapperPlayServerEntityHeadRotation();

        rotationPacket.setEntityID(entityId);
        rotationPacket.setHeadYaw((byte) ((int) (location.getYaw() * 256.0F / 360.0F)));

        return rotationPacket;
    }

    public WrapperPlayServerEntityTeleport createEntityTeleportPacket(int entityId, Location location) {
        var teleportPacket = new WrapperPlayServerEntityTeleport();

        teleportPacket.setEntityID(entityId);

        teleportPacket.setX(location.getX());
        teleportPacket.setY(location.getY());
        teleportPacket.setZ(location.getZ());

        teleportPacket.setYaw(location.getYaw());
        teleportPacket.setPitch(location.getPitch());

        return teleportPacket;
    }

    public WrapperPlayServerAnimation createAnimationPacket(int entityId, FakeEntityAnimation entityAnimation) {
        var animationPacket = new WrapperPlayServerAnimation();

        animationPacket.setEntityID(entityId);
        animationPacket.setAnimation(entityAnimation.ordinal());

        return animationPacket;
    }

    public WrapperPlayServerEntityMetadata createEntityMetadataPacket(int entityId, WrappedDataWatcher dataWatcher) {
        var metadataPacket = new WrapperPlayServerEntityMetadata();

        metadataPacket.setEntityID(entityId);
        metadataPacket.setMetadata(dataWatcher.getWatchableObjects());

        return metadataPacket;
    }


    public WrapperPlayServerEntityEquipment createEntityEquipmentPacket(int entityId, ItemStack itemStack, EnumWrappers.ItemSlot itemSlot) {
        var equipmentPacket = new WrapperPlayServerEntityEquipment();

        equipmentPacket.setEntityID(entityId);
        equipmentPacket.setSlot(itemSlot);
        equipmentPacket.setItem(itemStack);

        return equipmentPacket;
    }

    public WrapperPlayServerPlayerInfo createPlayerInfoPacket(EnumWrappers.PlayerInfoAction action, PlayerInfoData playerInfoData) {
        var playerInfoPacket = new WrapperPlayServerPlayerInfo();

        playerInfoPacket.setAction(action);
        playerInfoPacket.setData(ImmutableList.of(playerInfoData));

        return playerInfoPacket;
    }

    public WrapperPlayServerNamedEntitySpawn createSpawnNamedEntityPacket(int entityId, UUID uuid, Location location, WrappedDataWatcher dataWatcher) {
        var spawnPacket = new WrapperPlayServerNamedEntitySpawn();
        var currentVersion = MinecraftProtocolVersion.getCurrentVersion();
        var beeVersion = MinecraftProtocolVersion.getVersion(MinecraftVersion.BEE_UPDATE);

        spawnPacket.setEntityID(entityId);
        if (currentVersion < beeVersion) {
            spawnPacket.setMetadata(dataWatcher);
        }
        spawnPacket.setPlayerUUID(uuid);

        spawnPacket.setYaw(location.getYaw());
        spawnPacket.setPitch(location.getPitch());
        spawnPacket.setPosition(location.toVector());

        return spawnPacket;
    }

    public WrapperPlayServerSpawnEntity createSpawnEntityPacket(int entityId, int spawnTypeId, Location location, EntityType entityType) {
        var spawnPacket = new WrapperPlayServerSpawnEntity();
        var currentVersion = MinecraftProtocolVersion.getCurrentVersion();
        var beeVersion = MinecraftProtocolVersion.getVersion(MinecraftVersion.BEE_UPDATE);

        spawnPacket.setEntityID(entityId);
        if (currentVersion < beeVersion) {
            spawnPacket.getHandle().getIntegers().write(7, (int) entityType.getTypeId());
        }
        else {
            spawnPacket.getHandle().getEntityTypeModifier().write(0, entityType);
        }
        spawnPacket.setObjectData(spawnTypeId);

        spawnPacket.setX(location.getX());
        spawnPacket.setY(location.getY());
        spawnPacket.setZ(location.getZ());

        spawnPacket.setYaw(location.getYaw());
        spawnPacket.setPitch(location.getPitch());

        return spawnPacket;
    }

    public WrapperPlayServerSpawnLivingEntity createSpawnLivingEntityPacket(int entityId, Location location, EntityType entityType, WrappedDataWatcher dataWatcher) {
        var spawnPacket = new WrapperPlayServerSpawnLivingEntity();
        var currentVersion = MinecraftProtocolVersion.getCurrentVersion();
        var beeVersion = MinecraftProtocolVersion.getVersion(MinecraftVersion.BEE_UPDATE);

        spawnPacket.setEntityID(entityId);
        if (currentVersion < beeVersion) {
            spawnPacket.setMetadata(dataWatcher);
        }
        spawnPacket.setType(entityType);

        spawnPacket.setX(location.getX());
        spawnPacket.setY(location.getY());
        spawnPacket.setZ(location.getZ());

        spawnPacket.setYaw(location.getYaw());
        spawnPacket.setPitch(location.getPitch());

        return spawnPacket;
    }
}
