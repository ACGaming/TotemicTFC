package pokefenn.totemic.init;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.BlockCedarStripped;
import pokefenn.totemic.block.BlockDecoPillar;
import pokefenn.totemic.block.BlockDecoPillarBase;
import pokefenn.totemic.block.BlockTotemTorch;
import pokefenn.totemic.block.music.BlockDrum;
import pokefenn.totemic.block.music.BlockWindChime;
import pokefenn.totemic.block.tipi.BlockDummyTipi;
import pokefenn.totemic.block.tipi.BlockTipi;
import pokefenn.totemic.block.totem.BlockTotemBase;
import pokefenn.totemic.block.totem.BlockTotemPole;

@EventBusSubscriber(modid = Totemic.MOD_ID)
@ObjectHolder(Totemic.MOD_ID)
public final class ModBlocks
{
    public static final BlockCedarStripped stripped_cedar_log = null;
    public static final BlockTotemBase totem_base = null;
    public static final BlockTotemPole totem_pole = null;
    public static final BlockTotemTorch totem_torch = null;
    public static final BlockDrum drum = null;
    public static final BlockWindChime wind_chime = null;
    public static final BlockTipi tipi = null;
    public static final BlockDummyTipi dummy_tipi = null;
    public static final BlockDecoPillar wooden_pillar = null;
    public static final BlockDecoPillarBase wooden_pillar_base = null;

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(
            new BlockCedarStripped(),
            new BlockTotemBase(),
            new BlockTotemPole(),
            new BlockTotemTorch(),
            new BlockDrum(),
            new BlockWindChime(),
            new BlockTipi(),
            new BlockDummyTipi(),
            new BlockDecoPillar(),
            new BlockDecoPillarBase());
    }
}