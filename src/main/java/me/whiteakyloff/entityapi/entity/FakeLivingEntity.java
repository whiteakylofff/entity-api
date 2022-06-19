package me.whiteakyloff.entityapi.entity;

import lombok.Getter;

import me.whiteakyloff.entityapi.packet.ProtocolPacketFactory;
import me.whiteakyloff.entityapi.entity.animation.FakeEntityAnimation;

import org.bukkit.Location;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;

@Getter
@SuppressWarnings("unused")
public abstract class FakeLivingEntity extends FakeEntity
{
    protected float health;

    protected int arrowCount;

    protected ChatColor potionColorEffect;

    protected boolean ambientPotionEffect, intelligence;

    public FakeLivingEntity(EntityType entityType, Location location) {
        super(entityType, location);
    }

    public void setHealth(float health) {
        this.health = health;

        this.sendDataWatcherObject(7, FLOAT_SERIALIZER, health);
    }

    public void setPotionColorEffect(ChatColor color) {
        this.potionColorEffect = color;

        this.sendDataWatcherObject(8, INT_SERIALIZER, color.ordinal());
    }

    public void setAmbientPotionEffect(boolean ambientPotionEffect) {
        this.ambientPotionEffect = ambientPotionEffect;

        this.sendDataWatcherObject(9, BOOLEAN_SERIALIZER, ambientPotionEffect);
    }

    public void setArrowCount(int arrowCount) {
        this.arrowCount = arrowCount;

        this.sendDataWatcherObject(10, INT_SERIALIZER, arrowCount);
    }

    public void setAI(boolean intelligence) {
        this.intelligence = intelligence;

        this.sendDataWatcherObject(11, BYTE_SERIALIZER, this.generateBitMask());
    }

    public void playAnimation(FakeEntityAnimation entityAnimation) {
        this.getReceivers().forEach(receiver -> this.playAnimation(receiver, entityAnimation));
    }

    public void playAnimation(Player player, FakeEntityAnimation entityAnimation) {
        ProtocolPacketFactory.createAnimationPacket(this.getEntityId(), entityAnimation);
    }

    private byte generateBitMask() {
        return (byte) (!this.intelligence ? 0x01 : 0);
    }
}