package com.ardc.arkdust.worldgen.structure.structure.cworld;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.helper.PosHelper;
import com.ardc.arkdust.helper.StructureHelper;
import com.ardc.arkdust.resource.LootTable;
import com.ardc.arkdust.worldgen.structure.ExtraStructurePieceType;
import com.ardc.arkdust.worldgen.structure.ExtraStructureType;
import com.ardc.arkdust.worldgen.structure.preobj.CommonCWTemplatePiece;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PixArkLibrary extends Structure{

    public PixArkLibrary(Structure.StructureSettings p_i231997_1_) {
        super(p_i231997_1_);
    }


    public static final Codec<PixArkLibrary> CODEC = simpleCodec(PixArkLibrary::new);

    public static final List<ResourceLocation> list = Arrays.asList(new ResourceLocation(Utils.MOD_ID,"cworld/boat/boat_1"),new ResourceLocation(Utils.MOD_ID,"cworld/boat/boat_2"),new ResourceLocation(Utils.MOD_ID,"cworld/boat/boat_3"),new ResourceLocation(Utils.MOD_ID,"cworld/boat/boat_4"));

    @Override
    public GenerationStep.Decoration step() {//与生成的位置有关，大概。
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public StructureType<?> type() {
        return ExtraStructureType.CW$PIXARK_LIBRARY;
    }

    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {

        BlockPos center = PosHelper.randomSkew(context,9);
        if(StructureHelper.isEachPlaceAvailable(context.chunkGenerator(), Heightmap.Types.WORLD_SURFACE_WG,5, PosHelper.getCenterAndSquareVertexPos(center,24,false,true),context.heightAccessor(),context.randomState()))
            return Optional.of(new GenerationStub(center.below(),(builder)->builder.addPiece(new Tem(context.structureTemplateManager(),context.random(),center))));
        return Optional.empty();

//        TemplateStructurePiece piece =
    }

    public static class Tem extends CommonCWTemplatePiece {

        /**
         * 基本思路：(此部分已被弃用，为16版本开发记录)
         * 传参部分： 传入结构的nbt路径(modid:structures/...),通过TemplateManager生成Template数据保存(使用setup方法)同时可以设置方向等
         *          templatePosition用于存储结构添加的位置。
         *          同时将数据存入nbt包中，在另一构造函数中读取nbt以获取数据。
         *          其中Template类的template存储nbt模板数据，PlacementSettings的placeSetting存储诸如旋转，镜像的数据
         *          在官方给出的其他使用了的地方，通常使用了一套方法来使其高效运行：如下方loadTemplate
         * 再处理部分：handleDataMarker用于对加载的每个方块再次进行数据处理。
         *          值得注意的是，此方法通常不会被触发。你需要在你的结构中部署 结构方块-数据模式 以存储对应的数据标签
         *          之后postProcess方法才会调用此方法，传入的首个参数string即为结构方块存储的数据符
         *          你可以将它替换成你需要的方块，比如宝箱之类的，也可以用于生成位置固定的实体
         *          同时，原来的数据标签结构方块将被移除
         *          如上，我们使用addChildren来方便地处理添加结构的事……虽然说并没有很方便的亚子。注意，这个方法同时也在StructurePiece中有，只是传参不同所以没有显示覆写。
         *          另外，别忘了calculateBoundingBox()！
         *       */

        public static final ResourceLocation BUILDING_NBT_OLD = new ResourceLocation(Utils.MOD_ID,"cworld/pixark_library/library");
        public static final ResourceLocation BUILDING_NBT_NEW = new ResourceLocation(Utils.MOD_ID,"cworld/pixark_library/library2");

        public Tem(StructureTemplateManager templateManager, RandomSource random, BlockPos pos) {
            super(ExtraStructurePieceType.CW$PIXARK_LIBRARY, templateManager, random, pos,random.nextBoolean() ? BUILDING_NBT_NEW : BUILDING_NBT_OLD);
        }

        public Tem(StructureTemplateManager manager, CompoundTag tag) {
            super(ExtraStructurePieceType.CW$PIXARK_LIBRARY, manager,tag);
        }

        @Override
        protected void handleDataMarker(String meta, BlockPos pos, ServerLevelAccessor levelAccessor, RandomSource randomSource, BoundingBox boundingBox) {
//            System.out.println("info:" + dataName + " in " + pos.toString());
            if ("chest".equals(meta)) {
                if(randomSource.nextFloat() <= 0.2) {
                    createChest(levelAccessor,boundingBox,randomSource,pos,LootTable.CW_BLUEPRINT_BOX,null);
//                    }
                }else{
                    levelAccessor.setBlock(pos, Blocks.BOOKSHELF.defaultBlockState(), 2);
                }
            }else if("chest2".equals(meta)){
                if(randomSource.nextFloat() <= 0.15){
                    createChest(levelAccessor,boundingBox,randomSource,pos,LootTable.CW_BLUEPRINT_BOX,null);
                }else{
                    levelAccessor.setBlock(pos, Blocks.GRASS_BLOCK.defaultBlockState(), 2);
                }
            }
        }
    }
}
