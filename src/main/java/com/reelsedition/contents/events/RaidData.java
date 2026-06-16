package com.reelsedition.contents.events;

import com.reelsedition.contents.registers.entity.Droid;
import com.reelsedition.contents.registers.entity.YN;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;

import java.util.ArrayList;
import java.util.List;

public class RaidData {

    private final BossInfoServer bossBar;
    private final List<YN> raiders = new ArrayList<>();

    private int initialSize = 0;

    public RaidData() {
        bossBar = new BossInfoServer(
                new TextComponentString("Peaceful Protest"),
                BossInfo.Color.RED,
                BossInfo.Overlay.PROGRESS
        );

        bossBar.setVisible(true);
        bossBar.setPercent(1.0F);
    }

    public void addRaider(YN YN) {
        raiders.add(YN);
    }

    public void addPlayer(EntityPlayerMP player) {
        bossBar.addPlayer(player);
    }

    public void removePlayer(EntityPlayerMP player) {
        bossBar.removePlayer(player);
    }

    public void setInitialSize(int size) {
        this.initialSize = size;
    }

    public List<YN> getRaiders() {
        return raiders;
    }

    public void update() {

        raiders.removeIf(e -> e.isDead);

        if (initialSize <= 0) {
            bossBar.setPercent(0.0F);
            return;
        }

        float progress = (float) raiders.size() / (float) initialSize;
        bossBar.setPercent(Math.max(0.0F, progress));
    }

    public boolean isFinished() {
        return raiders.isEmpty();
    }

    public void end() {
        bossBar.setVisible(false);

        for (EntityPlayerMP player : new ArrayList<>(bossBar.getPlayers())) {
            bossBar.removePlayer(player);
        }

        raiders.clear();
    }
}