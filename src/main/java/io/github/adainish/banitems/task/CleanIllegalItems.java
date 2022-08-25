package io.github.adainish.banitems.task;

import io.github.adainish.banitems.BanItems;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class CleanIllegalItems implements Runnable{
    @Override
    public void run() {
        for (ServerPlayerEntity pl: ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            BanItems.illegalItemWrapper.deleteIllegalItems(pl);
        }
    }
}
