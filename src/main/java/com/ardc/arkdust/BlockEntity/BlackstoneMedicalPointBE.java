package com.ardc.arkdust.BlockEntity;

import com.ardc.arkdust.Items.blocks.BlackstoneMedicalPoint;
import com.ardc.arkdust.playmethod.camp.Camp;
import com.ardc.arkdust.playmethod.camp.CampHelper;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.registry.TileEntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlackstoneMedicalPointBE extends TileEntity implements ITickableTileEntity {
//    public boolean working;//�Ƿ��ڹ���״̬
    public int workingRange;//������Ч��Χ
    public boolean activation;//�������
    public boolean attackNeutrality;//�Ƿ񹥻�������λ
    public boolean attackUnfriendly;//�Ƿ񹥻��з���λ
    public boolean treatFriendly;//�Ƿ������ҷ���λ
    public int damage;//��е�;�
    public boolean canBeHurt;//���Ա��򡭡����������з����
    public List<Camp> campBelong;//���ڵ���Ӫ
    public List<Camp> campHate;//�жԵ���Ӫ
    public List<Float> funcValue = Arrays.asList(new Float[2]);
    public List<Integer> funcTick = Arrays.asList(new Integer[2]);
    public boolean belongUs;
    public boolean belonging;
    public enum mode{
        PHONOGRAPH,BSMP
    }
    public mode setMode;

    public int attackTimer = 0;
    public int treatTimer = 0;

    public BlackstoneMedicalPointBE() {
        super(TileEntityTypeRegistry.aBlackstoneMedicalPointBE.get());
    }

    public boolean ifDefaultSet() {
        return activation;
    }

    public boolean ifCanBeHurt(){
        return canBeHurt;
    }
    
//    public boolean ifWorking(){
//        return working;
//    }

    public void setDefaultValue(float attackValue, float treatValue, int attackTick, int treatTick, int damage, boolean attackNeutrality, boolean attackUnfriendly, boolean treatFriendly, boolean canBeHurt,int workingRange,mode mode) {
        funcValue.set(0, attackValue);
        funcValue.set(1, treatValue);
        funcTick.set(0, Math.max(attackTick, 1));
        funcTick.set(1, Math.max(treatTick, 1));
        this.damage = damage;
        this.activation = true;
        this.attackNeutrality = attackNeutrality;
        this.attackUnfriendly = attackUnfriendly;
        if(attackNeutrality) this.attackUnfriendly = true;
        this.treatFriendly = treatFriendly;
        this.canBeHurt = canBeHurt;
        this.setMode = mode;
        this.workingRange = workingRange;
        setChanged();
    }

    public void setCampBelong(List<Camp> campList){
        this.campBelong = campList;
        setChanged();
    }

    public void setCampHate(List<Camp> campList){
        this.campHate = campList;
        setChanged();
    }

    public void setBelongUs(boolean belongUs) {
        this.belongUs = belongUs;
        this.belonging = true;
        setChanged();
    }

//    public void setWorking(boolean working){
//        this.working = working;
//    }
    
    public int blackstoneMedicalPointState(){
        if(level==null)
            return 0;
        return level.getBlockState(worldPosition).getValue(BlackstoneMedicalPoint.RUNNING_STATE);
    }

    //TODO ս������ģ��
    //TODO �;���ʧģ��

    public void tick() {
        if (level != null && !level.isClientSide()) {//ǰ���ж�
            BlockState blockState = level.getBlockState(worldPosition);//��ȡ����
            if (!(blockState.getValue(BlackstoneMedicalPoint.RUNNING_STATE) == 3) || !activation) return;//��ȡ����״̬�Ƿ�Ϊ������,���򷵻�
            //�������Ʒ�Χaabb��
            AxisAlignedBB rangeAABB = new AxisAlignedBB(worldPosition.getX() - workingRange, worldPosition.getY() - workingRange, worldPosition.getZ() - workingRange, worldPosition.getX() + workingRange, worldPosition.getY() + workingRange, worldPosition.getZ() + workingRange);
            if (setMode == mode.BSMP && level.getBlockState(worldPosition.below()).getBlock() != BlockRegistry.pau_block.get()) {//��BSMPģʽ�����·��ķ��鲻�ǳ���ʱ
                level.setBlock(worldPosition, blockState.setValue(BlackstoneMedicalPoint.RUNNING_STATE, 2), 1);//������״̬����Ϊȱ�ٷ�Ӧ��
                return;
            }

            if (treatFriendly) {//��������Ѻõ�λģ�鼤��
                if (treatTimer < funcTick.get(1)) treatTimer++;//ҽ�Ƴ����ʱ
                else {//��ҽ�Ƴ���ﵽĿ��ʱ��
                    List<LivingEntity> entityList = level.getEntitiesOfClass(LivingEntity.class, rangeAABB);//��ȡ��Χ��ʵ��
                    List<LivingEntity> goalEntityList = new ArrayList<>();//�����յ�,Ŀ��ʵ���б�
                    for (LivingEntity entity : entityList) {//����ʵ��
                        if (CampHelper.ifEntityBelongToCamp(campBelong, entity) && entity.getHealth() < entity.getMaxHealth()) { //���ʵ�����ڴ���Ӫ��Ѫ���������� TODO ��Ӫ�л�
//                            System.out.printf(entity+"%n  heal before:" + entity.getHealth());
                            goalEntityList.add(entity);//��Ŀ��ʵ�������ʵ��
                            entity.heal(funcValue.get(1));//����ʵ��
//                            System.out.printf("%n  heal after:" + entity.getHealth());
                            addHappyVillagerParticle(entity.getX(),entity.getY(),entity.getZ());//�������
                        }
                    }
                    setChanged();
                    if(!goalEntityList.isEmpty()) treatTimer = 0;//���������ʵ��,����ʱ����
                    pauBlockChange(goalEntityList.size());//�������Ƶ������ж��Ƿ��������
                }
            }
            if (attackUnfriendly) {
                if (attackTimer <= funcTick.get(0)) attackTimer++;
            }
        }
    }

    private void addHappyVillagerParticle(double x,double y,double z){
        if(level instanceof ServerWorld) ((ServerWorld)level).sendParticles(ParticleTypes.HAPPY_VILLAGER,x,y,z,32,2,2,2,1);
    }

    private void pauBlockChange(int size){
        if(setMode == mode.BSMP && level!= null && size > 0) {
            Random r = new Random();
            int i = r.nextInt(damage);
            if(i <= size)
                level.setBlock(worldPosition.below(), Blocks.GOLD_BLOCK.defaultBlockState(),11);
        }
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        workingRange = nbt.getInt("workingRange");
        damage = nbt.getInt("damage");
        funcTick.set(0,nbt.getInt("attackTick"));
        funcTick.set(1,nbt.getInt("treatTick"));
        funcValue.set(0,nbt.getFloat("attackValue"));
        funcValue.set(1,nbt.getFloat("treatValue"));
        activation = nbt.getBoolean("activation");
        attackNeutrality = nbt.getBoolean("attackNeutrality");
        attackUnfriendly = nbt.getBoolean("attackUnfriendly");
        treatFriendly = nbt.getBoolean("treatFriendly");
        canBeHurt = nbt.getBoolean("canBeHurt");
        belongUs = nbt.getBoolean("belongUs");
        belonging = nbt.getBoolean("belonging");
        campBelong = CampHelper.loadCampDataFromNBT("belong",nbt);
        campHate = CampHelper.loadCampDataFromNBT("hate",nbt);
//        System.out.println(nbt);
        super.load(state, nbt);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.putInt("workingRange", workingRange);
        nbt.putInt("damage", damage);
        nbt.putInt("attackTick", funcTick.get(0));
        nbt.putInt("treatTick", funcTick.get(1));
        nbt.putFloat("attackValue", funcValue.get(0));
        nbt.putFloat("treatValue", funcValue.get(1));
        nbt.putBoolean("activation", activation);
        nbt.putBoolean("attackNeutrality", attackNeutrality);
        nbt.putBoolean("attackUnfriendly", attackUnfriendly);
        nbt.putBoolean("treatFriendly", treatFriendly);
        nbt.putBoolean("canBeHurt", canBeHurt);
        nbt.putBoolean("belongUs", belongUs);
        nbt.putBoolean("belonging", belonging);
        CampHelper.saveCampDataToNBT("belong", nbt, campBelong);
        CampHelper.saveCampDataToNBT("hate", nbt, campHate);
//        System.out.println(nbt);
        return super.save(nbt);
    }
}