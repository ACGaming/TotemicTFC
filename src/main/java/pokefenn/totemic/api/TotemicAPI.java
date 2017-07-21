package pokefenn.totemic.api;

import net.minecraftforge.event.RegistryEvent;
import pokefenn.totemic.api.lexicon.LexiconAPI;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.api.totem.TotemEffectAPI;

/**
 * This class provides access to the registries and utility functions that are part of the Totemic API
 */
public final class TotemicAPI
{
    private static API instance;

    /**
     * Returns an instance of the Totemic API.
     *
     * <p>The API will be instantiated by Totemic during the preinitalization phase.
     */
    public static API get()
    {
        if(instance == null)
            throw new IllegalStateException("The Totemic API has not been initalized yet, or Totemic is not installed");
        return instance;
    }

    public interface API
    {
        /**
         * Provides access to Totemic's registries
         * @deprecated Instruments, Totem Effects and Ceremonies now use Forge's registry system.
         * Use {@link RegistryEvent.Register} to register them.<br>
         * You can access the registries from {@link TotemicRegistries}.
         * <p>Since Forge's registries require the registry names to be lowercase, attempts are
         * made to automatically convert camelCased names into snake_case when they appear in
         * save files, and also when registering through this interface to preserve binary compatibility.
         * You should register your entries with snake cased names.
         */
        @Deprecated
        TotemicRegistry registry();

        /**
         * Provides access to functionality commonly used by music instrument blocks and items
         */
        MusicAPI music();

        /**
         * Provides access to functionality commonly used for implementing Totem effects
         */
        TotemEffectAPI totemEffect();

        /**
         * Provides access to functionality for adding content to the Totempedia
         */
        LexiconAPI lexicon();
    }
}
