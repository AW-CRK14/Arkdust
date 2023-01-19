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
    public GenerationStage.Decoration step() {//与生成的位置有关，大概。
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource,
                                     long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ,
                                     Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        BlockPos centerOfChunk = new BlockPos((chunkX << 4), 0, (chunkZ << 4));

        return StructureHelper.isEachPlaceAvailable(chunkGenerator, Heightmap.Type.WORLD_SURFACE_WG,4,
                PosHelper.getCenterAndSquareVertexPos(centerOfChunk,16,false,true)
        ) && biome.getBaseTemperature() <= 1.2F;//获取此位置是否为流体（防止生成在水上）
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
         * 基本思路：
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
