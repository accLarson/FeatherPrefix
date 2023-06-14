package dev.zerek.featherprefix.tasks;

import dev.zerek.featherprefix.FeatherPrefix;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

import static net.kyori.adventure.text.Component.text;

public class CheckTimeStatTask implements Runnable{
    private final FeatherPrefix plugin;

    public CheckTimeStatTask(FeatherPrefix plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (p.hasPermission("feather.prefix.track")){
                Integer minutes = p.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60;
                if (plugin.getConfigMap().containsKey(minutes) && plugin.getPlayTimeManager().getPlayTimeMap().containsKey(p)){
                    if (!minutes.equals(plugin.getPlayTimeManager().getPlayTimeMap().get(p))) {
                        plugin.getConfigMap().get(minutes).get("console-commands").forEach(line -> plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), line.replace("<username>", p.getName())));
                        plugin.getConfigMap().get(minutes).get("broadcasts").forEach(line -> plugin.getServer().broadcast(MiniMessage.miniMessage().deserialize(line, Placeholder.unparsed("username", p.getName()))));
                    }
                }
            }
        }
    }
}
