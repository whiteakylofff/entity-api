package me.whiteakyloff.entityapi.entity;

import lombok.var;
import lombok.Setter;
import lombok.Getter;

import me.whiteakyloff.entityapi.packet.ProtocolPacketFactory;
import me.whiteakyloff.entityapi.entity.equipment.FakeEntityEquipment;

import com.comphenix.protocol.utility.*;
import com.comphenix.protocol.wrappers.*;
import com.comphenix.protocol.reflect.accessors.Accessors;
import com.comphenix.protocol.reflect.accessors.FieldAccessor;
import com.comphenix.protocol.utility.MinecraftProtocolVersion;

import org.bukkit.Location;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Getter
@SuppressWarnings("unused")
public abstract class FakeEntity
{
    private static final List<FakeEntity> ENTITIES = new ArrayList<>();

    private static final FieldAccessor ENTITY_ID = Accessors.getFieldAccessor(
            MinecraftReflection.getEntityClass(), "entityCount", true);

    private final int entityId;

    protected int spawnTypeId;

    private final EntityType entityType;

    private final List<Player> receivers = new ArrayList<>();

    private final WrappedDataWatcher dataWatcher = new WrappedDataWatcher();
    private final FakeEntityEquipment entityEquipment = new FakeEntityEquipment(this);
    
    private Location location;
    
    private String customName;

    private ChatColor glowingColor;

    @Setter private Consumer<Player> clickAction;

    private boolean silent, burning, sneaking, sprinting, invisible, noGravity, elytraFlying, customNameVisible;

    public FakeEntity(EntityType entityType, Location location) {
        this.entityId = FakeEntity.ENTITY_ID.get(null) instanceof AtomicInteger ? ((AtomicInteger) FakeEntity.ENTITY_ID.get(null)).incrementAndGet() : ((int) FakeEntity.ENTITY_ID.get(null));
        this.entityType = entityType;
        this.location = location;

        if (!(FakeEntity.ENTITY_ID.get(null) instanceof AtomicInteger)) {
            FakeEntity.ENTITY_ID.set(null, entityId + 1);
        }
        FakeEntity.ENTITIES.add(this);
    }

    public void teleport(Location location) {
        this.location = location;

        this.receivers.forEach(this::sendEntityTeleportPacket);
    }

    public boolean hasReceiver(Player player) {
        return this.receivers.contains(player);
    }

    public void addReceiver(Player player) {
        this.receivers.add(player);

        this.sendSpawnPacket(player);
        this.getEntityEquipment().updateEquipmentPacket(player);
    }

    public void removeReceiver(Player player) {
        this.receivers.remove(player);

        this.sendDestroyPacket(player);
    }

    public void setSneaking(boolean sneaking) {
        if (this.sneaking == sneaking) {
            return;
        }
        this.sneaking = sneaking;

        this.sendDataWatcherObject(0, BYTE_SERIALIZER, this.generateBitMask());
    }

    public void setSprinting(boolean sprinting) {
        if (this.sprinting == sprinting) {
            return;
        }
        this.sprinting = sprinting;

        this.sendDataWatcherObject(0, BYTE_SERIALIZER, this.generateBitMask());
    }

    public void setBurning(boolean burning) {
        if (this.burning == burning) {
            return;
        }
        this.burning = burning;

        this.sendDataWatcherObject(0, BYTE_SERIALIZER, this.generateBitMask());
    }

    public void setInvisible(boolean invisible) {
        if (this.invisible == invisible) {
            return;
        }
        this.invisible = invisible;

        this.sendDataWatcherObject(0, BYTE_SERIALIZER, this.generateBitMask());
    }

    public void setElytraFlying(boolean elytraFlying) {
        if (this.elytraFlying == elytraFlying) {
            return;
        }
        this.elytraFlying = elytraFlying;

        this.sendDataWatcherObject(0, BYTE_SERIALIZER, this.generateBitMask());
    }
    
