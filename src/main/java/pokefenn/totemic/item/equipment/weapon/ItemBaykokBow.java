package pokefenn.totemic.item.equipment.weapon;

import java.lang.reflect.Method;

import org.apache.logging.log4j.Level;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import pokefenn.totemic.Totemic;
import pokefenn.totemic.entity.projectile.EntityInvisArrow;
import pokefenn.totemic.lib.Strings;

import static pokefenn.totemic.Totemic.logger;

public class ItemBaykokBow extends ItemBow
{
    private static final Method findAmmoMethod = ReflectionHelper.findMethod(ItemBow.class, "findAmmo", "func_185060_a", EntityPlayer.class);

    public ItemBaykokBow()
    {
        setRegistryName(Strings.BAYKOK_BOW_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.BAYKOK_BOW_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setMaxDamage(576);
        addPropertyOverride(new ResourceLocation("pull"), (stack, world, entity) -> {
            if (entity != null && entity.getActiveItemStack().getItem() == this)
                return (stack.getMaxItemUseDuration() - entity.getItemInUseCount()) / 20.0F;
            else
                return 0.0F;
        });
        addPropertyOverride(new ResourceLocation("pulling"), (stack, world, entity) ->
            (entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack) ? 1.0F : 0.0F);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int timeLeft)
    {
        if (!(entity instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) entity;
        boolean infinity = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
        ItemStack arrow = findAmmo0(player);

        int chargeTicks = this.getMaxItemUseDuration(stack) - timeLeft;
        chargeTicks = ForgeEventFactory.onArrowLoose(stack, world, player, chargeTicks, !arrow.isEmpty() || infinity);
        if (chargeTicks < 0)
            return;

        if (!arrow.isEmpty() || infinity)
        {
            if (arrow.isEmpty())
                arrow = new ItemStack(Items.ARROW);

            float charge = getArrowVelocity(chargeTicks);

            if (charge >= 0.1)
            {
                boolean flag1 = infinity && arrow.getItem() instanceof ItemArrow; //Forge: Fix consuming custom arrows.

                if (!world.isRemote)
                {
                    ItemArrow itemarrow = ((ItemArrow) (arrow.getItem() instanceof ItemArrow ? arrow.getItem() : Items.ARROW));
                    EntityArrow entityarrow;

                    if (itemarrow == Items.ARROW) //Mundane arrows will become invisible
                    {
                        entityarrow = new EntityInvisArrow(world, player);
                    }
                    else
                    {
                        entityarrow = itemarrow.createArrow(world, arrow, entity);
                    }

                    if (entityarrow.getDamage() < 2.5)
                        entityarrow.setDamage(2.5);

                    entityarrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, charge * 4.0F, 1.0F);

                    if (charge == 1.0F)
                        entityarrow.setIsCritical(true);

                    int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                    if (power > 0)
                        entityarrow.setDamage(entityarrow.getDamage() + power * 0.5D + 0.5D);

                    int punch = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                    if (punch > 0)
                        entityarrow.setKnockbackStrength(punch);

                    if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
                        entityarrow.setFire(100);

                    stack.damageItem(1, player);

                    if (flag1)
                        entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;

                    world.spawnEntity(entityarrow);
                }

                world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);

                if (!flag1)
                {
                    arrow.shrink(1);

                    if (arrow.isEmpty())
                        player.inventory.deleteStack(arrow);
                }

                player.addStat(StatList.getObjectUseStats(this));
            }
        }
    }

    @Override
    public int getItemEnchantability()
    {
        return 5;
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.RARE;
    }

    //The name of this method must not be "findAmmo", otherwise if there is some Access Transformer
    //that makes ItemBow.findAmmo protected or public, it will cause an infinite recursion
    protected ItemStack findAmmo0(EntityPlayer player)
    {
        try
        {
            return (ItemStack) findAmmoMethod.invoke(this, player);
        }
        catch (ReflectiveOperationException e)
        {
            logger.catching(Level.ERROR, e);
            return ItemStack.EMPTY;
        }
    }
}