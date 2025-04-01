package com.github.joshuacgunn.core.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) representing a floor within a dungeon.
 * This class is used for transferring dungeon floor data between different
 * layers of the application and for serialization purposes.
 */
public class DungeonFloorDTO implements Serializable {
    /** The number of this floor within the dungeon */
    private int floorNumber;

    /** Unique identifier for this floor */
    private UUID floorUUID;

    /** Reference to the parent dungeon that contains this floor */
    private UUID parentDungeonUUID;

    public DungeonFloorDTO() { }

    /**
     * Gets the unique identifier of this floor.
     *
     * @return The UUID of this floor
     */
    public UUID getFloorUUID() {
        return floorUUID;
    }

    /**
     * Sets the unique identifier of this floor.
     *
     * @param floorUUID The UUID to set for this floor
     */
    public void setFloorUUID(UUID floorUUID) {
        this.floorUUID = floorUUID;
    }

    /**
     * Gets the unique identifier of the parent dungeon.
     *
     * @return The UUID of the parent dungeon
     */
    public UUID getParentDungeonUUID() {
        return parentDungeonUUID;
    }

    /**
     * Sets the unique identifier of the parent dungeon.
     *
     * @param parentDungeonUUID The UUID of the parent dungeon to set
     */
    public void setParentDungeonUUID(UUID parentDungeonUUID) {
        this.parentDungeonUUID = parentDungeonUUID;
    }

    /**
     * Gets the floor number of this dungeon floor.
     *
     * @return The floor number
     */
    public int getFloorNumber() {
        return floorNumber;
    }

    /**
     * Sets the floor number of this dungeon floor.
     *
     * @param floorNumber The floor number to set
     */
    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }
}