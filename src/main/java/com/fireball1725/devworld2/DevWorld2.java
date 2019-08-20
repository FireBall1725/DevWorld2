package com.fireball1725.devworld2;

import com.fireball1725.devworld2.proxy.ClientProxy;
import com.fireball1725.devworld2.proxy.IProxy;
import com.fireball1725.devworld2.proxy.ServerProxy;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod("devworld2")
public class DevWorld2 {
  public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

  public DevWorld2() {
    // Register Events
    proxy.registerEventHandler();
  }
}
