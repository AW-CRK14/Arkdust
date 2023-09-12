package com.ardc.arkdust.capability.story;

import com.ardc.arkdust.advanced_obj.RangeNoRepIntList;
import com.ardc.arkdust.playmethod.story.Story;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.eventbus.api.Event;

import java.util.Map;

public interface IStorySaveCapability extends INBTSerializable<CompoundNBT> {
    Map<Story, RangeNoRepIntList> getStoryProcess();

    boolean contains(Story story,int storyIndex);

    boolean add(Story storyIns,int storyIndex,PlayerEntity player);

    void finishStory(Story story);

    void buildListener();
    void pushEvent(Event event, PlayerEntity player);

    boolean isStoryFinished(Story story);
}