package pokefenn.totemic.ceremony;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import net.dries007.tfc.objects.entity.animal.EntityParrotTFC;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.entity.animal.EntityBaldEagle;
import pokefenn.totemic.util.EntityUtil;

public class CeremonyEagleDance extends Ceremony
{
    public CeremonyEagleDance(String name, int musicNeeded, int maxStartupTime, MusicInstrument... selectors)
    {
        super(name, musicNeeded, maxStartupTime, selectors);
    }

    @Override
    public void effect(World world, BlockPos pos, CeremonyEffectContext context)
    {
        if (world.isRemote)
            return;

        TotemicEntityUtil.getEntitiesInRange(EntityParrotTFC.class, world, pos, 8, 8)
            .limit(2)
            .forEach(parrot -> {
                EntityBaldEagle eagle = new EntityBaldEagle(world);
                EntityUtil.spawnEntity(world, parrot.posX, parrot.posY, parrot.posZ, eagle);
                if (parrot.getLeashed())
                    eagle.setLeashHolder(parrot.getLeashHolder(), true);
                parrot.setDead();
                ((WorldServer) world).spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, parrot.posX, parrot.posY + 1.0, parrot.posZ, 24, 0.6D, 0.5D, 0.6D, 1.0D);
            });
    }
}