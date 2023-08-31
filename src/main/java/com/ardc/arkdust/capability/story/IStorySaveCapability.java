package com.ardc.arkdust.capability.story;

import com.ardc.arkdust.advanced_obj.RangeNoRepIntList;
import com.ardc.arkdust.playmethod.story.Story;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;

public interface IStorySaveCapability extends INBTSerializable<CompoundNBT> {
    Map<Story, RangeNoRepIntList> getStoryProcess();

    boolean contains(Story story,int storyIndex);

    boolean add(Story storyIns,int storyIndex);

    void finishStory(Story story);
}