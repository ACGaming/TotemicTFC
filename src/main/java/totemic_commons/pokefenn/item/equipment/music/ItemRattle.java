package totemic_commons.pokefenn.item.equipment.music;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.network.client.PacketRattleSound;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.util.EntityUtil;
import totemic_commons.pokefenn.util.TotemUtil;

import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemRattle extends ItemMusic
{
    public int time;

    //This int will hold the amount of nearby poles, which are sticks and buffalo heads. Each pole will increase the music made.
    public int headPoles;

    public ItemRattle()
    {
        super(Strings.CEREMONY_RATTLE_NAME, HandlerInitiation.flute);
        time = 0;
        headPoles = 0;
    }

    /*@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add(StatCollector.translateToLocal("totemic.tooltip.rattle"));

        super.addInformation(stack, player, list, par4);
    }*/

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack itemStack)
    {
        World world = entityLiving.worldObj;
        int x = (int) entityLiving.posX;
        int y = (int) entityLiving.posY;
        int z = (int) entityLiving.posZ;

        if(!world.isRemote)
        {
            if(entityLiving instanceof EntityPlayer && !(entityLiving instanceof FakePlayer))
            {
                EntityPlayer player = (EntityPlayer) entityLiving;
                MovingObjectPosition block = EntityUtil.raytraceFromEntity(world, player, true, 5);
                if(block == null)
                {
                    time++;
                    if(time >= 4 && !player.isSneaking())
                    {
                        time = 0;
                        TotemUtil.playMusicFromItem(world, (int) player.posX, (int) player.posY, (int) player.posZ, HandlerInitiation.rattle, 0, 0);
                        particlesAllAround(world, player.posX, player.posY, player.posZ, false);
                        PacketHandler.sendAround(new PacketRattleSound(x, y, z), ((EntityPlayer) entityLiving).worldObj.provider.dimensionId, x, y, z);
                        return false;
                    }
                    if(time >= 4 && player.isSneaking())
                    {
                        time = 0;
                        TotemUtil.playMusicFromItemForCeremonySelector(player, (int) player.posX, (int) player.posY, (int) player.posZ, musicHandler, 0);
                        particlesAllAround(world, player.posX, player.posY, player.posZ, true);
                        PacketHandler.sendAround(new PacketRattleSound(x, y, z), ((EntityPlayer) entityLiving).worldObj.provider.dimensionId, x, y, z);
                    }
                }
            }
        }

        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if(!player.isSwingInProgress)
            player.swingItem();
        return stack;
    }

    public void particlesAllAround(World world, double x, double y, double z, boolean firework)
    {
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", x + 0.5D, y + 1.2D, z + 0.5D, 2, 0.5D, 0.0D, 0.5D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", x, y + 1.2D, z, 2, 0.0D, 0.0D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", x + 0.5D, y + 1.2D, z, 2, 0.0D, 0.0D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", x, y + 1.2D, z + 0.5D, 2, 0.0D, 0.0D, 0.0D, 0.0D);

        if(firework)
        {
            MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("fireworksSpark", x + 0.5D, y + 1.2D, z + 0.5D, 2, 0.5D, 0.0D, 0.5D, 0.0D);
            MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("fireworksSpark", x, y + 1.2D, z, 2, 0.0D, 0.0D, 0.0D, 0.0D);
            MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("fireworksSpark", x + 0.5D, y + 1.2D, z, 2, 0.0D, 0.0D, 0.0D, 0.0D);
            MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("fireworksSpark", x, y + 1.2D, z + 0.5D, 2, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    /*
    @Override
    public int getMaximumMusic(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 110;
    }

    @Override
    public int getMusicOutput(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 9 + (headPoles * 2);
    }

    @Override
    public int getRange(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 8;
    }
    */
}
