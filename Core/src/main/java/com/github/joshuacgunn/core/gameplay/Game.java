package com.github.joshuacgunn.core.gameplay;

import com.github.joshuacgunn.core.entity.Player;
import com.github.joshuacgunn.core.item.Weapon;
import com.github.joshuacgunn.core.location.Town;
import com.github.joshuacgunn.core.save.SaveManager;

import java.io.File;
import java.util.UUID;

public class Game {
    public static void main(String[] args) {
        Player player;
        boolean isNewGame = !(new File("saves/player_save.json").exists());

        if (isNewGame) {
            player = Player.createPlayer();
            Town startingTown = new Town(UUID.randomUUID(), true);
            player.setCurrentLocation(startingTown);
        } else {
            SaveManager.loadState();
            player = SaveManager.loadPlayer();
        }

        player.setCurrentWeapon(Weapon.generateWeapon(0, 7, player.getInventory()));

        // Only save after the game loop if it's a new game
        if (isNewGame) {
            SaveManager.saveState(player);
        }

        GameLoop gameLoop = new GameLoop(player);
    }
}
