package com.github.joshuacgunn.core.location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a dungeon location in the game world.
 * A dungeon consists of multiple floors and maintains a reference to the current floor.
 * Extends the base Location class and provides dungeon-specific functionality.
 */
public class Dungeon extends Location {
    /** List of all floors in this dungeon */
    private List<DungeonFloor> floors = new ArrayList<>();

    /** Reference to the current floor the player is on */
    private DungeonFloor currentFloor;

    public boolean isCleared;

    /**
     * Creates a new dungeon with the specified name and UUID.
     * Automatically creates and adds the first floor.
     *
     * @param name The name of the dungeon
     * @param uuid The unique identifier for the dungeon
     */
    public Dungeon(String name, UUID uuid) {
        super(name, uuid);
        addFloor();
    }

    /**
     * Adds a new floor to the dungeon.
     * The floor number is automatically assigned based on the current number of floors.
     * If this is the first floor, it is set as the current floor.
     *
     * @return The newly created dungeon floor
     */
    public DungeonFloor addFloor() {
        int floorNumber = floors.size() + 1;
        DungeonFloor newFloor = new DungeonFloor(UUID.randomUUID(), this, floorNumber);
        floors.add(newFloor);
        if (currentFloor == null) {
            currentFloor = newFloor;
        }
        return newFloor;
    }

    public void clearFloor() {
        currentFloor = floors.get(currentFloor.getFloorNumber() - 2);
        if (currentFloor != null && currentFloor.getFloorNumber() <= 1) {
            isCleared = true;
        }
    }

    /**
     * Gets all floors in this dungeon.
     *
     * @return A list of all dungeon floors
     */
    public List<DungeonFloor> getFloors() {
        return floors;
    }

    /**
     * Gets the current floor of the dungeon.
     *
     * @return The current dungeon floor
     */
    public DungeonFloor getCurrentFloor() {
        return currentFloor;
    }

    /**
     * Sets the current floor of the dungeon.
     *
     * @param currentFloor The dungeon floor to set as current
     */
    public void setCurrentFloor(DungeonFloor currentFloor) {
        this.currentFloor = currentFloor;
    }

    /**
     * Retrieves a dungeon floor by its floor number.
     *
     * @param floorNumber The floor number to search for
     * @return The matching dungeon floor, or null if not found
     */
    public DungeonFloor getFloorByNumber(int floorNumber) {
        try {
            return floors.stream().filter(f -> f.getFloorNumber() == floorNumber).findFirst().orElse(null);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}