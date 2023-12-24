package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.helper.ArdRegHelper;
import com.ardc.arkdust.playmethod.story.Story;
import com.ardc.arkdust.playmethod.story.StoryAchListener;
import com.ardc.arkdust.registry.item.Item$CommonWorld;
import net.minecraft.resources.ResourceLocation;

public class StoryRegistry {

    public static final ArdRegHelper.RegistryContainer<Story> s = new ArdRegHelper.RegistryContainer<>(Story.storyMap,(o)->o.name);

    //    public static final Story MOON_FALL = addToStoryMap(new ResourceLocation(Utils.MOD_ID,"moon_fall"),11, Story.StoryType.FIRST_PERSON,null,Story.defaultRenderPicBig,Story.defaultRenderPicSmall,null);
    public static final Story DEFAULT = s.register(new Story(new ResourceLocation(Utils.MOD_ID,"default"),1));
    public static final Story MOON_FALL = s.register(new Story(new ResourceLocation(Utils.MOD_ID,"moon_fall"),11, Story.StoryType.FIRST_PERSON,null,Story.defRPB,Story.defRPS,null,false,
            (map)->{
        map.put(2,StoryAchListener.Pre.itemPickup(Item$CommonWorld.pau_ingot.getId()));
        map.put(3,StoryAchListener.Pre.itemPickup(Item$CommonWorld.ender_energy_radar.getId()));
    }));




}
