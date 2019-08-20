package com.fireball1725.devworld2.proxy;

import com.fireball1725.devworld2.client.events.EventsMainMenu;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
  @Override
  public void registerEventHandler() {
    MinecraftForge.EVENT_BUS.register(new EventsMainMenu());
  }
}
