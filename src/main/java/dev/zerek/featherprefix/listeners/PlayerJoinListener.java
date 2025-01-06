package dev.zerek.featherprefix.listeners;

import dev.zerek.featherprefix.FeatherPrefix;
import org.bukkit.Statistic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final FeatherPrefix plugin;

    public PlayerJoinListener(FeatherPrefix plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("feather.prefix.track")) {
            int minutes = event.getPlayer().getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60;
            plugin.getPrefixManager().ensureCorrectPrefix(event.getPlayer(), minutes);
        }
    }
}
