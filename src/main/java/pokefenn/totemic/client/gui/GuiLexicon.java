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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.lexicon.LexiconCategory;
import pokefenn.totemic.client.gui.button.GuiButtonBookmark;
import pokefenn.totemic.client.gui.button.GuiButtonInvisible;

public class GuiLexicon extends GuiScreen
{

    public static final int BOOKMARK_START = 1337;
    public static final ResourceLocation texture = new ResourceLocation("totemic:textures/gui/totempedia.png");
    public static GuiLexicon currentOpenLexicon = new GuiLexicon();
    public static ItemStack stackUsed = ItemStack.EMPTY;
    public static List<GuiLexicon> bookmarks = new ArrayList<>();
    boolean bookmarksNeedPopulation = false;
    String title;
    int guiWidth = 146;
    int guiHeight = 180;
    int left, top;

    @Override
    public void drawScreen(int par1, int par2, float par3)
    {
        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(texture);
        drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);
        drawBookmark(left + guiWidth / 2, top - getTitleHeight(), getTitle(), true);
        String subtitle = getSubtitle();
        if (subtitle != null)
        {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 1F);
            drawCenteredString(fontRenderer, subtitle, left * 2 + guiWidth, (top - getTitleHeight() + 11) * 2, 0x00FF00);
            GlStateManager.popMatrix();
        }

        drawHeader();

        if (bookmarksNeedPopulation)
        {
            populateBookmarks();
            bookmarksNeedPopulation = false;
        }

        super.drawScreen(par1, par2, par3);
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id >= BOOKMARK_START)
            handleBookmark(par1GuiButton);
        else
        {
            int i = par1GuiButton.id - 3;
            if (i < 0)
                return;

            List<LexiconCategory> categoryList = TotemicAPI.get().lexicon().getCategories();
            LexiconCategory category = i >= categoryList.size() ? null : categoryList.get(i);

            if (category != null)
            {
                mc.displayGuiScreen(new GuiLexiconIndex(category));
            }
        }
    }

    @Override
    public void initGui()
    {
        super.initGui();

        title = stackUsed.getDisplayName();
        currentOpenLexicon = this;

        left = width / 2 - guiWidth / 2;
        top = height / 2 - guiHeight / 2;

        buttonList.clear();
        if (isIndex())
        {
            int x = 18;
            for (int i = 0; i < 12; i++)
            {
                int y = 16 + i * 12;
                buttonList.add(new GuiButtonInvisible(i, left + x, top + y, 110, 10, ""));
            }
            populateIndex();
        }
        populateBookmarks();
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    public void drawBookmark(int x, int y, String s, boolean drawLeft)
    {
        // This function is called from the buttons so I can't use fontRenderer
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        boolean unicode = font.getUnicodeFlag();
        font.setUnicodeFlag(true);
        int l = font.getStringWidth(s.trim());
        int fontOff = 0;

        if (!drawLeft)
        {
            x += l / 2;
            fontOff = 2;
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        GlStateManager.color(1F, 1F, 1F, 1F);
        drawTexturedModalRect(x + l / 2 + 3, y - 1, 54, 180, 6, 11);
        if (drawLeft)
            drawTexturedModalRect(x - l / 2 - 9, y - 1, 61, 180, 6, 11);
        for (int i = 0; i < l + 6; i++)
            drawTexturedModalRect(x - l / 2 - 3 + i, y - 1, 60, 180, 1, 11);

        font.drawString(s, x - l / 2 + fontOff, y, 0x111111, false);
        font.setUnicodeFlag(unicode);
    }

    public void handleBookmark(GuiButton par1GuiButton)
    {
        int i = par1GuiButton.id - BOOKMARK_START;
        if (i == bookmarks.size())
        {
            if (!bookmarks.contains(this))
                bookmarks.add(this);
        }
        else
        {
            if (isShiftKeyDown())
                bookmarks.remove(i);
            else
            {
                GuiLexicon bookmark = bookmarks.get(i);
                Minecraft.getMinecraft().displayGuiScreen(bookmark);
                if (!bookmark.getTitle().equals(getTitle()))
                {
                    if (bookmark instanceof IParented)
                        ((IParented) bookmark).setParent(this);
                }
            }
        }

        bookmarksNeedPopulation = true;
    }

    public int bookmarkWidth(String b)
    {
        boolean unicode = fontRenderer.getUnicodeFlag();
        fontRenderer.setUnicodeFlag(true);
        int width = fontRenderer.getStringWidth(b) + 15;
        fontRenderer.setUnicodeFlag(unicode);
        return width;
    }

    void drawHeader()
    {
        boolean unicode = fontRenderer.getUnicodeFlag();
        fontRenderer.setUnicodeFlag(true);
        fontRenderer.drawSplitString(I18n.format("totemic.gui.lexicon.header"), left + 18, top + 14, 110, 0);
        fontRenderer.setUnicodeFlag(unicode);
    }

    String getTitle()
    {
        return title;
    }

    String getSubtitle()
    {
        return null;
    }

    int getTitleHeight()
    {
        return getSubtitle() == null ? 12 : 16;
    }

    boolean isIndex()
    {
        return true;
    }

    void populateIndex()
    {
        List<LexiconCategory> categoryList = TotemicAPI.get().lexicon().getCategories();
        for (int i = 3; i < 12; i++)
        {
            int i_ = i - 3;
            GuiButtonInvisible button = (GuiButtonInvisible) buttonList.get(i);
            LexiconCategory category = i_ >= categoryList.size() ? null : categoryList.get(i_);
            if (category != null)
                button.displayString = I18n.format(category.getUnlocalizedName());
            else
                button.displayString = "";
        }
    }

    void populateBookmarks()
    {
        List<GuiButton> remove = new ArrayList<>();
        List<GuiButton> buttons = buttonList;
        for (GuiButton button : buttons)
            if (button.id >= BOOKMARK_START)
                remove.add(button);
        buttonList.removeAll(remove);

        int len = bookmarks.size();
        boolean thisExists = false;
        for (GuiLexicon lex : bookmarks)
            if (lex.getTitle().equals(getTitle()))
                thisExists = true;

        boolean addEnabled = len < 10 && this instanceof IParented && !thisExists;
        for (int i = 0; i < len + (addEnabled ? 1 : 0); i++)
        {
            boolean isAdd = i == bookmarks.size();
            GuiLexicon gui = isAdd ? null : bookmarks.get(i);
            buttonList.add(new GuiButtonBookmark(BOOKMARK_START + i, left + 138, top + 18 + 14 * i, gui == null ? this : gui, gui == null ? "+" : gui.getTitle()));
        }
    }

}
