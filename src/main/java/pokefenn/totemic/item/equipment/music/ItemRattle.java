package pokefenn.totemic.item.equipment.music;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.music.ItemInstrument;
import pokefenn.totemic.init.ModSounds;
import pokefenn.totemic.lib.Strings;

public class ItemRattle extends ItemInstrument
{
    public ItemRattle()
    {
        setSound(ModSounds.rattle);

        setRegistryName(Strings.RATTLE_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.RATTLE_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!player.isSwingInProgress)
            player.swingArm(hand);
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(I18n.format(getUnlocalizedName() + ".tooltip"));
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entity, ItemStack stack)
    {
        if (!entity.world.isRemote && !(entity instanceof EntityPlayer && ((EntityPlayer) entity).isSpectator()))
            useInstrument(stack, entity, 16);
        return false;
    }
}