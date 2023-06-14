package dev.zerek.featherprefix;

import dev.zerek.featherprefix.commands.PrefixCommand;
import dev.zerek.featherprefix.listeners.PlayerJoinListener;
import dev.zerek.featherprefix.listeners.PlayerQuitListener;
import dev.zerek.featherprefix.managers.PlayTimeManager;
import dev.zerek.featherprefix.tasks.CheckTimeStatTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public final class FeatherPrefix extends JavaPlugin {

    private PlayTimeManager playTimeManager;
    private final HashMap<Integer, HashMap<String, List<String>>> configMap = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        playTimeManager = new PlayTimeManager(this);
        getConfig().getKeys(false).forEach(m -> {
            HashMap<String, List<String>> actions = new HashMap<>();
            actions.put("console-commands",getConfig().getStringList(m + ".console-commands"));
            actions.put("broadcasts",getConfig().getStringList(m + ".broadcasts"));
            configMap.put(Integer.valueOf(m),actions);
        });
        this.getCommand("prefix").setExecutor(new PrefixCommand(this));
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this),this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this),this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new CheckTimeStatTask(this), 0L, 1200L);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public PlayTimeManager getPlayTimeManager() {
        return playTimeManager;
    }
    public HashMap<Integer, HashMap<String, List<String>>> getConfigMap() {
        return configMap;
    }
}
