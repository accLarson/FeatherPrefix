package dev.zerek.featherprefix.managers;

import dev.zerek.featherprefix.FeatherPrefix;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayTimeManager {
    private final HashMap<Player,Integer> playTimeMap = new HashMap<>();

    private final FeatherPrefix plugin;
    public PlayTimeManager(FeatherPrefix plugin) {
        this.plugin = plugin;
    }


    public void addPlayer(Player p) {
        this.playTimeMap.put(p, p.getStatistic(Statistic.PLAY_ONE_MINUTE)/20/60);
    }

    public void removePlayer(Player p) {
        this.playTimeMap.remove(p);
    }

    public HashMap<Player, Integer> getPlayTimeMap() {
        return playTimeMap;
    }
}
