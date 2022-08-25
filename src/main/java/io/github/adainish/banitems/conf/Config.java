package io.github.adainish.banitems.conf;

import info.pixelmon.repack.org.spongepowered.serialize.SerializationException;
import io.github.adainish.banitems.BanItems;

import java.util.Arrays;

public class Config extends Configurable{
    private static Config config;

    public static Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public void setup() {
        super.setup();
    }

    public void load() {
        super.load();
    }
    public void save() {super.save();}
    @Override
    public void populate() {
        try {
            this.get().node("IllegalItems")
                    .set(Arrays.asList("pixelmon:bronze_cap", "minecraft:beacon"))
                    .comment("List of item strings that are deemed illegal and to be deleted");
        } catch (SerializationException e) {
            BanItems.log.error(e);
        }
    }


    @Override
    public String getConfigName() {
        return "config.yaml";
    }
}
