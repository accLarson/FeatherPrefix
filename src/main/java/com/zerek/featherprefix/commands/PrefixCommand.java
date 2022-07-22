package com.zerek.featherprefix.commands;

import com.zerek.featherprefix.FeatherPrefix;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;

public class PrefixCommand implements CommandExecutor {

    private final FeatherPrefix plugin;

    public PrefixCommand(FeatherPrefix plugin) {
        this.plugin = plugin;
    }

    private boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            if (args.length == 0){
                displayChart(sender);
                displayHours((Player) sender, (Player) sender);
            } else {
                Player player = plugin.getServer().getPlayer(args[0]);
                if ( !( player == null ) && player.isOnline() && !isVanished(player) && !player.hasPermission("group.administrator")){
                    displayChart(sender);
                    displayHours((Player) sender, player);
                } else sender.sendMessage(ChatColor.of("#E4453A") + "Player not found");
            }
        }
        else displayChart(sender);
        return true;
    }

    public void displayChart(CommandSender s){
        TextComponent more = (TextComponent) MiniMessage.miniMessage().deserialize("<#f2727e>MORE");
        more = more.hoverEvent(HoverEvent.showText(MiniMessage.miniMessage().deserialize("<#656b96>Prefix<gray>: <dark_red>X<dark_gray>I     <dark_red>X<dark_gray>II     <dark_red>X<dark_gray>III   <dark_red>X<dark_gray>IV    <dark_red>X<dark_gray>V     <dark_red>X<dark_gray>VI    <dark_red>X<dark_gray>VII   <dark_red>X<dark_gray>VIII  <dark_red>X<dark_gray>IX    <dark_red>XX\n<#656b96>Hours<gray>: <#949bd1>2001  2008  2020  2045  2090  2170  2320  2580  3090  4000")));
        s.sendMessage(MiniMessage.miniMessage().deserialize("<#656b96>Prefix<gray>: <dark_gray>I  II  III  IV  V    VI    VII   VIII  IX      <dark_red>X        ").append(more));
        s.sendMessage(MiniMessage.miniMessage().deserialize("<#656b96>Hours<gray>: <#949bd1>1  8  20  45  90  170  320  580  1090   2000"));
    }

    public void displayHours(Player requester, Player p){
        int hours = p.getStatistic(Statistic.PLAY_ONE_MINUTE)/20/60/60;
        requester.sendMessage(ChatColor.of("#949bd1") + p.getName() + ChatColor.of("#656b96") + " Hours: " + ChatColor.of("#949bd1") + hours);
    }
}