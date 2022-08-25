package io.github.adainish.banitems;

import ca.landonjw.gooeylibs2.implementation.tasks.Task;
import io.github.adainish.banitems.cmd.Command;
import io.github.adainish.banitems.conf.Config;
import io.github.adainish.banitems.listeners.ItemInteractListener;
import io.github.adainish.banitems.task.CleanIllegalItems;
import io.github.adainish.banitems.wrapper.IllegalItemWrapper;
import io.github.adainish.banitems.wrapper.PermissionWrapper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("banitems")
public class BanItems {
    public static BanItems instance;
    public static final String MOD_NAME = "BanItems";
    public static final String VERSION = "1.0.1-SNAPSHOT";
    public static final String AUTHORS = "Winglet";
    public static final String YEAR = "2022";
    public static Logger log = LogManager.getLogger(MOD_NAME);
    public static File configDir;
    public static PermissionWrapper permissionWrapper;
    public static IllegalItemWrapper illegalItemWrapper;
    public static File getConfigDir() {
        return configDir;
    }

    public static void setConfigDir(File configDir) {
        BanItems.configDir = configDir;
    }


    public BanItems() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
        instance = this;
    }

    private void setup(final FMLCommonSetupEvent event) {
        log.log(Level.WARN, "Booting up %n by %authors %v %y"
                .replace("%n", MOD_NAME)
                .replace("%authors", AUTHORS)
                .replace("%v", VERSION)
                .replace("%y", YEAR)
        );
    }

    @SubscribeEvent
    public void onCommandRegistry(RegisterCommandsEvent event) {
        permissionWrapper = new PermissionWrapper();
        event.getDispatcher().register(Command.getCommand());
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        init();
        MinecraftForge.EVENT_BUS.register(new ItemInteractListener());
        new Task.TaskBuilder().interval(20 * 60).execute(new CleanIllegalItems()).infinite().build();
    }

    public void initDirs() {
        setConfigDir(new File(FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath()).toString()));
        getConfigDir().mkdir();
    }

    public void initConfigs() {
        Config.getConfig().setup();
    }

    public void loadConfigs() {
        Config.getConfig().load();
    }

    public void init() {
        initDirs();
        initConfigs();
        loadConfigs();
        illegalItemWrapper = new IllegalItemWrapper();
    }

}
