package pokefenn.totemic.potion;

import net.minecraft.potion.Potion;

public class PotionBat extends Potion
{
    public PotionBat()
    {
        super(false, 0xF2F2F0);
        setRegistryName("bat");
        setPotionName("totemic.potion.bat");
        setIconIndex(0, 0);
    }
}