package com.ardc.arkdust.advanced_obj;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.helper.DirectionAndRotationHelper;
import com.ardc.arkdust.helper.ListAndMapHelper;
import com.ardc.arkdust.helper.PosHelper;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Pair;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AATemJigsawPiece extends TemplateStructurePiece {
    private static final Logger LOGGER = Utils.LOGGER;
    public final ResourceLocation templateLocation;
    public final Rotation rotation;
    public final int growth;
    public final BlockPos rotationPivotPos;
    public final BlockPos ignorePos;

    public boolean start;
    public String generateFromKey;
    public MapNecessityTemJigsawPieceElement key2ElementMap;
    public Map<ChunkPos,List<BoundingBox>> boundingBoxMap;
    public boolean skipSpecialPieceManager = false;//跳过特殊片处理，此变量应该在special piece manager处使用

    private static final StructurePlaceSettings EMPTY = new StructurePlaceSettings();
    public AATemJigsawPiece(StructurePieceType iStructurePieceType, StructureTemplateManager templateManager, ResourceLocation structurePlace, BlockPos addPos, Rotation rotation, BlockPos rotationPivotPoint, int growth, BlockPos ignorePos, MapNecessityTemJigsawPieceElement k2eMap, Map<ChunkPos,List<BoundingBox>> bbMap, String generateFromKey) {
        super(iStructurePieceType, 3,templateManager,structurePlace,structurePlace.toString(),EMPTY,addPos);
        this.templateLocation = structurePlace;
        this.templatePosition = addPos;
        this.rotation = rotation;
        this.rotationPivotPos = rotationPivotPoint;
        this.setup();
        this.growth = growth;
        this.ignorePos = ignorePos;
        this.key2ElementMap = k2eMap;
        if(bbMap==null) {
            this.boundingBoxMap = new HashMap<>();
            this.start = true;
        }else {
            boundingBoxMap = bbMap;
        }
        this.generateFromKey = generateFromKey;

    }
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag nbt) {
        super.addAdditionalSaveData(context,nbt);
        nbt.putString("Template", this.templateLocation.toString());
        nbt.putString("Rot", this.rotation.name());
        nbt.putLong("Pivot",this.rotationPivotPos.asLong());
        nbt.putLong("Ignore",this.ignorePos.asLong());
        nbt.putInt("Growth",this.growth);
    }
    public AATemJigsawPiece(StructurePieceType iStructurePieceType, StructureTemplateManager templateManager, CompoundTag nbt) {
        super(iStructurePieceType, nbt,templateManager,(r)->EMPTY);
        this.templateLocation = new ResourceLocation(nbt.getString("Template"));
        this.rotation = Rotation.valueOf(nbt.getString("Rot"));
        this.rotationPivotPos = BlockPos.of(nbt.getLong("Pivot"));
        this.ignorePos = BlockPos.of(nbt.getLong("Ignore"));
        this.growth = nbt.getInt("Growth");
        this.setup();
    }
    public void setup() {
        this.placeSettings = getSpecialSettingMap().getOrDefault(this.templateLocation,getPlacementSetting());
        this.boundingBox = template.getBoundingBox(placeSettings,templatePosition);
    }
    public void clearMap(){
        this.boundingBoxMap = null;
        this.key2ElementMap = null;
    }

    //获取模板内所有拼图方块信息
    public List<StructureTemplate.StructureBlockInfo> getJigsawBlocksInfo(){
        return this.template.filterBlocks(this.templatePosition, this.placeSettings, Blocks.JIGSAW);
    }

    //获取拼图方块的平面方向
    public Direction getJigsawInfoHorizonDirection(StructureTemplate.StructureBlockInfo info){
        return info.state().getValue(BlockStateProperties.ORIENTATION).front().getAxis() != Direction.Axis.Y ? info.state().getValue(BlockStateProperties.ORIENTATION).front() : info.state().getValue(BlockStateProperties.ORIENTATION).top();
    }

    public Rotation getJigsawInfoRotation(StructureTemplate.StructureBlockInfo infoTo,StructureTemplate.StructureBlockInfo infoFrom,Rotation fallback){
        FrontAndTop to = infoTo.state().getValue(BlockStateProperties.ORIENTATION);
        FrontAndTop from = infoFrom.state().getValue(BlockStateProperties.ORIENTATION);
        if (to.front().getAxis().equals(Direction.Axis.Y) && from.front().getAxis().equals(Direction.Axis.Y)){
//            if(from.front().getAxisDirection() != to.front().getAxisDirection()) return isJigsawBlockRollable(infoFrom) ? fallback : DirectionAndRotationHelper.direction2Rotation(from.top(),to.top());
            if(from.front().getAxisDirection() != to.front().getAxisDirection()) return DirectionAndRotationHelper.direction2Rotation(to.top(),from.top());
            return null;
        }else {
            if(from.front().getAxis().equals(Direction.Axis.Y) || to.front().getAxis().equals(Direction.Axis.Y)) return null;
            return DirectionAndRotationHelper.direction2Rotation(from.front(),to.front().getOpposite());
        }
    }

    //创建延申的piece的列表
    public List<AATemJigsawPiece> createChildList(StructureTemplateManager templateManager){
        if(start) this.checkBoundingBox();
        List<AATemJigsawPiece> list = new ArrayList<>();
        if(this.growth == 0) return list;
        //获取所有输出不为空的拼图方块信息
        List<StructureTemplate.StructureBlockInfo> info = getJigsawBlocksInfo().stream().filter(i -> i.nbt() != null && !i.nbt().getString("target").equals("minecraft:empty") && !i.nbt().getString("target").equals("minecraft:") && !i.pos().equals(ignorePos)).toList();
        //根据位置创建随机数
        Random r = PosHelper.posToRandom(templatePosition);

        for(StructureTemplate.StructureBlockInfo i : info){
            //获取新的连接结构的路径
            List<ResourceLocation> newResourceList = key2ElementMap.randomLocation(i.nbt().getString("target"),r,getTemJigsawElementRequireType());
            if(newResourceList.isEmpty()) continue;
            newResourceList = newResourceList.subList(0,Math.min(Math.max(this.growth,4),newResourceList.size()));
            //获取发出方块的目标名
            String targetName = i.nbt().getString("target");
            if(tryAddPiece(templateManager, list, i, newResourceList, targetName,null))
                tryAddPiece(templateManager,list,i,key2ElementMap.randomLocation(targetName,r,TemJigsawPieceElement.RequireType.END),targetName,null);
        }
        return list;
    }

    private boolean tryAddPiece(StructureTemplateManager templateManager, List<AATemJigsawPiece> list, StructureTemplate.StructureBlockInfo i, List<ResourceLocation> newResourceList, String targetName,Rotation rotation) {
        for (ResourceLocation l : newResourceList) {
            //对于明确为empty的资源路径直接返回
            if(l.getPath().equals("empty")) return false;
            //通过发出方块位置与目标创建新片的目标方块理论对应位置
            BlockPos checkPos = getJigsawFrontPos(i);
            StructureTemplate newTemplate = templateManager.getOrCreate(l);
            //获取目标中所有name与发出方块target相同的方块信息
            List<StructureTemplate.StructureBlockInfo> targetJigsawInfo = ListAndMapHelper.disorganizeList(PosHelper.posToRandom(checkPos), newTemplate.filterBlocks(BlockPos.ZERO, new StructurePlaceSettings(), Blocks.JIGSAW).stream().filter(t -> t.nbt().getString("name").equals(targetName)).collect(Collectors.toList()));
            if (targetJigsawInfo.isEmpty()) continue;
            for (StructureTemplate.StructureBlockInfo targetInfo : targetJigsawInfo) {
                //根据数据获取旋转，生成点等信息
                BlockPos spawnPos = checkPos.offset(-targetInfo.pos().getX(), -targetInfo.pos().getY(), -targetInfo.pos().getZ());
                if(rotation==null) rotation = getJigsawInfoRotation(targetInfo,i,Rotation.getRandom(new LegacyRandomSource(checkPos.asLong())));
                if(rotation==null) continue;

                //对于特定的片进行特殊处理。第一个参数为添加的list，第二个参数为是否进行范围箱
                //若需要获取拼图方块的键，请使用i.nbt.getString("name")
                //可以在处理时将skipSpecialPieceManager选择性设置为true达到减少算法负担目的
                if(!skipSpecialPieceManager) {
                    Pair<List<AATemJigsawPiece>, Boolean> pair = specialPieceManager(templateManager, l, checkPos, newTemplate, i, spawnPos, rotation, targetInfo);
                    if (pair != null && pair.getFirst() != null) {
                        for (AATemJigsawPiece piece : pair.getFirst()) {
                            if (!pair.getSecond() || piece.checkBoundingBox()) {
                                list.add(piece);
                                key2ElementMap.onResourceUse(targetName, l);
                            }
                        }
                        return false;
                    }
                }
                Pair<Boolean,Integer> p = setChildGrowth(targetName,l);

                //注：旋转轴坐标为相对坐标位置
                if(!checkBoundingBox(newTemplate.getBoundingBox(spawnPos,rotation,targetInfo.pos(), Mirror.NONE))) continue;
                AATemJigsawPiece piece = createPiece().create(templateManager, l, spawnPos, rotation,
                        targetInfo.pos(),p.getFirst() ? p.getSecond() : this.growth - (shouldNotGrow(targetName,l) ? 0 : 1),
                        checkPos,key2ElementMap,boundingBoxMap,targetName);
                list.add(piece);
                key2ElementMap.onResourceUse(targetName,l);
                return false;
            }

        }
        return true;
    }

    //此方法用于强制使shouldNotGrow方法失效，即 通过比对目标的模板路径使其优先于在key2map中比对目标而返回false
    //需要时应override此方法
    public boolean noNecessaryAlright(ResourceLocation location,String key){
        return false;
    }

    private boolean shouldNotGrow(String key,ResourceLocation location){
        return this.growth==2 && !noNecessaryAlright(location,key) && !key2ElementMap.isNecessaryEmpty(key);
    }

    public Pair<Boolean,Integer> setChildGrowth(String key,ResourceLocation toLocation){
        return Pair.of(false,0);
    }

    //覆写生成流程
    public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos blockPos) {
        this.placeSettings.setBoundingBox(box);
        this.boundingBox = this.template.getBoundingBox(this.placeSettings, this.templatePosition);
        if (this.template.placeInWorld(level, this.templatePosition, blockPos, this.placeSettings, random, 2)) {
            for(StructureTemplate.StructureBlockInfo info : this.template.filterBlocks(this.templatePosition, this.placeSettings, Blocks.STRUCTURE_BLOCK)) {
                if (info.nbt() != null) {
                    StructureMode structuremode = StructureMode.valueOf(info.nbt().getString("mode"));
                    if (structuremode == StructureMode.DATA) {
                        this.handleDataMarker(info.nbt().getString("metadata"), info.pos(), level, random, box);
                    }
                }
            }

            for(StructureTemplate.StructureBlockInfo info : this.template.filterBlocks(this.templatePosition, this.placeSettings, Blocks.JIGSAW)) {
                if (info.nbt() != null) {
                    String s = info.nbt().getString("final_state");
                    BlockState blockstate = Blocks.AIR.defaultBlockState();

                    try {
                        blockstate = BlockStateParser.parseForBlock(level.holderLookup(Registries.BLOCK), s, true).blockState();
                    } catch (CommandSyntaxException commandsyntaxexception) {
                        LOGGER.error("Error while parsing blockstate {} in jigsaw block @ {}", s, info.pos());
                    }

                    jigsawDataMarker(blockstate,info.pos(),level,random,box);
                }
            }
        }
    }

    //对于拼图方块的额外处理
    protected void jigsawDataMarker(BlockState defaultState, BlockPos pos, WorldGenLevel iSeedReader, RandomSource random, BoundingBox box){
        iSeedReader.setBlock(pos, defaultState, 3);
    }

    //此方法用于对特殊结构片进行处理，需要时override
    public Pair<List<AATemJigsawPiece>, Boolean> specialPieceManager(StructureTemplateManager manager, ResourceLocation resourceLocation, BlockPos connectPos, StructureTemplate preparedTemplate, StructureTemplate.StructureBlockInfo jigsawInfo, BlockPos defaultSpawnPos, Rotation defaultRotation, StructureTemplate.StructureBlockInfo toJigsawInfo) {
        return null;
//        this.createPiece().create(manager,p.getFirst(),defaultSpawnPos,defaultRotation,PosHelper.posToRandom(connectPos),jigsawInfo.pos,p.getSecond(),connectPos,key2ElementMap,boundingBoxMap,jigsawInfo.nbt.getString("target"));
    }



    private boolean checkBoundingBox(){
        return checkBoundingBox(this);
    }
    private boolean checkBoundingBox(AATemJigsawPiece piece){
        if(!getIgnoreBoundingBoxCheckList().contains(piece.templateLocation))
            return checkBoundingBox(piece.boundingBox);

        Pair<ChunkPos,ChunkPos> p = PosHelper.boundingBoxToChunkPos(piece.boundingBox);
        List<ChunkPos> chunkList = ChunkPos.rangeClosed(p.getFirst(), p.getSecond()).toList();
        for(ChunkPos pos : chunkList)
            ListAndMapHelper.tryAddElementToMapList(boundingBoxMap,pos,piece.boundingBox);
        return true;
    }

    private boolean checkBoundingBox(BoundingBox box){
        Pair<ChunkPos,ChunkPos> p = PosHelper.boundingBoxToChunkPos(box);
        List<ChunkPos> chunkList = ChunkPos.rangeClosed(p.getFirst(), p.getSecond()).toList();
        for (ChunkPos pos : chunkList) {
            List<BoundingBox> list = boundingBoxMap.getOrDefault(pos, Collections.EMPTY_LIST);
            for (BoundingBox inBox : list) {
                if (inBox.intersects(box)) return false;
            }
        }
        for(ChunkPos pos : chunkList)
            ListAndMapHelper.tryAddElementToMapList(boundingBoxMap,pos,box);
        return true;
    }

    private TemJigsawPieceElement.RequireType getTemJigsawElementRequireType(){
        if(growth > minGrowthAllow()) return TemJigsawPieceElement.RequireType.END_IGNORE;
        if(growth > limitGrowth()) return TemJigsawPieceElement.RequireType.NORMAL;
        if(growth > 1) return TemJigsawPieceElement.RequireType.NECESSITY_FIRST;
        return TemJigsawPieceElement.RequireType.END;
    }

    //检测拼图方块是否为可旋转
    public static boolean isJigsawBlockRollable(StructureTemplate.StructureBlockInfo info){
        return info.nbt().getString("joint").equals("rollable");
    }
    //获取拼图方块对应方向坐标
    public static BlockPos getJigsawFrontPos(StructureTemplate.StructureBlockInfo info){
        return info.pos().relative(info.state().getValue(BlockStateProperties.ORIENTATION).front());
    }

    public abstract CreatePiece createPiece();
    public interface CreatePiece {
        AATemJigsawPiece create(StructureTemplateManager templateManager, ResourceLocation structurePlace,
                                BlockPos addPos, Rotation rotation, BlockPos rotationPivotPoint,
                                int growth,BlockPos ignorePos,MapNecessityTemJigsawPieceElement k2eMap,Map<ChunkPos,List<BoundingBox>> bbMap,String generateFromKey);
    }
    private StructurePlaceSettings getPlacementSetting(){
        return placementSettingsAttach((new StructurePlaceSettings()).setRotation(this.rotation).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK).setRotationPivot(this.rotationPivotPos));
    }

    public StructurePlaceSettings placementSettingsAttach(StructurePlaceSettings settings){
        return settings;
    }

    protected void handleDataMarker(String meta, BlockPos pos, ServerLevelAccessor levelAccessor, RandomSource randomSource, BoundingBox boundingBox) {
    }

    //    public abstract TemJigsawPieceElement getTemJigsawPieceElement();
    public abstract int limitGrowth();//到达此值后优先生成强制生成部分，随机 与 尾 分配概率相同
    public abstract int minGrowthAllow();//到达此值后开始生成 尾
    public List<ResourceLocation> getIgnoreBoundingBoxCheckList(){return Collections.EMPTY_LIST;}
    public Map<ResourceLocation,StructurePlaceSettings> getSpecialSettingMap(){return Collections.EMPTY_MAP;}


