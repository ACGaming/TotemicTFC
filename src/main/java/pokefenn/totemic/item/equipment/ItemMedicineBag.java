package pokefenn.totemic.item.equipment;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.item.ItemTotemic;
import pokefenn.totemic.lib.Strings;
import pokefenn.totemic.tileentity.totem.StateTotemEffect;
import pokefenn.totemic.tileentity.totem.TileTotemBase;
import pokefenn.totemic.tileentity.totem.TileTotemPole;
import pokefenn.totemic.util.EntityUtil;
import pokefenn.totemic.util.ItemUtil;

public class ItemMedicineBag extends ItemTotemic
{
    public static final String MED_BAG_TOTEM_KEY = "totem";
    public static final String MED_BAG_CHARGE_KEY = "charge";

    public static Optional<TotemEffect> getEffect(ItemStack stack)
    {
        return Optional.ofNullable(stack.getTagCompound())
            .map(tag -> TotemicRegistries.totemEffects().getValue(new ResourceLocation(tag.getString(MED_BAG_TOTEM_KEY))));
    }

    public static int getCharge(ItemStack stack)
    {
        if (stack.hasTagCompound())
            return stack.getTagCompound().getInteger(MED_BAG_CHARGE_KEY);
        else
            return 0;
    }

    public static int getMaxCharge(ItemStack stack)
    {
        int unbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
        return (4 + 2 * unbreaking) * 60 * 20;
    }

    public ItemMedicineBag()
    {
        super(Strings.MEDICINE_BAG_NAME);
        setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!player.isSneaking())
        {
            ItemStack copyStack = player.isCreative() ? stack.copy() : stack; //Workaround for creative mode, otherwise Minecraft will reset the item damage
            ActionResult<ItemStack> result = openOrClose(copyStack);
            if (result.getType() == EnumActionResult.SUCCESS)
                player.setHeldItem(hand, result.getResult());
            return result.getType();
        }
        else
            return trySetEffect(stack, player, world, pos, hand);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        return openOrClose(player.getHeldItem(hand));
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (!world.isRemote && world.getTotalWorldTime() % 20 == 0)
            tryCharge(stack, world, entity.getPosition());

        if (stack.getMetadata() != 0)
        {
            int charge = getCharge(stack);
            if (charge != 0)
            {
                getEffect(stack).ifPresent(eff -> {
                    if (world.getTotalWorldTime() % eff.getInterval() == 0)
                    {
                        eff.medicineBagEffect(world, (EntityPlayer) entity, stack, charge);
                        if (!world.isRemote && charge != -1)
                            stack.getTagCompound().setInteger(MED_BAG_CHARGE_KEY, Math.max(charge - eff.getInterval(), 0));
                    }
                });
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        String key;
        if (getEffect(stack).isPresent())
        {
            if (getCharge(stack) != 0)
                key = (stack.getMetadata() == 0) ? "tooltipClosed" : "tooltipOpen";
            else
                key = "tooltipEmpty";
        }
        else
            key = "tooltip";
        tooltip.add(I18n.format(getUnlocalizedName() + "." + key));

        if (flag.isAdvanced())
            tooltip.add(I18n.format(getUnlocalizedName() + ".tooltipCharge", getCharge(stack), getMaxCharge(stack)));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack)
    {
        String key = (getCharge(stack) != -1) ? getUnlocalizedName() : (getUnlocalizedName() + ".creative");
        return getEffect(stack)
            .map(eff -> I18n.format(key + ".display", I18n.format(eff.getUnlocalizedName())))
            .orElseGet(() -> I18n.format(key + ".name"));
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return true;
    }

    @Override
    public int getItemEnchantability()
    {
        return 8;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
        if (isInCreativeTab(tab))
        {
            subItems.add(new ItemStack(this));
            ItemStack stack = new ItemStack(this);
            stack.setTagInfo(MED_BAG_CHARGE_KEY, new NBTTagInt(-1));
            subItems.add(stack);
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return getCharge(stack) != -1;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return 1.0 - Math.min((double) getCharge(stack) / getMaxCharge(stack), 1.0);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack)
    {
        return MathHelper.hsvToRGB(Math.min((float) getCharge(stack) / getMaxCharge(stack), 1.0F) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        return enchantment == Enchantments.EFFICIENCY || enchantment == Enchantments.UNBREAKING || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return slotChanged || !ItemStack.areItemsEqual(oldStack, newStack);
    }

    @Override
    public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack)
    {
        return !ItemStack.areItemsEqual(oldStack, newStack);
    }

    private void tryCharge(ItemStack stack, World world, BlockPos pos)
    {
        int charge = getCharge(stack);
        int maxCharge = getMaxCharge(stack);
        if (charge < maxCharge && charge != -1)
        {
            getEffect(stack).ifPresent(effect -> {
                if (EntityUtil.getTileEntitiesInRange(TileTotemBase.class, world, pos, 6, 6).stream()
                    .anyMatch(tile -> tile.getState() instanceof StateTotemEffect && tile.getTotemEffectSet().contains(effect)))
                {
                    stack.getTagCompound().setInteger(MED_BAG_CHARGE_KEY, Math.min(charge + maxCharge / 12, maxCharge));
                }
            });
        }
    }

    private ActionResult<ItemStack> openOrClose(ItemStack stack)
    {
        if (getEffect(stack).isPresent())
        {
            stack.setItemDamage((stack.getMetadata() == 0) ? 1 : 0);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        else
            return new ActionResult<>(EnumActionResult.FAIL, stack);
    }

    private EnumActionResult trySetEffect(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand)
    {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileTotemPole)
        {
            TotemEffect effect = ((TileTotemPole) tile).getEffect();
            if (effect != null)
            {
                if (!effect.isPortable())
                {
                    if (world.isRemote)
                        player.sendStatusMessage(new TextComponentTranslation("totemicmisc.effectNotPortable", new TextComponentTranslation(effect.getUnlocalizedName())), true);
                    return EnumActionResult.FAIL;
                }

                ItemStack newStack = stack.copy();
                NBTTagCompound tag = ItemUtil.getOrCreateTag(newStack);
                tag.setString(MED_BAG_TOTEM_KEY, effect.getRegistryName().toString());
                if (tag.getInteger(MED_BAG_CHARGE_KEY) != -1)
                    tag.setInteger(MED_BAG_CHARGE_KEY, 0);
                player.setHeldItem(hand, newStack);
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }
}