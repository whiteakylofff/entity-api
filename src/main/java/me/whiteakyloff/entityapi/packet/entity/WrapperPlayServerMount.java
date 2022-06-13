package me.whiteakyloff.entityapi.packet.entity;

import lombok.var;

import me.whiteakyloff.entityapi.packet.AbstractPacket;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class WrapperPlayServerMount extends AbstractPacket
{
    public static final PacketType TYPE =
            PacketType.Play.Server.MOUNT;

    public WrapperPlayServerMount() {
        super(new PacketContainer(TYPE), TYPE);
    }

    public WrapperPlayServerMount(PacketContainer packet) {
        super(packet, TYPE);
    }

    public int getEntityID() {
        return this.handle.getIntegers().read(0);
    }

    public Entity getEntity(World world) {
        return this.handle.getEntityModifier(world).read(0);
    }

    public Entity getEntity(PacketEvent event) {
        return getEntity(event.getPlayer().getWorld());
    }

    public void setEntityID(int value) {
        this.handle.getIntegers().write(0, value);
    }

    public int[] getPassengerIds() {
        return this.handle.getIntegerArrays().read(0);
    }

    public List<Entity> getPassengers(PacketEvent event) {
        return this.getPassengers(event.getPlayer().getWorld());
    }

    public List<Entity> getPassengers(World world) {
        var ids = this.getPassengerIds();
        var passengers = new ArrayList<Entity>();
        var protocolManager = ProtocolLibrary.getProtocolManager();

        for (var id : ids) {
            var entity = protocolManager.getEntityFromID(world, id);
            if (entity != null) {
                passengers.add(entity);
            }
        }
        return passengers;
    }

    public void setPassengerIds(int[] value) {
        this.handle.getIntegerArrays().write(0, value);
    }

    public void setPassengers(List<Entity> value) {
        var array = new int[value.size()];
        for (var i = 0; i < value.size(); i++) {
            array[i] = value.get(i).getEntityId();
        }
        this.setPassengerIds(array);
    }
}
