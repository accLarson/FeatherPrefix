package dev.zerek.featherprefix.managers;

import dev.zerek.featherprefix.FeatherPrefix;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PrefixManager {

    private final FeatherPrefix plugin;
    private final List<String> prefixGroups;

    public PrefixManager(FeatherPrefix plugin) {
        this.plugin = plugin;
        this.prefixGroups = Arrays.asList("i", "ii", "iii", "iv", "v", "vi", "vii", "viii", "ix", "x");
    }

    public void ensureCorrectPrefix(Player player, int minutes) {
        // Get current prefix
        String currentPrefix = prefixGroups.stream()
                .filter(group -> player.hasPermission("group." + group))
                .findFirst()
                .orElse(null);

        // Get highest threshold reached
        Integer threshold = plugin.getConfigMap().keySet().stream()
                .filter(t -> minutes >= t)
                .max(Integer::compareTo)
                .orElse(null);

        // Only proceed if we have a valid threshold
        if (threshold != null) {
            // Calculate expected prefix
            int prefixIndex = (int) plugin.getConfigMap().keySet().stream()
                    .filter(t -> t <= threshold)
                    .count() - 1;
            String expectedPrefix = prefixGroups.get(prefixIndex);

            // Only update if prefixes don't match
            if (!Objects.equals(currentPrefix, expectedPrefix)) {
                plugin.getLogger().info("Updating prefix for " + player.getName() + " from " + currentPrefix + " to " + expectedPrefix);

                plugin.getConfigMap().get(threshold).get("console-commands").forEach(cmd ->
                        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),
                                cmd.replace("<username>", player.getName()))
                );

                plugin.getConfigMap().get(threshold).get("broadcasts").forEach(msg ->
                        plugin.getServer().broadcast(MiniMessage.miniMessage().deserialize(msg,
                                Placeholder.unparsed("username", player.getName())))
                );
            }
        }
    }}
