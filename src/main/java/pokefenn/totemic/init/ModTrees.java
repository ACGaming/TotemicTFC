package pokefenn.totemic.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Tree;

import static net.dries007.tfc.types.DefaultTrees.GEN_TALL;
import static net.dries007.tfc.util.Helpers.getNull;
import static pokefenn.totemic.Totemic.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public final class ModTrees
{
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":red_cedar")
    public static final Tree RED_CEDAR = getNull();

    @SubscribeEvent
    public static void onPreRegisterTrees(TFCRegistryEvent.RegisterPreBlock<Tree> event)
    {
        event.getRegistry().registerAll(
            new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "red_cedar"), 10f, 240f, -8f, 17f, GEN_TALL).setHeight(16).setBurnInfo(625f, 1500).build()
        );
    }
}