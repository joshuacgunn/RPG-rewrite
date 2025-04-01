package com.github.joshuacgunn.core.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Abstract base class representing an item in the game world.
 * Provides common functionality for all item types and manages
 * a global registry of all items.
 */
public class Item {
    /** The name of this item */
    private String itemName;

    /** Unique identifier for this item */
    private UUID itemUUID;

    /** Global registry mapping UUIDs to all created items */
    public static Map<UUID, Item> itemMap = new HashMap<>();

    /**
     * Creates a new item and registers it in the global item map.
     *
     * @param itemName The name of the item
     * @param itemUUID The unique identifier for the item
     */
    public Item(String itemName, UUID itemUUID) {
        this.itemName = itemName;
        this.itemUUID = itemUUID;
        itemMap.put(itemUUID, this);
    }

    /**
     * Gets the name of this item.
     *
     * @return The name of this item
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Gets the unique identifier of this item.
     *
     * @return The UUID of this item
     */
    public UUID getItemUUID() {
        return itemUUID;
    }

    /**
     * Returns a list of all registered items.
     *
     * @return A list containing all items
     */
    public static List<Item> getItems() {
        return itemMap.values().stream().toList();
    }

    /**
     * Returns a filtered list of items of the specified type.
     *
     * @param <T> The specific item type to filter by
     * @param itemClass The class object representing the item type
     * @return A list containing only items of the specified type
     */
    public static <T extends Item> List<T> getItemsByType(Class<T> itemClass) {
        return itemMap.values().stream().filter(itemClass::isInstance).map(itemClass::cast).toList();
    }
}