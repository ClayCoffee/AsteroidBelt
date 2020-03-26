package com.narcissu14.spacetech.generator.populators;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Narcissu14
 */
public class PlanetPopulator extends BlockPopulator{
    /**小型星球几率*/
    private static final int CRATER_CHANCE = 10;
    private static final int MIN_CRATER_SIZE = 3;
    private static final int SMALL_CRATER_SIZE = 20;
    private static final int BIG_CRATER_SIZE = 100;
    /**大型星球几率*/
    private static final int BIG_CRATER_CHANCE = 2;
    private static final List<Material> PLANET_BLOCKS;

    static {
        PLANET_BLOCKS = new ArrayList<Material>();
        PLANET_BLOCKS.add(Material.COBBLESTONE);
        PLANET_BLOCKS.add(Material.COBBLESTONE);
        PLANET_BLOCKS.add(Material.COBBLESTONE);
        PLANET_BLOCKS.add(Material.COBBLESTONE);
        PLANET_BLOCKS.add(Material.OBSIDIAN);
        PLANET_BLOCKS.add(Material.CYAN_TERRACOTTA);
        PLANET_BLOCKS.add(Material.CYAN_TERRACOTTA);
    }

    @Override
    public void populate(World world, Random random, Chunk source) {
        if (random.nextInt(100) <= CRATER_CHANCE) {
            int radius;
            if (random.nextInt(100) <= BIG_CRATER_CHANCE) {
                radius = random.nextInt(BIG_CRATER_SIZE - MIN_CRATER_SIZE + 1) + MIN_CRATER_SIZE;
            } else {
                radius = random.nextInt(SMALL_CRATER_SIZE - MIN_CRATER_SIZE + 1) + MIN_CRATER_SIZE;
            }

            int centerX = (source.getX() << 4) + random.nextInt(16);
            int centerZ = (source.getZ() << 4) + random.nextInt(16);
            //避免最高和最低y值导致星球生成不完整
            int centerY = random.nextInt(world.getMaxHeight() - (radius << 1)) + radius;
            Vector center = new BlockVector(centerX, centerY, centerZ);

            boolean enoughSize = radius > 12;
            int coreRadius = radius > 20 ? ((radius - 10) >> 3) + 5 : 5;

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Vector position = center.clone().add(new Vector(x, y, z));
                        double distance = radius + 0.5;

                        //生成星核
                        if (enoughSize) {
                            if (center.distance(position) <= coreRadius + 0.5) {
                                world.getBlockAt(position.toLocation(world)).setType(Material.BEDROCK);
                                continue;
                            }
                        }

                        //生成星球外层
                        if (center.distance(position) <= distance) {
                            Material data = PLANET_BLOCKS.get(random.nextInt(PLANET_BLOCKS.size()));
                            world.getBlockAt(position.toLocation(world)).setType(data, false);
                        }
                    }
                }
            }

            if (enoughSize) {
                //星核高级矿物数量
                int coreOreAmount = (((int)(Math.pow(coreRadius, 3) - Math.pow(5, 3))) >> 5) + 4;
                for (int i = 0;i < coreOreAmount;i++) {
                    //随机星核内位置并生成高价值矿物
                    Vector v = ramdomOreLoc(center, coreRadius - 1, 0);
                    Block block = v.toLocation(world).getBlock();
                    block.setType(Material.GOLD_BLOCK, false);
                }
            }

            //星球普通矿物数量
            int normalOreAmount = ((int)(Math.pow(radius, 3) - Math.pow(coreRadius, 3))) >> 7;
            for (int i = 0;i < normalOreAmount;i++) {
                //随机在星球内且在星核外位置，并生成常规矿物
                Vector v = ramdomOreLoc(center, radius, coreRadius);
                Block block = v.toLocation(world).getBlock();
                block.setType(Material.IRON_BLOCK, false);
            }
        }

    }

    /**
     * 随机取球体或空心球体内的一个坐标位置(不包含空心部分)
     */
    private Vector ramdomOreLoc(Vector center, int radius, int minRandomRadius) {
        Random random = new Random();
        double randomRadius = random.nextInt(radius - minRandomRadius) + minRandomRadius;
        //倾斜角
        double tiltAngle = random.nextDouble() * 360;
        //方位角
        double azimuth = random.nextDouble() * 360;

        return center.clone().add(new Vector(Math.cos(tiltAngle) * randomRadius * Math.cos(azimuth),
                Math.sin(tiltAngle) * randomRadius,
                Math.cos(tiltAngle) * randomRadius * Math.sin(azimuth)));
    }
}