    public void setGlowingColor(ChatColor glowingColor) {
        if (this.glowingColor == glowingColor) {
            return;
        }
        this.glowingColor = glowingColor;

        this.sendDataWatcherObject(0, BYTE_SERIALIZER, this.generateBitMask());
    }

    public void setCustomName(String customName) {
        if (this.customName != null && this.customName.equals(customName)) {
            return;
        }
        this.customName = customName;
        var currentVersion = MinecraftProtocolVersion.getCurrentVersion();
        var aquaticVersion = MinecraftProtocolVersion.getVersion(MinecraftVersion.AQUATIC_UPDATE);

        if (currentVersion < aquaticVersion) {
            this.sendDataWatcherObject(2, STRING_SERIALIZER, customName);
        }
        else {
            this.sendDataWatcherObject(2, CHAT_COMPONENT_SERIALIZER, WrappedChatComponent.fromText(customName));
        }
    }
    
    public void setCustomNameVisible(boolean customNameVisible) {
        if (this.customNameVisible == customNameVisible) {
            return;
        }
        this.customNameVisible = customNameVisible;

        this.sendDataWatcherObject(3, BOOLEAN_SERIALIZER, customNameVisible);
    }

    public void setSilent(boolean silent) {
        if (this.silent == silent) {
            return;
        }
        this.silent = silent;

        this.sendDataWatcherObject(4, BOOLEAN_SERIALIZER, silent);
    }

    public void setNoGravity(boolean noGravity) {
        if (this.noGravity == noGravity) {
            return;
        }
        this.noGravity = noGravity;

        this.sendDataWatcherObject(5, BOOLEAN_SERIALIZER, noGravity);
    }

    public void look(float yaw, float pitch) {
        this.location.setYaw(yaw);
        this.location.setPitch(pitch);

        this.receivers.forEach(receiver -> {
            this.sendEntityLookPacket(receiver);
            this.sendHeadRotationPacket(receiver);
        });
    }

    public void look(Player player, Location location) {
        var vector = location.clone().subtract(location).toVector().normalize();

        this.location.setDirection(vector);
        this.location.setYaw(location.getYaw());
        this.location.setPitch(location.getPitch());

        this.sendEntityLookPacket(player);
        this.sendHeadRotationPacket(player);
    }

    public void setVelocity(Vector vector) {
        this.receivers.forEach(receiver -> this.setVelocity(receiver, vector));
    }

    public void setVelocity(Player player, Vector vector) {
        ProtocolPacketFactory.createEntityVelocityPacket(this.entityId, vector).sendPacket(player);
    }

    public void setPassengers(int... entityIds) {
        this.receivers.forEach(receiver -> this.setPassengers(receiver, entityIds));
    }

    public void setPassengers(FakeEntity... fakeEntities) {
        this.setPassengers(Arrays.stream(fakeEntities).mapToInt(FakeEntity::getEntityId).toArray());
    }

    public void setPassengers(Player player, int... entityIds) {
        ProtocolPacketFactory.createMountPacket(this.entityId, entityIds).sendPacket(player);
    }

    protected final void sendEntityLookPacket(Player player) {
        ProtocolPacketFactory.createEntityLookPacket(this.entityId, this.location).sendPacket(player);
    }

    protected final void sendEntityTeleportPacket(Player player) {
        ProtocolPacketFactory.createEntityTeleportPacket(this.entityId, this.location).sendPacket(player);
    }

    protected final void sendHeadRotationPacket(Player player) {
        ProtocolPacketFactory.createEntityHeadRotationPacket(this.entityId, this.location).sendPacket(player);
    }

    protected void sendSpawnPacket(Player player) {
        if (!this.entityType.isAlive()) {
            ProtocolPacketFactory.createSpawnEntityPacket(this.entityId, this.spawnTypeId, this.location, this.entityType);
        }
        else {
            ProtocolPacketFactory.createSpawnLivingEntityPacket(this.entityId, this.location, this.entityType, this.dataWatcher).sendPacket(player);
        }
        this.sendDataWatcherPacket();
    }

