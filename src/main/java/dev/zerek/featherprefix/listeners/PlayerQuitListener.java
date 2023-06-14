package dev.zerek.featherprefix.listeners;

import dev.zerek.featherprefix.FeatherPrefix;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.List;

public class PlayerQuitListener implements Listener {

    private final FeatherPrefix plugin;

    public PlayerQuitListener(FeatherPrefix plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        if (p.hasPermission("feather.prefix.track")) {
            Integer quitMinutes = event.getPlayer().getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60;
            if (plugin.getConfigMap().containsKey(quitMinutes)) {
                if (!quitMinutes.equals(plugin.getPlayTimeManager().getPlayTimeMap().get(p))) {
                    plugin.getConfigMap().get(quitMinutes).get("console-commands").forEach(line -> plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), line.replace("<USERNAME>", p.getName())));
                    plugin.getConfigMap().get(quitMinutes).get("broadcasts").forEach(line -> plugin.getServer().broadcast(MiniMessage.miniMessage().deserialize(line, Placeholder.unparsed("USERNAME", p.getName()))));
                }
            }
            plugin.getPlayTimeManager().removePlayer(p);
        }
    }
}
