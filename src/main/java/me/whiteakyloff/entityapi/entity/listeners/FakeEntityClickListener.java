package me.whiteakyloff.entityapi.entity.listeners;

import lombok.var;

import me.whiteakyloff.entityapi.entity.FakeEntity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketAdapter;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class FakeEntityClickListener extends PacketAdapter
{
    public static JavaPlugin JAVA_PLUGIN;

    public FakeEntityClickListener(JavaPlugin javaPlugin) {
        super(javaPlugin, PacketType.Play.Client.USE_ENTITY);
        FakeEntityClickListener.JAVA_PLUGIN = javaPlugin;
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        var player = event.getPlayer();
        var fakeEntity = FakeEntity.getEntityById(event.getPacket().getIntegers().read(0));

        if (fakeEntity == null || fakeEntity.getClickAction() == null) {
            return;
        }
        fakeEntity.getClickAction().accept(player);
    }
}
