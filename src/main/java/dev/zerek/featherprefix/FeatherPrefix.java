package dev.zerek.featherprefix;

import dev.zerek.featherprefix.commands.PrefixCommand;
import dev.zerek.featherprefix.listeners.PlayerJoinListener;
import dev.zerek.featherprefix.managers.PrefixManager;
import dev.zerek.featherprefix.tasks.CheckTimeStatTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public final class FeatherPrefix extends JavaPlugin {

    private PrefixManager prefixManager;
    private final HashMap<Integer, HashMap<String, List<String>>> configMap = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        prefixManager = new PrefixManager(this);
        getConfig().getKeys(false).forEach(m -> {
            HashMap<String, List<String>> actions = new HashMap<>();
            actions.put("console-commands",getConfig().getStringList(m + ".console-commands"));
            actions.put("broadcasts",getConfig().getStringList(m + ".broadcasts"));
            configMap.put(Integer.valueOf(m),actions);
        });
        this.getCommand("prefix").setExecutor(new PrefixCommand(this));
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this),this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new CheckTimeStatTask(this), 0L, 1000L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public PrefixManager getPrefixManager() {
        return prefixManager;
    }

    public HashMap<Integer, HashMap<String, List<String>>> getConfigMap() {
        return configMap;
    }
}
