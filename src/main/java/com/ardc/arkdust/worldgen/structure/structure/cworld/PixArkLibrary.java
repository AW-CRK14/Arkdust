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
    public GenerationStep.Decoration step() {//�����ɵ�λ���йأ���š�
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
         * ����˼·��(�˲����ѱ����ã�Ϊ16�汾������¼)
         * ���β��֣� ����ṹ��nbt·��(modid:structures/...),ͨ��TemplateManager����Template���ݱ���(ʹ��setup����)ͬʱ�������÷����
         *          templatePosition���ڴ洢�ṹ��ӵ�λ�á�
         *          ͬʱ�����ݴ���nbt���У�����һ���캯���ж�ȡnbt�Ի�ȡ���ݡ�
         *          ����Template���template�洢nbtģ�����ݣ�PlacementSettings��placeSetting�洢������ת�����������
         *          �ڹٷ�����������ʹ���˵ĵط���ͨ��ʹ����һ�׷�����ʹ���Ч���У����·�loadTemplate
         * �ٴ����֣�handleDataMarker���ڶԼ��ص�ÿ�������ٴν������ݴ���
         *          ֵ��ע����ǣ��˷���ͨ�����ᱻ����������Ҫ����Ľṹ�в��� �ṹ����-����ģʽ �Դ洢��Ӧ�����ݱ�ǩ
         *          ֮��postProcess�����Ż���ô˷�����������׸�����string��Ϊ�ṹ����洢�����ݷ�
         *          ����Խ����滻������Ҫ�ķ��飬���籦��֮��ģ�Ҳ������������λ�ù̶���ʵ��
         *          ͬʱ��ԭ�������ݱ�ǩ�ṹ���齫���Ƴ�
         *          ���ϣ�����ʹ��addChildren������ش�����ӽṹ���¡�����Ȼ˵��û�кܷ�������ӡ�ע�⣬�������ͬʱҲ��StructurePiece���У�ֻ�Ǵ��β�ͬ����û����ʾ��д��
         *          ���⣬������calculateBoundingBox()��
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
