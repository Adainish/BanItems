package io.github.adainish.banitems.listeners;

import io.github.adainish.banitems.BanItems;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ItemInteractListener {
    @SubscribeEvent
    public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.isCanceled())
            return;

        if (event.getItemStack().isEmpty())
            return;

        if (BanItems.illegalItemWrapper.isIllegalItem(event.getItemStack())) {
            event.getPlayer().inventory.deleteStack(event.getItemStack());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteractSpecific event) {
        if (event.isCanceled())
            return;

        if (event.getItemStack().isEmpty())
            return;

        if (BanItems.illegalItemWrapper.isIllegalItem(event.getItemStack())) {
            event.getPlayer().inventory.deleteStack(event.getItemStack());
            event.setCanceled(true);
        }
    }
}
