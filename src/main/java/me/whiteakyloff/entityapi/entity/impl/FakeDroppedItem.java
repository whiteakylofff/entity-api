package me.whiteakyloff.entityapi.entity.impl;

import lombok.Getter;

import me.whiteakyloff.entityapi.entity.FakeEntity;

import me.whiteakyloff.entityapi.packet.entity.WrapperPlayServerSpawnEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public class FakeDroppedItem extends FakeEntity
{
    @Getter
    private ItemStack itemStack;

    public FakeDroppedItem(Location location) {
        this(location, new ItemStack(Material.AIR));
    }

    public FakeDroppedItem(Location location, ItemStack itemStack) {
        super(EntityType.DROPPED_ITEM, location);

        this.setItemStack(itemStack);
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;

        this.getDataWatcher().getWatchableObjects().forEach(wrappedWatchableObject -> wrappedWatchableObject.setDirtyState(false));
        this.sendDataWatcherObject(6, ITEMSTACK_SERIALIZER, itemStack);
    }

    public int getSpawnTypeId() {
        return WrapperPlayServerSpawnEntity.ObjectTypes.ITEM_STACK;
    }
}
