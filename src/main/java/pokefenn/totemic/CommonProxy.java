package pokefenn.totemic;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import pokefenn.totemic.datafix.VanillaIronNugget;
import pokefenn.totemic.entity.ModEntities;
import pokefenn.totemic.handler.EntityFall;
import pokefenn.totemic.handler.EntitySpawn;
import pokefenn.totemic.handler.EntityUpdate;
import pokefenn.totemic.handler.PlayerInteract;
import pokefenn.totemic.item.ItemBuffaloDrops;
import pokefenn.totemic.item.ItemTotemicItems;
import pokefenn.totemic.lib.Strings;
import pokefenn.totemic.network.GuiHandler;
import pokefenn.totemic.network.NetworkHandler;
import pokefenn.totemic.tileentity.TileTipi;
import pokefenn.totemic.tileentity.music.TileDrum;
import pokefenn.totemic.tileentity.music.TileWindChime;
import pokefenn.totemic.tileentity.totem.TileTotemBase;
import pokefenn.totemic.tileentity.totem.TileTotemPole;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        ModEntities.init();
        registerTileEntities();
    }

    public void init(FMLInitializationEvent event)
    {
        ModContent.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(Totemic.instance, new GuiHandler());
        NetworkHandler.init();
        oreDictionary();
        furnaceRecipes();
        registerEventHandlers();
        registerDataFixers();
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

    private void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileTotemBase.class, Strings.RESOURCE_PREFIX + Strings.TOTEM_BASE_NAME);
        GameRegistry.registerTileEntity(TileTotemPole.class, Strings.RESOURCE_PREFIX + Strings.TOTEM_POLE_NAME);
        GameRegistry.registerTileEntity(TileDrum.class, Strings.RESOURCE_PREFIX + Strings.DRUM_NAME);
        GameRegistry.registerTileEntity(TileWindChime.class, Strings.RESOURCE_PREFIX + Strings.WIND_CHIME_NAME);
        GameRegistry.registerTileEntity(TileTipi.class, Strings.RESOURCE_PREFIX + Strings.TIPI_NAME);
    }

    private void oreDictionary()
    {
        OreDictionary.registerOre("treeLeaves", new ItemStack(ModBlocks.cedar_leaves, 1));
        OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.cedar_log, 1, 0));
        OreDictionary.registerOre("plankWood", new ItemStack(ModBlocks.cedar_plank, 1, 0));
        OreDictionary.registerOre("bellsIron", new ItemStack(ModItems.sub_items, 1, ItemTotemicItems.Type.iron_bells.ordinal()));
        OreDictionary.registerOre("listAllmeatraw", new ItemStack(ModItems.buffalo_meat));
        OreDictionary.registerOre("listAllbeefraw", new ItemStack(ModItems.buffalo_meat));
        OreDictionary.registerOre("listAllbuffaloraw", new ItemStack(ModItems.buffalo_meat));
        OreDictionary.registerOre("listAllmeatcooked", new ItemStack(ModItems.cooked_buffalo_meat));
        OreDictionary.registerOre("listAllbeefcooked", new ItemStack(ModItems.cooked_buffalo_meat));
        OreDictionary.registerOre("listAllbuffalocooked", new ItemStack(ModItems.cooked_buffalo_meat));
        OreDictionary.registerOre("hideBuffalo", new ItemStack(ModItems.buffalo_items, 1, ItemBuffaloDrops.Type.hide.ordinal()));
        OreDictionary.registerOre("teethBuffalo", new ItemStack(ModItems.buffalo_items, 1, ItemBuffaloDrops.Type.teeth.ordinal()));
    }

    private void furnaceRecipes()
    {
        GameRegistry.addSmelting(ModBlocks.stripped_cedar_log, new ItemStack(Items.COAL, 1, 1), 0.5F);
        GameRegistry.addSmelting(ModBlocks.cedar_log, new ItemStack(Items.COAL, 1, 1), 0.5F);
        GameRegistry.addSmelting(ModItems.buffalo_meat, new ItemStack(ModItems.cooked_buffalo_meat), 0.35F);
    }

    private void registerDataFixers()
    {
        ModFixs fixes = FMLCommonHandler.instance().getDataFixer().init(Totemic.MOD_ID, 1000);
        fixes.registerFix(FixTypes.ITEM_INSTANCE, new VanillaIronNugget());
    }

    protected void registerEventHandlers()
    {
        MinecraftForge.EVENT_BUS.register(new EntityUpdate());
        MinecraftForge.EVENT_BUS.register(new EntityFall());
        MinecraftForge.EVENT_BUS.register(new PlayerInteract());
        MinecraftForge.EVENT_BUS.register(new EntitySpawn());
    }
}