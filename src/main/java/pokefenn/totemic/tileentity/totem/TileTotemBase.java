package pokefenn.totemic.tileentity.totem;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.math.IntMath;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import pokefenn.totemic.api.TotemicCapabilities;
import pokefenn.totemic.api.totem.TotemBase;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.handler.GameOverlay;
import pokefenn.totemic.lib.WoodVariant;
import pokefenn.totemic.tileentity.TileTotemic;

public class TileTotemBase extends TileTotemic implements TotemBase, ITickable
{
    private final List<TotemEffect> totemEffectList = new ArrayList<>(MAX_POLE_SIZE);
    private final Multiset<TotemEffect> totemEffects = HashMultiset.create(MAX_POLE_SIZE);
    private boolean firstTick = true;
    private WoodVariant woodType = WoodVariant.OAK;
    private TotemState state = new StateTotemEffect(this);
    private int commonTotemEffectInterval = Integer.MAX_VALUE;

    @Override
    public void update()
    {
        if (firstTick)
        {
            calculateTotemEffects();
            firstTick = false;
        }

        state.update();
    }

    public void onPoleChange()
    {
        calculateTotemEffects();
    }

    public TotemState getState()
    {
        return state;
    }

    void setState(TotemState state)
    {
        if (state != this.state)
        {
            this.state = state;
            if (world != null) //prevent NPE when called during loading
            {
                markForUpdate();
                markDirty();
            }
        }
    }

    public WoodVariant getWoodType()
    {
        return woodType;
    }

    public void setWoodType(WoodVariant woodType)
    {
        this.woodType = woodType;
    }

    public Multiset<TotemEffect> getTotemEffectSet()
    {
        return totemEffects;
    }

    public int getCommonTotemEffectInterval()
    {
        return commonTotemEffectInterval;
    }

    @Override
    public int getTotemEffectMusic()
    {
        if (state instanceof StateTotemEffect)
            return ((StateTotemEffect) state).getMusicAmount();
        else
            return 0;
    }

    @Override
    public int getPoleSize()
    {
        return totemEffectList.size();
    }

    @Override
    public int getRepetition(TotemEffect effect)
    {
        return totemEffects.count(effect);
    }

    public void resetState()
    {
        setState(new StateTotemEffect(this));
    }

    /*public boolean canSelect()
    {
        return state.canSelect();
    }

    public void addSelector(@Nullable Entity entity, MusicInstrument instr)
    {
        state.addSelector(entity, instr);
    }*/

    @SideOnly(Side.CLIENT)
    public void setCeremonyOverlay()
    {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (getDistanceSq(player.posX, player.posY, player.posZ) <= 8 * 8)
        {
            GameOverlay.setActiveTotem(this);
        }
        else
        {
            if (GameOverlay.getActiveTotem() == this)
                GameOverlay.setActiveTotem(null);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        woodType = WoodVariant.fromID(tag.getByte("wood"));
        if (tag.hasKey("state", 99))
            state = TotemState.fromID(tag.getByte("state"), this);
        else
            state = new StateTotemEffect(this);

        state.readFromNBT(tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        tag = super.writeToNBT(tag);

        tag.setByte("wood", (byte) woodType.getID());
        tag.setByte("state", (byte) state.getID());
        state.writeToNBT(tag);

        return tag;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.getNbtCompound());
    }

    //Prevent unloaded totem bases on the client side to stick around in the Ceremony HUD,
    //see issue #77
    @Override
    public void onChunkUnload()
    {
        invalidate();
    }

    @Override
    public boolean hasCapability(Capability<?> cap, @Nullable EnumFacing facing)
    {
        return cap == TotemicCapabilities.MUSIC_ACCEPTOR || super.hasCapability(cap, facing);
    }

    @Override
    @Nullable
    public <T> T getCapability(Capability<T> cap, @Nullable EnumFacing facing)
    {
        if (cap == TotemicCapabilities.MUSIC_ACCEPTOR)
            return TotemicCapabilities.MUSIC_ACCEPTOR.cast(getState());
        else
            return super.getCapability(cap, facing);
    }

    private void calculateTotemEffects()
    {
        totemEffectList.clear();
        totemEffects.clear();

        for (int i = 0; i < MAX_POLE_SIZE; i++)
        {
            TileEntity tile = world.getTileEntity(pos.up(i + 1));
            if (tile instanceof TileTotemPole)
            {
                TotemEffect effect = ((TileTotemPole) tile).getEffect();
                totemEffectList.add(effect);
                if (effect != null)
                    totemEffects.add(effect);
            }
            else
                break;
        }

        //Calculate the greatest common divisor of all the intervals of the effects
        commonTotemEffectInterval = totemEffects.elementSet().stream()
            .mapToInt(TotemEffect::getInterval)
            .reduce(IntMath::gcd)
            .orElse(Integer.MAX_VALUE);
    }
}