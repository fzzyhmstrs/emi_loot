package fzzyhmstrs.emi_loot.client;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Optional;

public interface LootReceiver {
    boolean isEmpty();
    Identifier getId();
    LootReceiver fromBuf(PacketByteBuf buf);

    default ItemStack readItemStack(PacketByteBuf buf) {
       Item byId = Registries.ITEM.get(buf.readVarInt());
        int count = buf.readVarInt();
        if (buf.readBoolean()) {
            NbtElement nbt = buf.readNbt();
            ItemStack stack = new ItemStack(byId, count);
            if (nbt instanceof NbtCompound compound) {
                stack.setNbt(compound);
            }
            return stack;
        } else {
            return new ItemStack(byId, count);
        }
    }
}