package io.github.adainish.banitems.cmd;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.adainish.banitems.BanItems;
import io.github.adainish.banitems.util.PermissionUtil;
import io.github.adainish.banitems.wrapper.PermissionWrapper;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class Command {
    public static LiteralArgumentBuilder <CommandSource> getCommand() {
        return Commands.literal("banitemsforge")
                .requires(cs -> PermissionUtil.checkPermAsPlayer(cs, PermissionWrapper.adminPermission))
                .executes(context -> {
                    BanItems.instance.init();
                    context.getSource().sendFeedback(new StringTextComponent(TextFormatting.YELLOW + "Reloaded ban items data, check the console for any errors"), true);
                    return 1;
                });
    }
}
