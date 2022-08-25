package io.github.adainish.banitems.wrapper;

import info.pixelmon.repack.org.spongepowered.CommentedConfigurationNode;
import info.pixelmon.repack.org.spongepowered.serialize.SerializationException;
import io.github.adainish.banitems.conf.Config;
import io.leangen.geantyref.TypeToken;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class IllegalItemWrapper {
    public List <String> illegalItemIDList = new ArrayList <>();

    public IllegalItemWrapper() {
        init();
    }

    public void init() {
        illegalItemIDList.clear();
        try {
            illegalItemIDList.addAll(Objects.requireNonNull(Config.getConfig().get().node("IllegalItems").getList(TypeToken.get(String.class))));
        } catch (SerializationException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public Item getItemFromString(String s) {
        ResourceLocation location = new ResourceLocation(s);
        return ForgeRegistries.ITEMS.getValue(location);
    }

    public boolean isIllegalItem(ItemStack stack) {
        for (String s:illegalItemIDList) {
            return stack.getItem().getRegistryName().toString().equalsIgnoreCase(s);
        }
        return false;
    }

    public void deleteIllegalItems(ServerPlayerEntity playerEntity) {
        for (int i = 0; i < playerEntity.inventory.getSizeInventory(); i++) {
            ItemStack stack = playerEntity.inventory.getStackInSlot(i);
            if (stack.isEmpty())
                continue;
            if (isIllegalItem(stack))
                playerEntity.inventory.deleteStack(stack);
        }
    }

    public boolean hasIllegalItem(ServerPlayerEntity playerEntity) {
        for (int i = 0; i < playerEntity.inventory.getSizeInventory(); i++) {
            ItemStack stack = playerEntity.inventory.getStackInSlot(i);
            for (String s:illegalItemIDList) {
                if (stack.getItem().getRegistryName().toString().equalsIgnoreCase(s))
                    return true;
            }
        }
        return false;
    }
}