//    public abstract Map<ChunkPos,MutableBoundingBox> boundingBoxMap();

    //配套使用的结构元素处理表
    public abstract static class TemJigsawPieceElement{
        private final Map<String,List<ResourceLocation>> random;
        private final Map<String,List<ResourceLocation>> necessity;
        private final Map<String,List<ResourceLocation>> end;
        public TemJigsawPieceElement(Map<String,List<ResourceLocation>> randomPart, Map<String,List<ResourceLocation>> necessity, Map<String,List<ResourceLocation>> end){
            this.random = orEmpty(randomPart);
            this.necessity = orEmpty(necessity);
            this.end = orEmpty(end);
        }

        private Map<String,List<ResourceLocation>> orEmpty(Map<String,List<ResourceLocation>> map){
            return map == null ? new HashMap<>() : map;
        };

        public enum RequireType{
            NORMAL,
            NECESSITY_FIRST,
            NECESSITY_IGNORE,
            END,
            END_IGNORE
        }

        public Map<String,List<ResourceLocation>> getEnd() {
            return end;
        }

        public Map<String,List<ResourceLocation>> getNecessity() {
            return necessity;
        }

        public Map<String,List<ResourceLocation>> getRandomPart() {
            return random;
        }

        /**
         * 返回一个随机结构路径列表（含自动随机排序）
         * @param key 获取三个map的键
         * @param r 一个随机数包
         * @param type 获取的方式
         */
        public abstract List<ResourceLocation> randomLocation(String key,Random r,RequireType type);

        public abstract void onResourceUse(String key,ResourceLocation location);

        public abstract boolean isNecessaryEmpty(String key);

        public List<ResourceLocation> get(String key){
            List<ResourceLocation> locations = new ArrayList<>();
            locations.addAll(end.getOrDefault(key,Collections.EMPTY_LIST));
            locations.addAll(random.getOrDefault(key,Collections.EMPTY_LIST));
            locations.addAll(necessity.getOrDefault(key,Collections.EMPTY_LIST));
            return locations;
        }
    }

    //特性：必要结构按照map对应存储
    public static class MapNecessityTemJigsawPieceElement extends TemJigsawPieceElement{
        private Map<String,List<ResourceLocation>> necessity_require;


        public MapNecessityTemJigsawPieceElement(Map<String, List<ResourceLocation>> randomPart, Map<String, List<ResourceLocation>> necessity, Map<String, List<ResourceLocation>> end) {
            super(randomPart, necessity, end);
            this.necessity_require = new HashMap<>();
            this.necessity_require.putAll(necessity);
        }

        public void resetNecessityRequireList(){
            this.necessity_require = this.getNecessity();
        }

        public Map<String,List<ResourceLocation>> getNecessityRequire(){
            return this.necessity_require;
        }

        @Override
        public List<ResourceLocation> randomLocation(String key,Random r,RequireType type){
//            ListAndMapHelper.printList(getRandomPart().getOrDefault(key,new ArrayList<>()));
            List<ResourceLocation> end = getEnd().getOrDefault(key,new ArrayList<>());
            List<ResourceLocation> necessity = necessity_require.getOrDefault(key,new ArrayList<>());
            List<ResourceLocation> random = getRandomPart().getOrDefault(key,new ArrayList<>());

            if(end.isEmpty() && necessity.isEmpty() && random.isEmpty())
                return List.of(new ResourceLocation(Utils.MOD_ID, "empty"));
            return switch (type) {
                case NECESSITY_FIRST ->                 ListAndMapHelper.disorganizeList(r, end, necessity, necessity, necessity, random);
                case NORMAL ->                          ListAndMapHelper.disorganizeList(r, end, necessity, necessity, random, random);
                case END ->                             ListAndMapHelper.disorganizeList(r, end);
                case NECESSITY_IGNORE, END_IGNORE ->    ListAndMapHelper.disorganizeList(r, necessity, random);
//                default -> Arrays.asList(new ResourceLocation(Utils.MOD_ID, "empty"));//TODO
            };
        }

        @Override
        public void onResourceUse(String key, ResourceLocation location) {
            List<ResourceLocation> list = necessity_require.get(key);
            if(list != null) list.remove(location);
        }

        @Override
        public boolean isNecessaryEmpty(String key) {
            return necessity_require.getOrDefault(key,Collections.EMPTY_LIST).isEmpty();
        }


    }

    public static void iterateAATemJigsawPiece(StructurePiecesBuilder builder, AATemJigsawPiece piece, StructureTemplateManager manager){
        builder.pieces.add(piece);
        List<AATemJigsawPiece> childrenList = piece.createChildList(manager);
        builder.pieces.addAll(childrenList);
        if(!childrenList.isEmpty()){
            childrenList.forEach((i)->iterateAATemJigsawPiece(builder,i,manager));
        }
    }
}
