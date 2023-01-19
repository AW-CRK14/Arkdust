package com.ardc.arkdust.worldgen.structure.structure.cworld;

import com.ardc.arkdust.RunHelper.PosHelper;
import com.ardc.arkdust.RunHelper.StructureHelper;
import com.ardc.arkdust.resourcelocation.LootTable;
import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.structure.ArdStructureAddInfo;
import com.ardc.arkdust.worldgen.structure.StructurePieceTypeAdd;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class PixArkLibrary extends Structure<NoFeatureConfig> implements ArdStructureAddInfo {

    public static final ResourceLocation BUILDING_NBT_OLD = new ResourceLocation(Utils.MOD_ID,"cworld/pixark_library/library");
    public static final ResourceLocation BUILDING_NBT_NEW = new ResourceLocation(Utils.MOD_ID,"cworld/pixark_library/library2");

    public PixArkLibrary(Codec<NoFeatureConfig> p_i231997_1_) {
        super(p_i231997_1_);
    }

    @Override
    public GenerationStage.Decoration step() {//�����ɵ�λ���йأ���š�
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource,
                                     long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ,
                                     Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        BlockPos centerOfChunk = new BlockPos((chunkX << 4), 0, (chunkZ << 4));

        return StructureHelper.isEachPlaceAvailable(chunkGenerator, Heightmap.Type.WORLD_SURFACE_WG,4,
                PosHelper.getCenterAndSquareVertexPos(centerOfChunk,16,false,true)
        ) && biome.getBaseTemperature() <= 1.2F;//��ȡ��λ���Ƿ�Ϊ���壨��ֹ������ˮ�ϣ�
    }

    @Override
    public int spacing() {
        return 360;
    }

    @Override
    public int separation() {
        return 175;
    }

    @Override
    public int salt() {
        return 48948923;
    }

    @Override
    public buildMode mode() {
        return buildMode.OVERWORLD;
//        return buildMode.NOISY_REF;
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return Start::new;
    }

    public static class Start extends StructureStart<NoFeatureConfig> {

        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
            int x = chunkX * 16;
            int z = chunkZ * 16;
            int landHeight = chunkGenerator.getBaseHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG);
            BlockPos centerPos = new BlockPos(x,Math.max(landHeight-3,1),z);
            addChildren(templateManagerIn,this.pieces,random,centerPos);
//            System.out.println("piece add test:");
//            PrintHelper.printList(this.pieces);
            this.calculateBoundingBox();
            StructureHelper.movePieceToCenter(centerPos,this.boundingBox,this.pieces);
            this.calculateBoundingBox();
        }
    }

    public static void addChildren(TemplateManager templateManager, List<StructurePiece> pieceList, Random random, BlockPos centerPos) {
        Piece piece = new Piece(templateManager,random.nextBoolean() ? BUILDING_NBT_NEW : BUILDING_NBT_OLD,centerPos, Rotation.getRandom(random));
//        piece.setOrientation(Direction.SOUTH);
//        DirectionHelper.PosDirectionRun(centerPos,piece.getBoundingBox().getCenter(),new PosHelper.PosMoveBag(-16,0,-16),false).pieceMove(piece);
        pieceList.add(piece);
    }

    public static class Piece extends TemplateStructurePiece{

        public final ResourceLocation templateLocation;
        public final Rotation rotation;

        /**
         * ����˼·��
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
        public Piece(TemplateManager templateManager, ResourceLocation structurePlace, BlockPos addPos, Rotation aRotation) {
            super(StructurePieceTypeAdd.PIX_ARK_LIBRARY, 3);
            this.templateLocation = structurePlace;
            this.templatePosition = addPos;
            this.rotation = aRotation;
            this.loadTemplate(templateManager);
        }

        protected void addAdditionalSaveData(CompoundNBT nbt) {
            super.addAdditionalSaveData(nbt);
            nbt.putString("Template", this.templateLocation.toString());
            nbt.putString("Rot", this.rotation.name());
        }

        public Piece(TemplateManager templateManager, CompoundNBT nbt) {
            super(StructurePieceTypeAdd.PIX_ARK_LIBRARY, nbt);
            this.templateLocation = new ResourceLocation(nbt.getString("Template"));
            this.rotation = Rotation.valueOf(nbt.getString("Rot"));
            this.loadTemplate(templateManager);
//            System.out.println("piece loading test\nnbt:" + nbt.toString() + "\ntemplateManager:" + templateManager.toString() + "\ntemplate location:" + templateLocation + "\ntemplate:" + template + "\nsetting:" + placeSettings);
        }

        private void loadTemplate(TemplateManager templateManager) {
            Template template = templateManager.getOrCreate(this.templateLocation);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setRotationPivot(new BlockPos(16,0,16)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementsettings);
        }

//        public boolean postProcess(ISeedReader p_230383_1_, StructureManager p_230383_2_, ChunkGenerator p_230383_3_, Random p_230383_4_, MutableBoundingBox p_230383_5_, ChunkPos p_230383_6_, BlockPos p_230383_7_) {
//            System.out.println("PostProcess load:\nISeedReader:" + p_230383_1_ +"\nChuckGenerator:" + p_230383_3_ + "\nMutableBoundingBox:" + p_230383_5_ + "\nPos:" + p_230383_6_ + "  " + p_230383_7_);
//            return super.postProcess(p_230383_1_,p_230383_2_,p_230383_3_,p_230383_4_,p_230383_5_,p_230383_6_,p_230383_7_);
//        }

        @Override
        protected void handleDataMarker(String dataName, BlockPos pos, IServerWorld world, Random preRandom, MutableBoundingBox box) {
//            System.out.println("info:" + dataName + " in " + pos.toString());
            if ("chest".equals(dataName)) {
                Random r = PosHelper.posToRandom(pos);
                if(r.nextFloat() <= 0.2) {
                    createChest(world,box,r,pos,LootTable.CW_BLUEPRINT_BOX,null);
//                    }
                }else{
                    world.setBlock(pos, Blocks.BOOKSHELF.defaultBlockState(), 2);
                }
            }else if("chest2".equals(dataName)){
                Random r = PosHelper.posToRandom(pos);
                if(r.nextFloat() <= 0.15){
                    createChest(world,box,r,pos,LootTable.CW_BLUEPRINT_BOX,null);
                }else{
                    world.setBlock(pos, Blocks.GRASS_BLOCK.defaultBlockState(), 2);
                }
            }
        }
    }
}
