package com.reelsedition.contents.registers;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import static com.hbm.lib.HBMSoundHandler.register;
import static com.reelsedition.reelsedition.MODID;

/**
 * How to register a sound:
 * 1. Create the sound here, and register in init();
 * 2. add the sound to sounds.json.
 * IMPORTANT: suppose you create an entry like this:
 * "block.assembleroperate": {"category": "block", "sounds": [{"name": "hbm:block/assemblerOperate", "stream": false}]},
 * This works, but you must ensure that the .ogg file in src\main\resources\assets\hbm\sounds\block is in lowercase.
 * In other words, there must be an assembleroperate.ogg under that directory. assemblerOperate.ogg won't work!
 */
public class ReelsSoundHandler {
    public static final Object2ObjectLinkedOpenHashMap<ResourceLocation, SoundEvent> ALL_SOUNDS = new Object2ObjectLinkedOpenHashMap<>();


    public static void init() {

    }


    public static SoundEvent register(String name) {
        ResourceLocation loc = new ResourceLocation(MODID, name);
        SoundEvent existing = ALL_SOUNDS.get(loc);
        if (existing != null) return existing;
        SoundEvent e = new SoundEvent(loc);
        e.setRegistryName(loc);
        ALL_SOUNDS.put(loc, e);
        return e;
    }

    public static SoundEvent getOrCreate(ResourceLocation loc) {
        SoundEvent existing = ALL_SOUNDS.get(loc);
        if (existing != null) return existing;
        SoundEvent e = new SoundEvent(loc);
        e.setRegistryName(loc);
        ALL_SOUNDS.put(loc, e);
        return e;
    }
}

