package pokefenn.totemic.ceremony;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.music.MusicInstrument;

public class CeremonyDepths extends Ceremony
{
    public CeremonyDepths(String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, BlockPos pos, CeremonyEffectContext context)
    {
        if (world.isRemote)
            return;

        TotemicEntityUtil.getPlayersInRange(world, pos, 8, 8).forEach(entity ->
            entity.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 20 * (60 * 3), 1)));
    }
}
