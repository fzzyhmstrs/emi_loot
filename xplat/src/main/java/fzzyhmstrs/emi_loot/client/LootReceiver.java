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
    LootReceiver fromBuf(PacketByteBuf buf, World world);

    default ItemStack readItemStack(PacketByteBuf buf, World world) {
        RegistryEntry<Item> byId = Registries.ITEM.getEntry(Registries.ITEM.get(buf.readVarInt()));
        int count = buf.readVarInt();
        if (buf.readBoolean()) {
            NbtElement nbt = buf.readNbt();
			Optional<NbtCompound> opt = nbt instanceof NbtCompound nbtCompound ? Optional.of(nbtCompound) : Optional.empty();
			return new ItemStack(byId, count, opt);
        } else {
            return new ItemStack(byId, count);
        }
    }
}