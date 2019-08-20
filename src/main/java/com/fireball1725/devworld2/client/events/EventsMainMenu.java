package com.fireball1725.devworld2.client.events;

import com.fireball1725.devworld2.util.DevWorldUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventsMainMenu {
  @SubscribeEvent
  public void onScreenDraw(GuiScreenEvent.DrawScreenEvent.Post event) {
    if (event.getGui() instanceof MainMenuScreen) {
      int textY = event.getGui().height / 4 + 38;
      int textX = event.getGui().width / 2 + 146;
      event.getGui().drawCenteredString(Minecraft.getInstance().fontRenderer, "DevWorld 2", textX, textY, 0xFFFFFF);
    }
  }

  @SubscribeEvent
  public void onScreenInitPost(GuiScreenEvent.InitGuiEvent.Post event) {
    if (event.getGui() instanceof MainMenuScreen) {
      int buttonY = event.getGui().height / 4 + 48;
      int buttonX = event.getGui().width / 2 + 104;

      if (!DevWorldUtils.saveExist()) {
        // New World Button
        event.addWidget(new Button(buttonX, buttonY, 84, 20, "New DevWorld", (p_214318_1_) -> {
          DevWorldUtils.createDevWorld();
        }));
      } else {
        // Load World Button
        event.addWidget(new Button(buttonX, buttonY, 40, 20, "Load", (p_214318_1_) -> {
          DevWorldUtils.loadDevWorld();
        }));
        buttonX += 44;

        // Delete World Button
        event.addWidget(new Button(buttonX, buttonY, 40, 20, "Delete", (p_214318_1_) -> {
          DevWorldUtils.deleteDevWorld();
        }));
      }
    }
  }
}
