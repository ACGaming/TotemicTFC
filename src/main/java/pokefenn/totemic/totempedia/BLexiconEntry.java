/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Totemic Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 14, 2014, 9:47:21 PM (GMT)]
 */
package pokefenn.totemic.totempedia;


import pokefenn.totemic.api.lexicon.LexiconCategory;
import pokefenn.totemic.api.lexicon.LexiconEntry;
import pokefenn.totemic.api.lexicon.LexiconPage;

public class BLexiconEntry extends LexiconEntry
{
    public BLexiconEntry(String unlocalizedName, LexiconCategory category)
    {
        super(unlocalizedName, category);
    }

    @Override
    public String getUnlocalizedName()
    {
        return "totemic.entry." + super.getUnlocalizedName();
    }

    @Override
    public LexiconEntry addPages(LexiconPage... pages)
    {
        for (LexiconPage page : pages)
            page.unlocalizedName = "totemic.page." + getLazyUnlocalizedName() + page.unlocalizedName;

        return super.addPages(pages);
    }

    public String getLazyUnlocalizedName()
    {
        return super.getUnlocalizedName();
    }
}