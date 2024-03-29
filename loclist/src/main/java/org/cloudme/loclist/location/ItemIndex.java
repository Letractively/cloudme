package org.cloudme.loclist.location;

import java.text.DateFormat;
import java.util.Date;

import org.cloudme.loclist.item.Item;
import org.cloudme.sugar.Entity;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;

/**
 * Defines the order of {@link Item}s on each {@link Location}. Independent of
 * lists.
 * 
 * @author Moritz Petersen
 */
@Cached
public class ItemIndex extends Entity implements Comparable<ItemIndex> {
    @Unindexed
    private int index = -1;
    private Long itemId;
    private Long locationId;
    private Long lastUpdate;
    @Unindexed
    private String text;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemId() {
        return itemId;
    }

    @Override
    public int compareTo(ItemIndex o) {
        if (index == -1) {
            if (o.index == -1) {
                return 0;
            }
            return 1;
        }
        if (o.index == -1) {
            return -1;
        }
        return index - o.index;
    }

    @Override
    public String toString() {
        return "[itemId = " + itemId + ", index = " + index + "]";
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdateString() {
        return DateFormat.getDateTimeInstance().format(new Date(lastUpdate));
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
