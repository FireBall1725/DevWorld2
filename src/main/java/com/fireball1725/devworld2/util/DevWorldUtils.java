package com.fireball1725.devworld2.util;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.JsonOps;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.storage.SaveFormat;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;

import java.io.File;

public class DevWorldUtils {
  private final static String worldName = "DevWorld";

  // Create the world
  public static void createDevWorld() {
    Minecraft.getInstance().displayGuiScreen(null);

    WorldType.WORLD_TYPES[0].onGUICreateWorldPress();

    WorldSettings worldSettings = new WorldSettings(0, GameType.CREATIVE, false, false, WorldType.FLAT);

    FlatGenerationSettings flatGenerationSettings = FlatGenerationSettings.createFlatGeneratorFromString("minecraft:bedrock,3*minecraft:stone,52*minecraft:sandstone;minecraft:desert;");
    CompoundNBT worldGeneratorNBT = (CompoundNBT)flatGenerationSettings.func_210834_a(NBTDynamicOps.INSTANCE).getValue();

    worldSettings.setGeneratorOptions(Dynamic.convert(NBTDynamicOps.INSTANCE, JsonOps.INSTANCE, worldGeneratorNBT));

    File gameDir = Minecraft.getInstance().gameDir;
    SaveFormat saveFormat = new SaveFormat(gameDir.toPath().resolve("saves"), gameDir.toPath().resolve("backups"), null);
    SaveHandler saveHandler = saveFormat.getSaveLoader(worldName, null);

    CompoundNBT worldData = new CompoundNBT();
    // Spawn Location
    worldData.putInt("SpawnX", 0);
    worldData.putInt("SpawnY", 80);
    worldData.putInt("SpawnZ", 0);

    // World Generator
    worldData.putString("generatorName", "flat");
    worldData.put("generatorOptions", worldGeneratorNBT);

    // Cheat Mode
    worldData.putInt("GameType", GameType.CREATIVE.getID());
    worldData.putBoolean("allowCommands", true);

    // Make the generator thing it has already been generated
    worldData.putBoolean("initialized", true);

    // Set time
    worldData.putLong("Time", 6000);
    worldData.putLong("DayTime", 6000);

    // Set GameRules
    CompoundNBT worldRules = new CompoundNBT();
    worldRules.putString("doWeatherCycle", "false");
    worldRules.putString("doDaylightCycle", "false");
    worldData.put("GameRules", worldRules);

    WorldInfo worldInfo = new WorldInfo(worldData, null, 14, null);

    saveHandler.saveWorldInfo(worldInfo);

    Minecraft.getInstance().launchIntegratedServer(worldName, worldName, worldSettings);
  }

  // Load the world
  public static boolean loadDevWorld() {
    if (Minecraft.getInstance().getSaveLoader().canLoadWorld(worldName)) {
      Minecraft.getInstance().launchIntegratedServer(worldName, worldName, null);
      return true;
    }
    return false;
  }

  // Delete the world
  public static void deleteDevWorld() {
    SaveFormat saveFormat = Minecraft.getInstance().getSaveLoader();
    saveFormat.deleteWorldDirectory(worldName);
    Minecraft.getInstance().displayGuiScreen(null);
  }

  // Check to see if the Save Exists
  public static boolean saveExist() {
    return Minecraft.getInstance().getSaveLoader().getWorldInfo(worldName) != null;
  }
}
