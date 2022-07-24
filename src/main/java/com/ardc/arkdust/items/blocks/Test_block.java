package com.ardc.arkdust.items.blocks;

import com.ardc.arkdust.CodeMigration.Material;
import com.ardc.arkdust.CodeMigration.pre.PreBlock;

public class Test_block extends PreBlock {
    public Test_block(){
        super(Properties.of(Material.TEST1).strength(2));
        //strength是强度，等价于非官方版中的hardnessAndResistance

    }


}
