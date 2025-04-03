package com.github.joshuacgunn.core.entity;

import com.github.joshuacgunn.core.item.Armor;
import com.github.joshuacgunn.core.item.Weapon;

import java.util.UUID;
import java.util.Random;

/**
 * Represents a Goblin enemy in the game.
 * Extends the {@link Enemy} class.
 */
public class Goblin extends Enemy {

    /**
     * Constructs a new Goblin enemy with the given UUID.
     *
     * @param uuid The unique identifier for the Goblin.
     */
    Random rand = new Random();
    public Goblin(UUID uuid) {
        super("Goblin", uuid, (10*new Random().nextFloat(25f, 35f)) / 10.0f);
        final int armorPieces = rand.nextInt(1, 3);

        for (int i = 0; i < armorPieces; i++) {
            this.generateArmor(rand.nextInt(1, 2), Armor.ArmorQuality.values()[rand.nextInt(0, 2)]);
        }

        Weapon weapon = new Weapon("Goblin Sword", UUID.randomUUID(), 10.0f, 5.0f);
        this.getInventory().addItem(weapon);
        this.currentWeapon = weapon;
    }
}