    protected void sendDestroyPacket(Player player) {
        ProtocolPacketFactory.createDestroyEntityPacket(this.entityId).sendPacket(player);
    }

    protected void sendDataWatcherObject(int dataWatcherIndex, WrappedDataWatcher.Serializer serializer, Object value) {
        var wrappedDataWatcherObject = new WrappedDataWatcher.WrappedDataWatcherObject(dataWatcherIndex, serializer);

        this.getDataWatcher().setObject(wrappedDataWatcherObject, value);
        this.sendDataWatcherPacket();
    }

    protected final void sendDataWatcherPacket() {
        this.receivers.forEach(ProtocolPacketFactory.createEntityMetadataPacket(this.entityId, this.dataWatcher)::sendPacket);
    }

    private byte generateBitMask() {
        return (byte) ((this.burning ? 0x01 : 0) + (this.sneaking ? 0x02 : 0) + (this.sprinting ? 0x08 : 0) + (this.invisible ? 0x20 : 0) + (this.glowingColor != null ? 0x40 : 0) + (this.elytraFlying ? 0x80 : 0));
    }

    public static List<FakeEntity> getEntities() {
        return Collections.unmodifiableList(FakeEntity.ENTITIES);
    }

    public static FakeEntity getEntityById(int id) {
        return FakeEntity.getEntities().stream()
                .filter(fakeEntity -> fakeEntity.getEntityId() == id).findFirst().orElse(null);
    }

    protected static final int CURRENT_VERSION = MinecraftProtocolVersion.getCurrentVersion();
    protected static final int AQUATIC_VERSION = MinecraftProtocolVersion.getVersion(MinecraftVersion.AQUATIC_UPDATE);
    protected static final int VILLAGE_VERSION = MinecraftProtocolVersion.getVersion(MinecraftVersion.VILLAGE_UPDATE);
    protected static final int BEE_UPDATE = MinecraftProtocolVersion.getVersion(MinecraftVersion.BEE_UPDATE);
    protected static final int NETHER_UPDATE = MinecraftProtocolVersion.getVersion(MinecraftVersion.NETHER_UPDATE);
    protected static final int NETHER_UPDATE_2 = MinecraftProtocolVersion.getVersion(MinecraftVersion.NETHER_UPDATE_2);
    protected static final int CAVES_CLIFFS_1 = MinecraftProtocolVersion.getVersion(MinecraftVersion.CAVES_CLIFFS_1);

    protected static final WrappedDataWatcher.Serializer BYTE_SERIALIZER = WrappedDataWatcher.Registry.get(Byte.class);
    protected static final WrappedDataWatcher.Serializer INT_SERIALIZER = WrappedDataWatcher.Registry.get(Integer.class);
    protected static final WrappedDataWatcher.Serializer FLOAT_SERIALIZER = WrappedDataWatcher.Registry.get(Float.class);
    protected static final WrappedDataWatcher.Serializer STRING_SERIALIZER = WrappedDataWatcher.Registry.get(String.class);
    protected static final WrappedDataWatcher.Serializer BOOLEAN_SERIALIZER = WrappedDataWatcher.Registry.get(Boolean.class);
    protected static final WrappedDataWatcher.Serializer UUID_SERIALIZER = WrappedDataWatcher.Registry.getUUIDSerializer(true);
    protected static final WrappedDataWatcher.Serializer ROTATION_SERIALIZER = WrappedDataWatcher.Registry.get(Vector3F.getMinecraftClass());
    protected static final WrappedDataWatcher.Serializer CHAT_COMPONENT_SERIALIZER = WrappedDataWatcher.Registry.getChatComponentSerializer();
    protected static final WrappedDataWatcher.Serializer ITEMSTACK_SERIALIZER = WrappedDataWatcher.Registry.get(MinecraftReflection.getItemStackClass());
}
