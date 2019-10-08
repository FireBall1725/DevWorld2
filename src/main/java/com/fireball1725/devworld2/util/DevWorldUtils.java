/*
 * MIT License
 *
 * Copyright (c) 2019 Erin Reed / FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
    CompoundNBT worldGeneratorNBT = (CompoundNBT) flatGenerationSettings.func_210834_a(NBTDynamicOps.INSTANCE).getValue();

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
  }

  // Check to see if the Save Exists
  public static boolean saveExist() {
    return Minecraft.getInstance().getSaveLoader().getWorldInfo(worldName) != null;
  }
}
