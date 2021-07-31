package pokefenn.totemic.block;

import net.minecraft.block.BlockLog;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import pokefenn.totemic.Totemic;
import pokefenn.totemic.lib.Strings;

public class BlockCedarStripped extends BlockLog
{
    public BlockCedarStripped()
    {
        setRegistryName(Strings.STRIPPED_RED_CEDAR_LOG_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.STRIPPED_RED_CEDAR_LOG_NAME);
        setHardness(1.5F);
        setResistance(5F);
        setCreativeTab(Totemic.tabsTotem);
        setTickRandomly(true);
        setDefaultState(getDefaultState().withProperty(LOG_AXIS, EnumAxis.Y));
        Blocks.FIRE.setFireInfo(this, 5, 10);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return MapColor.PINK;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = getDefaultState();
        switch (meta)
        {
            case 0:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;
            case 4:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;
            case 8:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;
            default:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
        }
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = 0;
        switch (state.getValue(LOG_AXIS))
        {
            case Y:
                break;
            case X:
                meta |= 4;
                break;
            case Z:
                meta |= 8;
                break;
            case NONE:
                meta |= 12;
        }

        return meta;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, LOG_AXIS);
    }
}