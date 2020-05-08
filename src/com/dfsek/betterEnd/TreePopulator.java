package com.dfsek.betterEnd;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;

public class TreePopulator extends BlockPopulator {
	Main main = Main.getInstance();
	public void populate(World world, Random random, Chunk chunk) {

		int min = main.getConfig().getInt("trees.min-per-chunk");
		int max = main.getConfig().getInt("trees.max-per-chunk");
		int aetherM = main.getConfig().getInt("aether.tree-multiplier");
		int obMax = main.getConfig().getInt("trees.obsidian-pillars.max-height");
		int obMin = main.getConfig().getInt("trees.obsidian-pillars.min-height");
		int amount = random.nextInt(max-min)+min;  // Amount of trees
		if(chunk.getX() < 10 && chunk.getZ() < 10) return;
		for (int i = 1; i < amount; i++) {
			int X = random.nextInt(15);
			int Z = random.nextInt(15);
			int Y;
			for (Y = world.getMaxHeight()-1; chunk.getBlock(X, Y, Z).getType() == Material.AIR && Y>0; Y--); // Find the highest block of the (X,Z) coordinate chosen.
			if (Y > 1 && Y < 255) {
				Location blockLocation = chunk.getBlock(X, Y, Z).getLocation();
				switch(Main.getBiome(blockLocation.getBlockX(), blockLocation.getBlockZ(), world.getSeed())){
				case "END":
					if(blockLocation.getBlock().getType() == Material.END_STONE && random.nextInt(10) < 8) {
						world.generateTree(blockLocation.add(0,1,0), TreeType.CHORUS_PLANT);
					}
					break;
				case "AETHER":
					for (int j = 0; j < aetherM; j++) {
						X = random.nextInt(15);
						Z = random.nextInt(15);
						for (Y = world.getMaxHeight()-1; chunk.getBlock(X, Y, Z).getType() == Material.AIR && Y>0; Y--);
						int tree = random.nextInt(100);
						if(blockLocation.getBlock().getType() == Material.GRASS_BLOCK || blockLocation.subtract(0, 1, 0).getBlock().getType() == Material.GRASS_BLOCK) {
							if(tree < 45) {
								world.generateTree(blockLocation, TreeType.TREE);
							}  else if(tree < 93) {
								world.generateTree(blockLocation, TreeType.JUNGLE_BUSH);
							} else {
								world.generateTree(blockLocation, TreeType.BIG_TREE);
							}
						}
					}
					break;
				case "SHATTERED_END":
					if(blockLocation.getBlock().getType() == Material.END_STONE && random.nextInt(10) < 7) {
						if(random.nextInt(100) < 60) {
							int[] upBound = {random.nextInt(obMax-obMin)+obMin, 0, 0, 0};
							int[] lowBound = {random.nextInt(obMax-obMin)+obMin, 0, 0, 0};
							int maxH = 0;
							int maxHVal = upBound[0];
							for(int j = 1; j < upBound.length; j++) {
								upBound[j] = upBound[j-1] + random.nextInt(5)-2;
								if(upBound[j] > maxHVal) {
									maxH = j;
									maxHVal = upBound[j];
								}
							}
							for(int j = 1; j < lowBound.length; j++) {
								lowBound[j] = lowBound[j-1] + random.nextInt(7)-3;
							}
							for(int j = -lowBound[0]; j < upBound[0]; j++) {
								world.getBlockAt((chunk.getX()*16)+X, Y+j, (chunk.getZ()*16)+Z).setType(Material.OBSIDIAN);
							}
							for(int j = -lowBound[1]; j < upBound[1]; j++) {
								world.getBlockAt((chunk.getX()*16)+X+1, Y+j, (chunk.getZ()*16)+Z).setType(Material.OBSIDIAN);
							}
							for(int j = -lowBound[2]; j < upBound[2]; j++) {
								world.getBlockAt((chunk.getX()*16)+X, Y+j, (chunk.getZ()*16)+Z+1).setType(Material.OBSIDIAN);
							}
							for(int j = -lowBound[3]; j < upBound[3]; j++) {
								world.getBlockAt((chunk.getX()*16)+X+1, Y+j, (chunk.getZ()*16)+Z+1).setType(Material.OBSIDIAN);
							}
							if(random.nextInt(100) < 15) {
								//Entity crystal = EntityType.ENDER_CRYSTAL;
								//EnderCrystal crystal2 = (EnderCrystal) crystal;
								switch(maxH) {
								case 0:
									world.spawn(new Location(world, (chunk.getX()*16)+X+0.5, upBound[0]+Y, (chunk.getZ()*16)+Z+0.5), EnderCrystal.class, (enderCrystal) -> enderCrystal.setShowingBottom(false));
									break;
								case 1:
									world.spawn(new Location(world, (chunk.getX()*16)+X+1.5, upBound[1]+Y, (chunk.getZ()*16)+Z+0.5), EnderCrystal.class, (enderCrystal) -> enderCrystal.setShowingBottom(false));
									break;
								case 2:
									world.spawn(new Location(world, (chunk.getX()*16)+X+0.5, upBound[2]+Y, (chunk.getZ()*16)+Z+1.5), EnderCrystal.class, (enderCrystal) -> enderCrystal.setShowingBottom(false));
									break;
								case 3:
									world.spawn(new Location(world, (chunk.getX()*16)+X+1.5, upBound[3]+Y, (chunk.getZ()*16)+Z+1.5), EnderCrystal.class, (enderCrystal) -> enderCrystal.setShowingBottom(false));
									break;
								}
							}
						} else {
							int upBound = (int) (random.nextInt((int) ((obMax*0.75)-(obMin*0.75)))+(obMin*0.75));
							int lowBound = (int) (random.nextInt((int) ((obMax*0.75)-(obMin*0.75)))+(obMin*0.75));
							for(int j = -lowBound; j < upBound; j++) {
								world.getBlockAt((chunk.getX()*16)+X, Y+j, (chunk.getZ()*16)+Z).setType(Material.OBSIDIAN);
							}
						}
					}
					break;
				}
				// The tree type can be changed if you want.
			}
		}
	}

} 
