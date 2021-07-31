package pokefenn.totemic.entity.animal;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.Constants;
import net.dries007.tfc.objects.entity.animal.EntityAnimalTFC;
import net.dries007.tfc.objects.entity.animal.EntityCowTFC;
import net.dries007.tfc.util.calendar.CalendarTFC;
import pokefenn.totemic.lib.Resources;

public class EntityBuffalo extends EntityCowTFC
{
    public EntityBuffalo(World worldIn)
    {
        this(worldIn, Gender.valueOf(Constants.RNG.nextBoolean()), getRandomGrowth(ConfigTFC.Animals.COW.adulthood, ConfigTFC.Animals.COW.elder));
    }

    public EntityBuffalo(World worldIn, Gender gender, int birthDay)
    {
        super(worldIn, gender, birthDay);
        this.setSize(1.35F, 1.95F);
        this.setMilkedTick(0L);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity)
    {
        return 0;
    }

    @Override
    public void birthChildren()
    {
        int numberOfChildren = ConfigTFC.Animals.COW.babies;

        for (int i = 0; i < numberOfChildren; ++i)
        {
            EntityBuffalo baby = new EntityBuffalo(this.world, Gender.valueOf(Constants.RNG.nextBoolean()), (int) CalendarTFC.PLAYER_TIME.getTotalDays());
            baby.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
            baby.setFamiliarity(this.getFamiliarity() < 0.9F ? this.getFamiliarity() / 2.0F : this.getFamiliarity() * 0.9F);
            this.world.spawnEntity(baby);
        }
    }

    @Override
    protected void initEntityAI()
    {
        EntityAnimalTFC.addCommonLivestockAI(this, 1.2D);
        EntityAnimalTFC.addCommonPreyAI(this, 1.2D);
        this.tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
        targetTasks.addTask(1,
            new EntityAIHurtByTarget(this, false)
            {
                @Override
                protected boolean isSuitableTarget(@Nullable EntityLivingBase target, boolean includeInvincibles)
                {
                    return target instanceof AbstractSkeleton && super.isSuitableTarget(target, includeInvincibles);
                }
            });
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15);
        getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0);
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return Resources.LOOT_BUFFALO;
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    @Override
    protected float getSoundPitch()
    {
        return super.getSoundPitch() - 0.2F;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        boolean attacked = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        if (attacked)
        {
            if (entity instanceof EntityLivingBase)
                ((EntityLivingBase) entity).knockBack(this, 0.75F, Math.sin(Math.toRadians(rotationYaw)), -Math.cos(Math.toRadians(rotationYaw)));

            applyEnchantments(this, entity);
        }
        return attacked;
    }
}