package pokefenn.totemic.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import pokefenn.totemic.Totemic;
import pokefenn.totemic.network.client.PacketMouseWheel;
import pokefenn.totemic.network.server.PacketCeremonyStartupFull;
import pokefenn.totemic.network.server.PacketCeremonyStartupMusic;
import pokefenn.totemic.network.server.PacketTotemEffectMusic;

public class NetworkHandler
{
    public static final SimpleNetworkWrapper wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Totemic.MOD_ID);
    private static int id;

    public static void init()
    {
        registerPacket(PacketMouseWheel.Handler.class, PacketMouseWheel.class, Side.SERVER);
        registerPacket(PacketCeremonyStartupMusic.Handler.class, PacketCeremonyStartupMusic.class, Side.CLIENT);
        registerPacket(PacketCeremonyStartupFull.Handler.class, PacketCeremonyStartupFull.class, Side.CLIENT);
        registerPacket(PacketTotemEffectMusic.Handler.class, PacketTotemEffectMusic.class, Side.CLIENT);
    }

    public static void sendToServer(IMessage packet)
    {
        wrapper.sendToServer(packet);
    }

    public static void sendToClient(IMessage packet, EntityPlayerMP player)
    {
        wrapper.sendTo(packet, player);
    }

    public static void sendAround(IMessage packet, int dim, double x, double y, double z, double range)
    {
        wrapper.sendToAllAround(packet, new NetworkRegistry.TargetPoint(dim, x, y, z, range));
    }

    public static void sendAround(IMessage packet, int dim, BlockPos pos, double range)
    {
        sendAround(packet, dim, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, range);
    }

    public static void sendAround(IMessage packet, TileEntity tile, double range)
    {
        sendAround(packet, tile.getWorld().provider.getDimension(), tile.getPos(), range);
    }

    private static <T extends IMessage, R extends IMessage> void registerPacket(Class<? extends IMessageHandler<T, R>> handlerClass, Class<T> messageClass, Side side)
    {
        wrapper.registerMessage(handlerClass, messageClass, id++, side);
    }
}