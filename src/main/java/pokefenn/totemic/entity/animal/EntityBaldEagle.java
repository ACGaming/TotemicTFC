package pokefenn.totemic.entity.animal;

import javax.annotation.Nullable;

import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import net.dries007.tfc.Constants;
import net.dries007.tfc.objects.entity.animal.EntityAnimalTFC;
import net.dries007.tfc.objects.entity.animal.EntityParrotTFC;
import pokefenn.totemic.init.ModSounds;
import pokefenn.totemic.lib.Resources;

public class EntityBaldEagle extends EntityParrotTFC
{
    public EntityBaldEagle(World world)
    {
        this(world, Gender.valueOf(Constants.RNG.nextBoolean()), EntityAnimalTFC.getRandomGrowth(96, 0));
        this.setSize(0.6F, 1.0F);
    }

    public EntityBaldEagle(World world, Gender gender, int birthDay)
    {
        super(world);
        this.setSize(0.6F, 1.0F);
        this.setGender(gender);
        this.setBirthDay(birthDay);
        this.setFamiliarity(0.0F);
        this.setGrowingAge(0);
    }

    @Override
    @Nullable
    public SoundEvent getAmbientSound()
    {
        return ModSounds.baldEagleAmbient;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return ModSounds.baldEagleHurt;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound()
    {
        return ModSounds.baldEagleDeath;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return Resources.LOOT_BALD_EAGLE;
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity)
    {
        return 0;
    }
}