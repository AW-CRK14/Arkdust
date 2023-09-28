package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.enums.BlueprintTypeEnum;
import com.ardc.arkdust.enums.BlueprintValueEnum;
import com.ardc.arkdust.helper.ArdRegHelper;
import com.ardc.arkdust.playmethod.blueprint.BlueprintTarget;
import com.ardc.arkdust.playmethod.blueprint.BlueprintTargetHandler;
import net.minecraft.resources.ResourceLocation;

public class BlueprintTargetRegistry {

    public static final ArdRegHelper.RegistryContainer<BlueprintTarget> s = new ArdRegHelper.RegistryContainer<>(BlueprintTargetHandler.map,(o)->o.regId);

    public static final BlueprintTarget DEFAULT = s.register(BlueprintTargetHandler.createInfo(new ResourceLocation(Utils.MOD_ID,"test/object"), BlueprintTypeEnum.OBJ, BlueprintValueEnum.COMMON,1));




}
