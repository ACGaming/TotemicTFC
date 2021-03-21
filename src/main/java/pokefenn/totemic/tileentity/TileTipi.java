package pokefenn.totemic.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileTipi extends TileTotemic
{
    //public int colour = 0;
    //public boolean hasCatcher = false;

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        //colour = nbtTagCompound.getInteger("colour");
        //hasCatcher = nbtTagCompound.getBoolean("hasCatcher");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        tag = super.writeToNBT(tag);
        //tag.setInteger("colour", colour);
        //tag.setBoolean("hasCatcher", hasCatcher);
        return tag;
    }

    @Override
    public boolean canRenderBreaking()
    {
        return true; //This fixes the weird breaking animation caused by the oversized model
    }
}