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

    // Debug logging
    plugin.getLogger().info("Player: " + player.getName());
    plugin.getLogger().info("Minutes: " + minutes);
    plugin.getLogger().info("Current prefix: " + currentPrefix);

    // Only proceed if we have a valid threshold
    if (threshold != null) {
        // Calculate expected prefix
        int prefixIndex = (int) plugin.getConfigMap().keySet().stream()
            .filter(t -> t <= threshold)
            .count() - 1;
        String expectedPrefix = prefixGroups.get(prefixIndex);

        plugin.getLogger().info("Expected prefix: " + expectedPrefix);
        plugin.getLogger().info("Threshold: " + threshold);

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
}
