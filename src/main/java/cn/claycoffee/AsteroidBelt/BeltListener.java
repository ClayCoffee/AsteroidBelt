package cn.claycoffee.AsteroidBelt;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.scheduler.BukkitRunnable;

import cn.claycoffee.ClayTech.ClayTech;
import cn.claycoffee.ClayTech.api.ClayTechManager;

public class BeltListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onFallIntoEarth(PlayerMoveEvent e) {
		int y = (int) e.getPlayer().getLocation().getY();
		if (y <= 0 && e.getPlayer().getWorld().getName().equalsIgnoreCase("CAsteroidBelt")) {
			Player p = e.getPlayer();
			Location loc = p.getLocation();
			int teleportX = (int) (loc.getX() * 16);
			int teleportY = 500;
			int teleportZ = (int) (loc.getZ() * 16);
			World overWorld = Bukkit.getWorld(ClayTech.getOverworld());
			ClayTechManager.setSpaceSuitFallNoCostDurabilityOnce(p);
			ClayTechManager.allowSpaceTeleportOnce(p);
			if (getHighestBlockAt(overWorld, teleportX, teleportZ) == 0) {
				new BukkitRunnable() {

					@Override
					public void run() {
						p.teleport(findSafeLocation(overWorld), TeleportCause.PLUGIN);
					}

				}.runTask(AsteroidBelt.instance);
			} else {
				new BukkitRunnable() {

					@Override
					public void run() {
						p.teleport(new Location(overWorld, teleportX, teleportY, teleportZ), TeleportCause.PLUGIN);
					}

				}.runTask(AsteroidBelt.instance);
			}
			return;
		}
	}

	public static int getHighestBlockAt(World w, int x, int z) {
		int currentHighestY = 0;
		for (int y = 0; y < 255; y++) {
			if (w.getBlockAt(x, y, z).getType() != Material.AIR) {
				currentHighestY = y;
			}
		}
		return currentHighestY;
	}

	public static Location findSafeLocation(World w) {
		boolean pass = false;
		Location ret = null;
		int i = 0;
		while (!pass) {
			int MAX_TRY_TIMES = 40;
			// 最多寻找MAX_TRY_TIMES次,否则返回null.
			if (i <= MAX_TRY_TIMES) {
				int x = new Random().nextInt(10000);
				int z = new Random().nextInt(10000);
				int y = getHighestBlockAt(w, x, z);
				Material BlockType = w.getBlockAt(x, y, z).getType();
				if (BlockType != Material.AIR && BlockType != Material.WATER && BlockType != Material.LAVA) {
					ret = new Location(w, x + 0.0D, y + 0.0D, z + 0.0D);
					pass = true;
				} else {
					i++;
				}
			} else {
				return ret;
			}
		}
		return ret;
	}
}
