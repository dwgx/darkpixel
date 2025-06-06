package com.darkpixel.manager;

import com.darkpixel.Global;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class BanManager {
    private final Global context;

    public BanManager(Global context) {
        this.context = context;
    }

    public void banPlayer(String playerName, long banUntil, String reason, String clientIdentifier) {
        UUID uuid = Bukkit.getOfflinePlayer(playerName).getUniqueId();
        Player player = Bukkit.getPlayer(uuid);
        String ip = player != null ? player.getAddress().getAddress().getHostAddress() : null;
        String kickMessage = "你已被封禁" + (banUntil == -1 ? "永久" : "，剩余 " + ((banUntil - System.currentTimeMillis()) / 60000) + " 分钟") + "\n原因：" + reason;

        if (player != null && player.isOnline()) {
            Bukkit.getScheduler().runTask(context.getPlugin(), () -> player.kickPlayer(kickMessage));
        }

        Date expiry = banUntil == -1 ? null : new Date(banUntil);
        Bukkit.getBanList(BanList.Type.NAME).addBan(playerName, reason, expiry, "Server");
        if (ip != null) {
            Bukkit.getBanList(BanList.Type.IP).addBan(ip, reason, expiry, "Server");
        }

        context.getPlayerData().setBanStatus(playerName, banUntil, reason);

        Global.executor.submit(() -> {
            saveBanToDatabase(uuid, playerName, ip, clientIdentifier, banUntil, reason);
            updateGroupStatus(uuid, playerName, banUntil);
        });
    }

    public void banPlayer(String playerName, long banUntil, String reason) {
        banPlayer(playerName, banUntil, reason, null);
    }

    public void unbanPlayer(String playerName) {
        UUID uuid = Bukkit.getOfflinePlayer(playerName).getUniqueId();
        Player player = Bukkit.getPlayer(uuid);
        String ip = player != null ? player.getAddress().getAddress().getHostAddress() : null;

        Bukkit.getBanList(BanList.Type.NAME).pardon(playerName);
        if (ip != null) {
            Bukkit.getBanList(BanList.Type.IP).pardon(ip);
        }
        context.getPlayerData().setBanStatus(playerName, 0, null);

        Global.executor.submit(() -> {
            saveBanToDatabase(uuid, playerName, ip, null, 0, null);
            updateGroupStatus(uuid, playerName, 0);
        });
    }

    private void saveBanToDatabase(UUID uuid, String playerName, String ip, String clientIdentifier, long banUntil, String reason) {
        try (Connection conn = context.getRankManager().getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO bans (uuid, player_name, ip, client_identifier, ban_until, ban_reason) " +
                             "VALUES (?, ?, ?, ?, ?, ?) " +
                             "ON DUPLICATE KEY UPDATE ban_until = ?, ban_reason = ?, ip = ?, client_identifier = ?")) {
            conn.setAutoCommit(false);
            ps.setString(1, uuid.toString());
            ps.setString(2, playerName);
            ps.setString(3, ip);
            ps.setString(4, clientIdentifier);
            ps.setLong(5, banUntil);
            ps.setString(6, reason);
            ps.setLong(7, banUntil);
            ps.setString(8, reason);
            ps.setString(9, ip);
            ps.setString(10, clientIdentifier);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to save ban data for " + playerName + ": " + e.getMessage());
        }
    }

    private void updateGroupStatus(UUID uuid, String playerName, long banUntil) {
        try (Connection conn = context.getRankManager().getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO player_groups (uuid, player_name, group_name) " +
                             "VALUES (?, ?, ?) " +
                             "ON DUPLICATE KEY UPDATE group_name = ?")) {
            conn.setAutoCommit(false);
            String group = banUntil == 0 ? "member" : "banned";
            ps.setString(1, uuid.toString());
            ps.setString(2, playerName);
            ps.setString(3, group);
            ps.setString(4, group);
            ps.executeUpdate();
            conn.commit();

            if (context.isRankServerRunning()) {
                context.getRankManager().setGroupByUUID(uuid, group);
            } else {
                context.getRankServerClient().setGroup(uuid.toString(), group);
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to update group status for " + playerName + ": " + e.getMessage());
        }
    }
}