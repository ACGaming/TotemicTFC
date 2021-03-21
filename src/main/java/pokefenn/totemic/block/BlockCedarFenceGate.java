package pokefenn.totemic.block;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import pokefenn.totemic.lib.Strings;

public class BlockCedarFenceGate extends BlockFenceGate
{
    public BlockCedarFenceGate()
    {
        super(BlockPlanks.EnumType.OAK);
        setRegistryName(Strings.CEDAR_FENCE_GATE_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.CEDAR_FENCE_GATE_NAME);
        setHardness(2F);
        setResistance(5F);
        setSoundType(SoundType.WOOD);
        setCreativeTab(CreativeTabs.DECORATIONS);
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return MapColor.PINK;
    }
}
