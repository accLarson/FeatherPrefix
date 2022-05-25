package com.zerek.featherprefix.listeners;

import com.zerek.featherprefix.FeatherPrefix;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final FeatherPrefix plugin;
    public PlayerJoinListener(FeatherPrefix plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if (event.getPlayer().hasPermission("feather.prefix.track")){
            plugin.getPlayTimeManager().addPlayer(event.getPlayer());
        }
    }
}
