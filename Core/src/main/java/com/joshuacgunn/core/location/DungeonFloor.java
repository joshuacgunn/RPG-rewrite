package com.joshuacgunn.core.location;

import com.joshuacgunn.core.container.Chest;
import com.joshuacgunn.core.entity.Enemy;
import com.joshuacgunn.core.entity.Goblin;
import com.joshuacgunn.core.item.Weapon;

import java.util.*;

/**
 * Represents a floor within a dungeon in the game world.
 * Each floor is identified by a unique UUID and has a specific floor number
 * within its parent dungeon. Floors contain enemies, may have a chest with
 * items, and have a difficulty rating based on various factors.
 */
public class DungeonFloor {
    /** Unique identifier for this dungeon floor */
    private final UUID floorUUID;

    /** The number of this floor within its parent dungeon */
    private final int floorNumber;

    /**
     * The calculated difficulty rating of this floor based on enemies
     * and floor number
     */
    private float difficultyRating;

    /** Reference to the parent dungeon that contains this floor */
    private final Dungeon parentDungeon;

    /** List of enemies present on this floor */
    private ArrayList<Enemy> enemiesOnFloor = new ArrayList<>();

    /** Indicates whether this floor contains a chest */
    private boolean hasChest;

    /** The chest on this floor, if any */
    private Chest chest;

    /** Random number generator for enemy and loot generation */
    Random rand = new Random();

    /**
     * Creates a new dungeon floor with a specified UUID.
     *
     * @param floorUUID The unique identifier for this floor
     * @param parentDungeon The dungeon that contains this floor
     * @param floorNumber The number of this floor within the dungeon
     * @param skipEnemyGeneration If true, enemies will not be generated automatically
     */
    public DungeonFloor(UUID floorUUID, Dungeon parentDungeon, int floorNumber, boolean skipEnemyGeneration) {
        this.floorUUID = floorUUID;
        this.floorNumber = floorNumber;
        this.parentDungeon = parentDungeon;
        if (!skipEnemyGeneration) {
            generateEnemies();
        }
        this.difficultyRating = calculateDifficulty();
        this.hasChest = true;
        this.chest = new Chest(Chest.ChestRarity.COMMON, false, this);
    }

    /**
     * Gets the unique identifier of this floor.
     *
     * @return The UUID of this floor
     */
    public UUID getFloorUUID() {
        return floorUUID;
    }

    /**
     * Generates enemies for this floor based on random probability.
     * The number of enemies depends on a random chance, with higher
     * chances resulting in more enemies.
     */
    private void generateEnemies() {
        float enemyChance = rand.nextFloat();
        for (int i = 0; i < 3; i++) {
            enemiesOnFloor.add(new Goblin(UUID.randomUUID()));
        }
        if (enemyChance < 0.2f) {
            enemiesOnFloor.add(new Goblin(UUID.randomUUID()));
        } else if (enemyChance < .5f) {
            enemiesOnFloor.add(new Goblin(UUID.randomUUID()));
            enemiesOnFloor.add(new Goblin(UUID.randomUUID()));
        } else if (enemyChance < .8f) {
            enemiesOnFloor.add(new Goblin(UUID.randomUUID()));
            enemiesOnFloor.add(new Goblin(UUID.randomUUID()));
            enemiesOnFloor.add(new Goblin(UUID.randomUUID()));
        } else if (enemyChance < .99f) {
            enemiesOnFloor.add(new Goblin(UUID.randomUUID()));
            enemiesOnFloor.add(new Goblin(UUID.randomUUID()));
            enemiesOnFloor.add(new Goblin(UUID.randomUUID()));
            enemiesOnFloor.add(new Goblin(UUID.randomUUID()));
        }
    }

    /**
     * Sets the list of enemies on this floor.
     * Used primarily for loading saved game states.
     *
     * @param enemiesOnFloor The list of enemies to place on this floor
     */
    public void setEnemiesOnFloor(ArrayList<Enemy> enemiesOnFloor) {
        this.enemiesOnFloor = enemiesOnFloor;
    }

    /**
     * Gets the list of enemies currently on this floor.
     *
     * @return List of enemies on this floor
     */
    public ArrayList<Enemy> getEnemiesOnFloor() {
        return this.enemiesOnFloor;
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
     * Gets the parent dungeon that contains this floor.
     *
     * @return The parent dungeon
     */
    public Dungeon getParentDungeon() {
        return parentDungeon;
    }

    /**
     * Sets the difficulty rating of this floor manually.
     *
     * @param rating The new difficulty rating value
     */
    public void setDifficultyRating(float rating) {
        this.difficultyRating = rating;
    }

    /**
     * Calculates the difficulty rating of this floor based on multiple factors:
     * - Floor number (deeper floors are more difficult)
     * - Enemy statistics (HP, damage, defense)
     * - Number of enemies
     * - Variety of enemy types
     *
     * @return The calculated difficulty rating as a float
     */
    public float calculateDifficulty() {
        // Handle empty enemy list case
        if (enemiesOnFloor.isEmpty()) {
            return 0.0f;
        }

        // Base difficulty starts with the floor number
        float difficulty = floorNumber * 0.2f;

        // Total stats across all enemies
        float totalHp = 0;
        float totalDamage = 0;
        float totalDefense = 0;

        // Count different enemy types for variety bonus
        Set<String> enemyTypes = new HashSet<>();

        for (Enemy enemy : enemiesOnFloor) {
            totalHp += enemy.getEntityHp();
            totalDamage += enemy.getCurrentWeapon().getWeaponDamage();
            totalDefense += enemy.getEntityDefense();
            enemyTypes.add(enemy.getClass().getSimpleName());
        }

        // Calculate average stats but don't divide by enemy count
        float hpFactor = totalHp / 100f;
        float damageFactor = totalDamage / 25f;
        float defenseFactor = totalDefense / 25f;

        // Number of enemies is a multiplier rather than divisor
        float enemyCountFactor = 0.75f + (enemiesOnFloor.size() * 0.25f);

        // Variety bonus (more types = more difficult)
        float varietyBonus = (enemyTypes.size() - 1) * 0.15f;

        // Combine factors
        difficulty += (hpFactor + damageFactor + defenseFactor) * enemyCountFactor + varietyBonus;

        // Round to one decimal place
        return Math.round(difficulty * 10) / 100.0f;
    }

    /**
     * Gets the current difficulty rating of this floor.
     *
     * @return The difficulty rating
     */
    public float getDifficultyRating() {
        return this.difficultyRating;
    }

    /**
     * Checks if this floor has a chest.
     *
     * @return true if the floor has a chest, false otherwise
     */
    public boolean isHasChest() {
        return hasChest;
    }

    /**
     * Sets whether this floor has a chest.
     *
     * @param hasChest true if the floor should have a chest, false otherwise
     */
    public void setHasChest(boolean hasChest) {
        this.hasChest = hasChest;
    }

    /**
     * Gets the chest on this floor, if any.
     *
     * @return The chest object on this floor, or null if no chest exists
     */
    public Chest getChest() {
        return chest;
    }
}