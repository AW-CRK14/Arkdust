package com.ardc.arkdust.advanced_obj;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class AdvancedContainerMenu extends AbstractContainerMenu {
    public Player player;
    public BlockPos pos;
    public BlockEntity tileEntity;

    public AdvancedContainerMenu(MenuType<?> menuType, int synId, Player player, BlockPos pos){
        super(menuType,synId);
        this.player = player;
        this.pos = pos;
        this.tileEntity = player.level().getBlockEntity(pos);
    }
}
