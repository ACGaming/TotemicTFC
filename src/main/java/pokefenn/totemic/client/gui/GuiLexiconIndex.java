/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Totemic Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 */
package pokefenn.totemic.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.input.Mouse;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

import pokefenn.totemic.api.lexicon.LexiconCategory;
import pokefenn.totemic.api.lexicon.LexiconEntry;
import pokefenn.totemic.client.gui.button.GuiButtonBack;
import pokefenn.totemic.client.gui.button.GuiButtonInvisible;
import pokefenn.totemic.client.gui.button.GuiButtonPage;

public class GuiLexiconIndex extends GuiLexicon implements IParented
{

    LexiconCategory category;
    String title;
    int page = 0;

    GuiButton leftButton, rightButton, backButton;
    GuiLexicon parent;

    List<LexiconEntry> entriesToDisplay = new ArrayList<>();
    int fx = 0;
    boolean swiped = false;

    public GuiLexiconIndex(LexiconCategory category)
    {
        this.category = category;
        title = I18n.format(category.getUnlocalizedName());
        parent = new GuiLexicon();
    }

    public void updatePageButtons()
    {
        leftButton.enabled = page != 0;
        rightButton.enabled = page < (entriesToDisplay.size() - 1) / 12;
    }

    @Override
    public void setParent(GuiLexicon gui)
    {
        parent = gui;
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id >= BOOKMARK_START)
            handleBookmark(par1GuiButton);
        else
            switch (par1GuiButton.id)
            {
                case 12:
                    mc.displayGuiScreen(parent);
                    break;
                case 13:
                    page--;
                    updatePageButtons();
                    populateIndex();
                    break;
                case 14:
                    page++;
                    updatePageButtons();
                    populateIndex();
                    break;
                default:
                    int index = par1GuiButton.id + page * 12;
                    if (index >= entriesToDisplay.size())
                        return;

                    LexiconEntry entry = entriesToDisplay.get(index);
                    mc.displayGuiScreen(new GuiLexiconEntry(entry, this));
            }
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.add(backButton = new GuiButtonBack(12, left + guiWidth / 2 - 8, top + guiHeight + 2));
        buttonList.add(leftButton = new GuiButtonPage(13, left, top + guiHeight - 10, false));
        buttonList.add(rightButton = new GuiButtonPage(14, left + guiWidth - 18, top + guiHeight - 10, true));

        entriesToDisplay.clear();
        //ILexicon lex = (ILexicon) stackUsed.getItem();
        for (LexiconEntry entry : category.getEntries())
        {
            entriesToDisplay.add(entry);
        }
        Collections.sort(entriesToDisplay);

        updatePageButtons();
        populateIndex();
    }

    @Override
    void drawHeader()
    {
        // NO-OP
    }

    @Override
    String getTitle()
    {
        return title;
    }

    @SuppressWarnings("deprecation")
    @Override
    void populateIndex()
    {
        for (int i = page * 12; i < (page + 1) * 12; i++)
        {
            GuiButtonInvisible button = (GuiButtonInvisible) buttonList.get(i - page * 12);
            LexiconEntry entry = i >= entriesToDisplay.size() ? null : entriesToDisplay.get(i);
            if (entry != null)
                button.displayString = "" + (entry.isPriority() ? TextFormatting.ITALIC : "") + I18n.format(entry.getUnlocalizedName());
            else
                button.displayString = "";
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) throws IOException
    {
        if (par2 == 203 || par2 == 200 || par2 == 201) // Left, Up, Page Up
            prevPage();
        else if (par2 == 205 || par2 == 208 || par2 == 209) // Right, Down Page Down
            nextPage();
        else if (par2 == 14) // Backspace
            back();
        else if (par2 == 199)
        { // Home
            mc.displayGuiScreen(new GuiLexicon());
        }

        super.keyTyped(par1, par2);
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) throws IOException
    {
        super.mouseClicked(par1, par2, par3);

        fx = par1;
        if (par3 == 1)
            back();
    }

    @Override
    protected void mouseClickMove(int x, int y, int button, long time)
    {
        if (button == 0 && Math.abs(x - fx) > 100 && mc.gameSettings.touchscreen && !swiped)
        {
            double swipe = (x - fx) / Math.max(1, (double) time);
            if (swipe < 0.5)
            {
                nextPage();
                swiped = true;
            }
            else if (swipe > 0.5)
            {
                prevPage();
                swiped = true;
            }
        }
    }

    @Override
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();

        if (Mouse.getEventButton() == 0)
            swiped = false;

        int w = Mouse.getEventDWheel();
        if (w < 0)
            nextPage();
        else if (w > 0)
            prevPage();
    }

    void back()
    {
        if (backButton.enabled)
            actionPerformed(backButton);
    }

    void nextPage()
    {
        if (rightButton.enabled)
            actionPerformed(rightButton);
    }

    void prevPage()
    {
        if (leftButton.enabled)
            actionPerformed(leftButton);
    }
}
