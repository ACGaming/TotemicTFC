package pokefenn.totemic.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

import pokefenn.totemic.lib.Strings;

public class ItemBuffaloDrops extends ItemTotemic
{
    public ItemBuffaloDrops()
    {
        super("");
        setRegistryName(Strings.BUFFALO_ITEMS_NAME);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        int index = MathHelper.clamp(itemStack.getItemDamage(), 0, Type.values().length - 1);
        return "item." + Strings.RESOURCE_PREFIX + Type.values()[index].toString();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        if (isInCreativeTab(tab))
        {
            for (int meta = 0; meta < Type.values().length; ++meta)
                list.add(new ItemStack(this, 1, meta));
        }
    }

    public enum Type
    {
        hide, teeth/*, horn, hair, hoof, dung*/;

        private final String fullName = "buffalo_" + name();

        @Override
        public String toString()
        {
            return fullName;
        }
    }
}