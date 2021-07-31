package pokefenn.totemic;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.lib.Strings;

public class CreativeTabTotemic extends CreativeTabs
{
    public static ItemStack getEgg(String entityName)
    {
        ItemStack stack = new ItemStack(Items.SPAWN_EGG);
        NBTTagCompound entityTag = new NBTTagCompound();
        entityTag.setString("id", entityName);
        NBTTagCompound eggTag = new NBTTagCompound();
        eggTag.setTag("EntityTag", entityTag);
        stack.setTagCompound(eggTag);
        return stack;
    }

    public CreativeTabTotemic(String name)
    {
        super(name);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(ModBlocks.tipi);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void displayAllRelevantItems(NonNullList<ItemStack> list)
    {
        super.displayAllRelevantItems(list);

        list.add(getEgg(Strings.RESOURCE_PREFIX + Strings.BUFFALO_NAME));
        list.add(getEgg(Strings.RESOURCE_PREFIX + Strings.BALD_EAGLE_NAME));
        list.add(getEgg(Strings.RESOURCE_PREFIX + Strings.BAYKOK_NAME));
    }
}