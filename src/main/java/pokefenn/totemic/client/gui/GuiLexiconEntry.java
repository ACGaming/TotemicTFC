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

import org.lwjgl.input.Mouse;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

import pokefenn.totemic.api.internal.IGuiLexiconEntry;
import pokefenn.totemic.api.lexicon.IAddonEntry;
import pokefenn.totemic.api.lexicon.LexiconEntry;
import pokefenn.totemic.api.lexicon.LexiconPage;
import pokefenn.totemic.client.gui.button.GuiButtonBackWithShift;
import pokefenn.totemic.client.gui.button.GuiButtonPage;

public class GuiLexiconEntry extends GuiLexicon implements IGuiLexiconEntry, IParented
{

    public int page = 0;
    LexiconEntry entry;
    GuiScreen parent;
    String title;
    String subtitle;

    GuiButton leftButton, rightButton, backButton;
    int fx = 0;
    boolean swiped = false;

    public GuiLexiconEntry(LexiconEntry entry, GuiScreen parent)
    {
        this.entry = entry;
        this.parent = parent;

        title = I18n.format(entry.getUnlocalizedName());
        if (entry instanceof IAddonEntry)
            subtitle = I18n.format(((IAddonEntry) entry).getSubtitle());
        else
            subtitle = null;
    }

    @Override
    public LexiconEntry getEntry()
    {
        return entry;
    }

    @Override
    public int getPageOn()
    {
        return page;
    }

    @Override
    public int getLeft()
    {
        return left;
    }

    @Override
    public int getTop()
    {
        return top;
    }

    @Override
    public int getWidth()
    {
        return guiWidth;
    }

    @Override
    public int getHeight()
    {
        return guiHeight;
    }

    @Override
    public float getZLevel()
    {
        return zLevel;
    }

    public void updatePageButtons()
    {
        leftButton.enabled = page != 0;
        rightButton.enabled = page + 1 < entry.getPages().size();
    }

    @Override
    public void drawScreen(int par1, int par2, float par3)
    {
        super.drawScreen(par1, par2, par3);

        LexiconPage page = entry.getPages().get(this.page);
        page.renderScreen(this, par1, par2);
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id >= BOOKMARK_START)
            handleBookmark(par1GuiButton);
        else
            switch (par1GuiButton.id)
            {
                case 0:
                    mc.displayGuiScreen(GuiScreen.isShiftKeyDown() ? new GuiLexicon() : parent);
                    break;
                case 1:
                    page--;
                    break;
                case 2:
                    page++;
                    break;
            }
        updatePageButtons();
    }

    @Override
    public void initGui()
    {
        super.initGui();

        buttonList.add(backButton = new GuiButtonBackWithShift(0, left + guiWidth / 2 - 8, top + guiHeight + 2));
        buttonList.add(leftButton = new GuiButtonPage(1, left, top + guiHeight - 10, false));
        buttonList.add(rightButton = new GuiButtonPage(2, left + guiWidth - 18, top + guiHeight - 10, true));

        updatePageButtons();
    }

    @Override
    void drawHeader()
    {
        // NO-OP
    }

    @Override
    String getTitle()
    {
        return String.format("%s " + TextFormatting.ITALIC + "(%s/%s)", title, page + 1, entry.getPages().size());
    }

    @Override
    String getSubtitle()
    {
        return subtitle;
    }

    @Override
    boolean isIndex()
    {
        return false;
    }

    @Override
    public void setParent(GuiLexicon gui)
    {
        parent = gui;
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

    @Override
    public void updateScreen()
    {
        LexiconPage page = entry.getPages().get(this.page);
        page.updateScreen();
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
