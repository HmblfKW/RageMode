package org.kwstudios.play.ragemode.events;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.kwstudios.play.ragemode.gameLogic.PlayerList;
import org.kwstudios.play.ragemode.loader.PluginLoader;

public class EventListener implements Listener {

	public EventListener(PluginLoader plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		// Bukkit.broadcastMessage("Ragemode noticed that " + player.getName() +
		// " disconnected.");

		if (PlayerList.isPlayerPlaying(event.getPlayer().getUniqueId().toString())) {
			if (PlayerList.removePlayer(player)) {
				// Bukkit.broadcastMessage(player.getName() + " was removed
				// successfully.");
			}

		}
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getEntity();
			if (arrow.getShooter() instanceof Player) {
				Player shooter = (Player) arrow.getShooter();
				if (PlayerList.isPlayerPlaying(shooter.getUniqueId().toString())) {
					Location location = arrow.getLocation();
					World world = arrow.getWorld();
					double x = location.getX();
					double y = location.getY();
					double z = location.getZ();

					world.createExplosion(x, y, z, 4f, false, false);
					//TODO check if 4f is too strong (4f is TNT strength)
				}
			}
		}
	}

	// @EventHandler
	// public void onPlayerDeath(PlayerDeathEvent event) {
	// event.getEntity().getLastDamageCause().
	//
	// }
}
