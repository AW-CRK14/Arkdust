package com.ardc.arkdust.advanced_obj;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.helper.DirectionAndRotationHelper;
import com.ardc.arkdust.helper.ListAndMapHelper;
import com.ardc.arkdust.helper.PosHelper;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.JigsawBlock;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.JigsawTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.JigsawOrientation;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
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
    public Map<ChunkPos,List<MutableBoundingBox>> boundingBoxMap;
    public boolean skipSpecialPieceManager = false;//��������Ƭ�����˱���Ӧ����special piece manager��ʹ��
    public AATemJigsawPiece(IStructurePieceType iStructurePieceType, TemplateManager templateManager, ResourceLocation structurePlace, BlockPos addPos, Rotation rotation, BlockPos rotationPivotPoint, int growth,BlockPos ignorePos,MapNecessityTemJigsawPieceElement k2eMap,Map<ChunkPos,List<MutableBoundingBox>> bbMap,String generateFromKey) {
        super(iStructurePieceType, 3);
        this.templateLocation = structurePlace;
        this.templatePosition = addPos;
        this.rotation = rotation;
        this.rotationPivotPos = rotationPivotPoint;
        this.loadTemplate(templateManager);
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
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putString("Template", this.templateLocation.toString());
        nbt.putString("Rot", this.rotation.name());
        nbt.putLong("Pivot",this.rotationPivotPos.asLong());
        nbt.putLong("Ignore",this.ignorePos.asLong());
        nbt.putInt("Growth",this.growth);
    }
    public AATemJigsawPiece(IStructurePieceType iStructurePieceType, TemplateManager templateManager, CompoundNBT nbt) {
        super(iStructurePieceType, nbt);
        this.templateLocation = new ResourceLocation(nbt.getString("Template"));
        this.rotation = Rotation.valueOf(nbt.getString("Rot"));
        this.rotationPivotPos = BlockPos.of(nbt.getLong("Pivot"));
        this.ignorePos = BlockPos.of(nbt.getLong("Ignore"));
        this.growth = nbt.getInt("Growth");
        this.loadTemplate(templateManager);
    }
    public void loadTemplate(TemplateManager templateManager) {
        Template template = key2ElementMap!=null ? key2ElementMap.getTemplate(templateManager, this.templateLocation) : templateManager.getOrCreate(templateLocation);
        PlacementSettings placementsettings = getSpecialSettingMap().getOrDefault(this.templateLocation,getPlacementSetting());
        this.setup(template, this.templatePosition, placementsettings);
    }
    public void clearMap(){
        this.boundingBoxMap = null;
        this.key2ElementMap = null;
    }

    //��ȡģ��������ƴͼ������Ϣ
    public List<Template.BlockInfo> getJigsawBlocksInfo(){
        return this.template.filterBlocks(this.templatePosition, this.placeSettings, Blocks.JIGSAW);
    }

    //��ȡƴͼ�����ƽ�淽��
    public Direction getJigsawInfoHorizonDirection(Template.BlockInfo info){
        return info.state.getValue(BlockStateProperties.ORIENTATION).front().getAxis() != Direction.Axis.Y ? info.state.getValue(BlockStateProperties.ORIENTATION).front() : info.state.getValue(BlockStateProperties.ORIENTATION).top();
    }

    public Rotation getJigsawInfoRotation(Template.BlockInfo infoTo,Template.BlockInfo infoFrom,Rotation fallback){
        JigsawOrientation to = infoTo.state.getValue(BlockStateProperties.ORIENTATION);
        JigsawOrientation from = infoFrom.state.getValue(BlockStateProperties.ORIENTATION);
        if (to.front().getAxis().equals(Direction.Axis.Y) && from.front().getAxis().equals(Direction.Axis.Y)){
//            if(from.front().getAxisDirection() != to.front().getAxisDirection()) return isJigsawBlockRollable(infoFrom) ? fallback : DirectionAndRotationHelper.direction2Rotation(from.top(),to.top());
            if(from.front().getAxisDirection() != to.front().getAxisDirection()) return DirectionAndRotationHelper.direction2Rotation(to.top(),from.top());
            return null;
        }else {
            if(from.front().getAxis().equals(Direction.Axis.Y) || to.front().getAxis().equals(Direction.Axis.Y)) return null;
            return DirectionAndRotationHelper.direction2Rotation(from.front(),to.front().getOpposite());
        }
    }

    //���������piece���б�
    public List<AATemJigsawPiece> createChildList(TemplateManager templateManager){
        if(start) this.checkBoundingBox();
        List<AATemJigsawPiece> list = new ArrayList<>();
        if(this.growth == 0) return list;
        //��ȡ���������Ϊ�յ�ƴͼ������Ϣ
        List<Template.BlockInfo> info =getJigsawBlocksInfo().stream().filter(i->!i.nbt.getString("target").equals("minecraft:empty") && !i.nbt.getString("target").equals("minecraft:") && !i.pos.equals(ignorePos)).collect(Collectors.toList());
        //����λ�ô��������
        Random r = PosHelper.posToRandom(templatePosition);

        for(Template.BlockInfo i : info){
            //��ȡ�µ����ӽṹ��·��
            List<ResourceLocation> newResourceList = key2ElementMap.randomLocation(i.nbt.getString("target"),r,getTemJigsawElementRequireType());
            if(newResourceList.isEmpty()) continue;
            newResourceList = newResourceList.subList(0,Math.min(Math.max(this.growth,4),newResourceList.size()));
            //��ȡ���������Ŀ����
            String targetName = i.nbt.getString("target");
            if(tryAddPiece(templateManager, list, i, newResourceList, targetName,null))
                tryAddPiece(templateManager,list,i,key2ElementMap.randomLocation(targetName,r,TemJigsawPieceElement.RequireType.END),targetName,null);
        }
        return list;
    }

    private boolean tryAddPiece(TemplateManager templateManager, List<AATemJigsawPiece> list, Template.BlockInfo i, List<ResourceLocation> newResourceList, String targetName,Rotation rotation) {
        for (ResourceLocation l : newResourceList) {
            //������ȷΪempty����Դ·��ֱ�ӷ���
            if(l.getPath().equals("empty")) return false;
            //ͨ����������λ����Ŀ�괴����Ƭ��Ŀ�귽�����۶�Ӧλ��
            BlockPos checkPos = getJigsawFrontPos(i);
            Template newTemplate = key2ElementMap.getTemplate(templateManager,l);
            //��ȡĿ��������name�뷢������target��ͬ�ķ�����Ϣ
            List<Template.BlockInfo> targetJigsawInfo = ListAndMapHelper.disorganizeList(PosHelper.posToRandom(checkPos), newTemplate.filterBlocks(BlockPos.ZERO, new PlacementSettings(), Blocks.JIGSAW).stream().filter(t -> t.nbt.getString("name").equals(targetName)).collect(Collectors.toList()));
            if (targetJigsawInfo.isEmpty()) continue;
            for (Template.BlockInfo targetInfo : targetJigsawInfo) {
                //�������ݻ�ȡ��ת�����ɵ����Ϣ
                BlockPos spawnPos = checkPos.offset(-targetInfo.pos.getX(), -targetInfo.pos.getY(), -targetInfo.pos.getZ());
                if(rotation==null) rotation = getJigsawInfoRotation(targetInfo,i,Rotation.getRandom(PosHelper.posToRandom(checkPos)));
                if(rotation==null) continue;

                //�����ض���Ƭ�������⴦����һ������Ϊ��ӵ�list���ڶ�������Ϊ�Ƿ���з�Χ��
                //����Ҫ��ȡƴͼ����ļ�����ʹ��i.nbt.getString("name")
                //�����ڴ���ʱ��skipSpecialPieceManagerѡ��������Ϊtrue�ﵽ�����㷨����Ŀ��
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

                //ע����ת������Ϊ�������λ��
                if(!checkBoundingBox(newTemplate.getBoundingBox(spawnPos,rotation,targetInfo.pos, Mirror.NONE))) continue;
                AATemJigsawPiece piece = createPiece().create(templateManager, l, spawnPos, rotation,
                        targetInfo.pos,p.getFirst() ? p.getSecond() : this.growth - (shouldNotGrow(targetName,l) ? 0 : 1),
                        checkPos,key2ElementMap,boundingBoxMap,targetName);
                list.add(piece);
                key2ElementMap.onResourceUse(targetName,l);
                return false;
            }

        }
        return true;
    }

    //�˷�������ǿ��ʹshouldNotGrow����ʧЧ���� ͨ���ȶ�Ŀ���ģ��·��ʹ����������key2map�бȶ�Ŀ�������false
    //��ҪʱӦoverride�˷���
    public boolean noNecessaryAlright(ResourceLocation location,String key){
        return false;
    }

    private boolean shouldNotGrow(String key,ResourceLocation location){
        return this.growth==2 && !noNecessaryAlright(location,key) && !key2ElementMap.isNecessaryEmpty(key);
    }

    public Pair<Boolean,Integer> setChildGrowth(String key,ResourceLocation toLocation){
        return Pair.of(false,0);
    }

    //��д��������
    public boolean postProcess(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random, MutableBoundingBox box, ChunkPos chunkPos, BlockPos blockPos) {
        this.placeSettings.setBoundingBox(box);
        this.boundingBox = this.template.getBoundingBox(this.placeSettings, this.templatePosition);
        if (this.template.placeInWorld(iSeedReader, this.templatePosition, blockPos, this.placeSettings, random, 2)) {
            for(Template.BlockInfo template$blockinfo : this.template.filterBlocks(this.templatePosition, this.placeSettings, Blocks.STRUCTURE_BLOCK)) {
                if (template$blockinfo.nbt != null) {
                    StructureMode structuremode = StructureMode.valueOf(template$blockinfo.nbt.getString("mode"));
                    if (structuremode == StructureMode.DATA) {
                        this.handleDataMarker(template$blockinfo.nbt.getString("metadata"), template$blockinfo.pos, iSeedReader, random, box);
                    }
                }
            }

            for(Template.BlockInfo template$blockinfo1 : this.template.filterBlocks(this.templatePosition, this.placeSettings, Blocks.JIGSAW)) {
                if (template$blockinfo1.nbt != null) {
                    String s = template$blockinfo1.nbt.getString("final_state");
                    BlockStateParser blockstateparser = new BlockStateParser(new StringReader(s), false);
                    BlockState blockstate = Blocks.AIR.defaultBlockState();

                    try {
                        blockstateparser.parse(true);
                        BlockState blockstate1 = blockstateparser.getState();
                        if (blockstate1 != null) {
                            blockstate = blockstate1;
                        } else {
                            LOGGER.error("Error while parsing blockstate {} in jigsaw block @ {}", s, template$blockinfo1.pos);
                        }
                    } catch (CommandSyntaxException commandsyntaxexception) {
                        LOGGER.error("Error while parsing blockstate {} in jigsaw block @ {}", s, template$blockinfo1.pos);
                    }

                    jigsawDataMarker(blockstate,template$blockinfo1.pos,iSeedReader,random,box);
                }
            }
        }

        return true;
    }

    //����ƴͼ����Ķ��⴦��
    protected void jigsawDataMarker(BlockState defaultState, BlockPos pos, ISeedReader iSeedReader, Random random, MutableBoundingBox box){
        iSeedReader.setBlock(pos, defaultState, 3);
    };

    //�˷������ڶ�����ṹƬ���д�����Ҫʱoverride
    public Pair<List<AATemJigsawPiece>,Boolean> specialPieceManager(TemplateManager manager,ResourceLocation resourceLocation,BlockPos connectPos,Template preparedTemplate,Template.BlockInfo jigsawInfo,BlockPos defaultSpawnPos,Rotation defaultRotation,Template.BlockInfo toJigsawInfo){
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
        List<ChunkPos> chunkList = ChunkPos.rangeClosed(p.getFirst(),p.getSecond()).collect(Collectors.toList());
        for(ChunkPos pos : chunkList)
            ListAndMapHelper.tryAddElementToMapList(boundingBoxMap,pos,piece.boundingBox);
        return true;
    }

    private boolean checkBoundingBox(MutableBoundingBox box){
        Pair<ChunkPos,ChunkPos> p = PosHelper.boundingBoxToChunkPos(box);
        List<ChunkPos> chunkList = ChunkPos.rangeClosed(p.getFirst(),p.getSecond()).collect(Collectors.toList());
        for (ChunkPos pos : chunkList) {
            List<MutableBoundingBox> list = boundingBoxMap.getOrDefault(pos, Collections.EMPTY_LIST);
            for (MutableBoundingBox inBox : list) {
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

    //���ƴͼ�����Ƿ�Ϊ����ת
    public static boolean isJigsawBlockRollable(Template.BlockInfo info){
        return info.nbt.getString("joint").equals("rollable");
    }
    //��ȡƴͼ�����Ӧ��������
    public static BlockPos getJigsawFrontPos(Template.BlockInfo info){
        return info.pos.relative(info.state.getValue(BlockStateProperties.ORIENTATION).front());
    }

    public abstract CreatePiece createPiece();
    public interface CreatePiece {
        AATemJigsawPiece create(TemplateManager templateManager, ResourceLocation structurePlace,
                                BlockPos addPos, Rotation rotation, BlockPos rotationPivotPoint,
                                int growth,BlockPos ignorePos,MapNecessityTemJigsawPieceElement k2eMap,Map<ChunkPos,List<MutableBoundingBox>> bbMap,String generateFromKey);
    }
    private PlacementSettings getPlacementSetting(){
        return placementSettingsAttach((new PlacementSettings()).setRotation(this.rotation).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK).setRotationPivot(this.rotationPivotPos));
    }

    public PlacementSettings placementSettingsAttach(PlacementSettings settings){
        return settings;
    }

    protected void handleDataMarker(String p_186175_1_, BlockPos p_186175_2_, IServerWorld p_186175_3_, Random p_186175_4_, MutableBoundingBox p_186175_5_) {
    }

    //    public abstract TemJigsawPieceElement getTemJigsawPieceElement();
    public abstract int limitGrowth();//�����ֵ����������ǿ�����ɲ��֣���� �� β ���������ͬ
    public abstract int minGrowthAllow();//�����ֵ��ʼ���� β
    public List<ResourceLocation> getIgnoreBoundingBoxCheckList(){return Collections.EMPTY_LIST;}
    public Map<ResourceLocation,PlacementSettings> getSpecialSettingMap(){return Collections.EMPTY_MAP;}


//    public abstract Map<ChunkPos,MutableBoundingBox> boundingBoxMap();

    //����ʹ�õĽṹԪ�ش����
    public abstract static class TemJigsawPieceElement{
        private final Map<String,List<ResourceLocation>> random;
        private final Map<String,List<ResourceLocation>> necessity;
        private final Map<String,List<ResourceLocation>> end;
        private final Map<ResourceLocation,Template> prepared_template_map;
        public TemJigsawPieceElement(Map<String,List<ResourceLocation>> randomPart, Map<String,List<ResourceLocation>> necessity, Map<String,List<ResourceLocation>> end){
            this.random = randomPart == null ? new HashMap<>() : randomPart;
            this.necessity = necessity == null ? new HashMap<>() : necessity;
            this.end = end == null ? new HashMap<>() : end;
            prepared_template_map = new HashMap<>();
        }

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
         * ����һ������ṹ·���б����Զ��������
         * @param key ��ȡ����map�ļ�
         * @param r һ���������
         * @param type ��ȡ�ķ�ʽ
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

        public void buildTemplateMap(TemplateManager manager){
            end.forEach((key,list)->list.forEach((i)->{
                if(!prepared_template_map.containsKey(i))
                    prepared_template_map.put(i,manager.getOrCreate(i));
            }));
            random.forEach((key,list)->list.forEach((i)->{
                if(!prepared_template_map.containsKey(i))
                    prepared_template_map.put(i,manager.getOrCreate(i));
            }));
            necessity.forEach((key,list)->list.forEach((i)->{
                if(!prepared_template_map.containsKey(i))
                    prepared_template_map.put(i,manager.getOrCreate(i));
            }));
        }

        public Template getTemplate(TemplateManager manager,ResourceLocation location){
            return prepared_template_map.getOrDefault(location,manager.getOrCreate(location));
        }
    }

    //���ԣ���Ҫ�ṹ����map��Ӧ�洢
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
                return Arrays.asList(new ResourceLocation(Utils.MOD_ID,"empty"));
            switch (type){
                case NECESSITY_FIRST:
                    return ListAndMapHelper.disorganizeList(r,end,necessity,necessity,necessity,random);
                case NORMAL:
                    return ListAndMapHelper.disorganizeList(r,end,necessity,necessity,random,random);
                case END:
                    return ListAndMapHelper.disorganizeList(r,end);
                case NECESSITY_IGNORE:
                case END_IGNORE:
                    return ListAndMapHelper.disorganizeList(r,necessity,random);
                default:
                    return Arrays.asList(new ResourceLocation(Utils.MOD_ID,"empty"));//TODO
            }
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
}
