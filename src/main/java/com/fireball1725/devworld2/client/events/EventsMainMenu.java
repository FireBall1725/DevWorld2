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

package com.fireball1725.devworld2.client.events;

import com.fireball1725.devworld2.util.DevWorldUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventsMainMenu {
  private Button buttonCreate;
  private Button buttonLoad;
  private Button buttonDelete;
  private int keyShiftCount = 0;

  @SubscribeEvent
  public void onScreenDraw(GuiScreenEvent.DrawScreenEvent.Post event) {
    if (event.getGui() instanceof MainMenuScreen) {
      int textY = event.getGui().height / 4 + 38;
      int textX = event.getGui().width / 2 + 146;
      event.getGui().drawCenteredString(Minecraft.getInstance().fontRenderer, "DevWorld 2", textX, textY, 0xFFFFFF);

      if (buttonDelete.isHovered() && !buttonDelete.active) {
        event.getGui().renderTooltip("Press [Left Shift] 2 times to enable delete", event.getMouseX(), event.getMouseY());
      }

      if (!DevWorldUtils.saveExist()) {
        buttonCreate.visible = true;
        buttonLoad.visible = false;
        buttonDelete.visible = false;
      } else {
        buttonCreate.visible = false;
        buttonLoad.visible = true;
        buttonDelete.visible = true;
      }
    }
  }

  @SubscribeEvent
  public void onKeyPress(GuiScreenEvent.KeyboardKeyPressedEvent.Post event) {
    if (event.getGui() instanceof MainMenuScreen) {
      if (event.getKeyCode() == 340)
        keyShiftCount ++;

      if (keyShiftCount >= 2)
        buttonDelete.active = true;
    }
  }

  @SubscribeEvent
  public void onScreenInitPost(GuiScreenEvent.InitGuiEvent.Post event) {
    if (event.getGui() instanceof MainMenuScreen) {
      int buttonY = event.getGui().height / 4 + 48;
      int buttonX = event.getGui().width / 2 + 104;

      buttonCreate = new Button(buttonX, buttonY, 84, 20, "New DevWorld", (p_214318_1_) -> {
        DevWorldUtils.createDevWorld();
      });
      buttonLoad = new Button(buttonX, buttonY, 40, 20, "Load", (p_214318_1_) -> {
        DevWorldUtils.loadDevWorld();
      });
      buttonX += 44;
      buttonDelete = new Button(buttonX, buttonY, 40, 20, "Delete", (p_214318_1_) -> {
        DevWorldUtils.deleteDevWorld();
        keyShiftCount = 0;
      });

      buttonCreate.visible = false;
      buttonLoad.visible = false;
      buttonDelete.visible = false;
      buttonDelete.active = false;

      keyShiftCount = 0;

      event.addWidget(buttonCreate);
      event.addWidget(buttonLoad);
      event.addWidget(buttonDelete);
    }
  }
}
