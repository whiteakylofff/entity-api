package me.whiteakyloff.entityapi.entity.impl.arrow;

import lombok.Getter;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@SuppressWarnings("unused")
public class FakeTippedArrow extends FakeArrow
{
    @Getter
    private Color color;

    public FakeTippedArrow(Location location) {
        super(EntityType.TIPPED_ARROW, location);
    }

    public void setColor(Color color) {
        this.color = color;

        this.sendDataWatcherObject(7, INT_SERIALIZER, color.asBGR());
    }
}
