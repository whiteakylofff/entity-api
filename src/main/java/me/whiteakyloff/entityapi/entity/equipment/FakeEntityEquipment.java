package me.whiteakyloff.entityapi.entity.equipment;

import lombok.var;
import lombok.RequiredArgsConstructor;

import me.whiteakyloff.entityapi.entity.FakeEntity;
import me.whiteakyloff.entityapi.packet.ProtocolPacketFactory;

import com.comphenix.protocol.wrappers.EnumWrappers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class FakeEntityEquipment
{
    private final FakeEntity fakeEntity;

    private final EnumMap<EnumWrappers.ItemSlot, ItemStack> equipmentMap = new EnumMap<>(EnumWrappers.ItemSlot.class);

    public ItemStack getEquipment(EnumWrappers.ItemSlot itemSlot) {
        return this.equipmentMap.get(itemSlot);
    }

    public void setEquipment(EnumWrappers.ItemSlot itemSlot, ItemStack itemStack) {
        this.equipmentMap.put(itemSlot, itemStack);
        this.fakeEntity.getReceivers().forEach(receiver -> this.sendEquipmentPacket(itemSlot, itemStack, receiver));
    }

    public void updateEquipmentPacket(Player player) {
        for (var equipmentEntry : this.equipmentMap.entrySet()) {
            this.sendEquipmentPacket(equipmentEntry.getKey(), equipmentEntry.getValue(), player);
        }
    }

    public void sendEquipmentPacket(EnumWrappers.ItemSlot itemSlot, ItemStack itemStack, Player player) {
        ProtocolPacketFactory.createEntityEquipmentPacket(this.fakeEntity.getEntityId(), itemStack, itemSlot).sendPacket(player);
    }
}
