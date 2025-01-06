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

public class CheckTimeStatTask implements Runnable {
    private final FeatherPrefix plugin;

    public CheckTimeStatTask(FeatherPrefix plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getServer().getOnlinePlayers().stream()
            .filter(p -> p.hasPermission("feather.prefix.track"))
            .forEach(p -> {
                int minutes = p.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60;
                plugin.getPrefixManager().ensureCorrectPrefix(p, minutes);
            });
    }
}
