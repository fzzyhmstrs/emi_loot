package fzzyhmstrs.emi_loot.client;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.networking.ArchaeologyLootPayload;
import fzzyhmstrs.emi_loot.networking.BlockLootPayload;
import fzzyhmstrs.emi_loot.networking.ChestLootPayload;
import fzzyhmstrs.emi_loot.networking.ClearPayload;
import fzzyhmstrs.emi_loot.networking.GameplayLootPayload;
import fzzyhmstrs.emi_loot.networking.MobLootPayload;
import me.fzzyhmstrs.fzzy_config.networking.api.ClientPlayNetworkContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

public class ClientLootTables {
    public static final ClientLootTables INSTANCE = new ClientLootTables();

    private final List<LootReceiver> loots = new LinkedList<>();

    public List<LootReceiver> getLoots() {
        return loots;
    }

    public void clearLoots() {
        loots.clear();
    }

    void receiveChestSender(PacketByteBuf buf) {
        try {
            LootReceiver table = ClientChestLootTable.INSTANCE.fromBuf(buf);
            loots.add(table);
            if (EMILoot.config.isDebug(EMILoot.Type.CHEST)) EMILoot.LOGGER.info("received chest {}", table.getId());
        } catch (Throwable e) {
            EMILoot.LOGGER.error("Critical error encountered while receiving Chest Loot Packet");
            EMILoot.LOGGER.error("Thrown Error: ", e);
        }
    }

    void receiveBlockSender(PacketByteBuf buf) {
        try {
            LootReceiver table = ClientBlockLootTable.INSTANCE.fromBuf(buf);
            loots.add(table);
            if (EMILoot.config.isDebug(EMILoot.Type.BLOCK)) EMILoot.LOGGER.info("received block {}", table.getId());
        } catch (Throwable e) {
            EMILoot.LOGGER.error("Critical error encountered while receiving Block Loot Packet");
            EMILoot.LOGGER.error("Thrown Error: ", e);
        }
    }

    void receiveMobSender(PacketByteBuf buf) {
        try {
            LootReceiver table = ClientMobLootTable.INSTANCE.fromBuf(buf);
            loots.add(table);
            if (EMILoot.config.isDebug(EMILoot.Type.MOB)) EMILoot.LOGGER.info("received mob {}", table.getId());
        } catch (Throwable e) {
            EMILoot.LOGGER.error("Critical error encountered while receiving Mob Loot Packet");
            EMILoot.LOGGER.error("Thrown Error: ", e);
        }
    }

    void receiveGameplaySender(PacketByteBuf buf) {
        try {
            LootReceiver table = ClientGameplayLootTable.INSTANCE.fromBuf(buf);
            loots.add(table);
            if (EMILoot.config.isDebug(EMILoot.Type.GAMEPLAY)) EMILoot.LOGGER.info("received gameplay loot: {}", table.getId());
        } catch (Throwable e) {
            EMILoot.LOGGER.error("Critical error encountered while receiving Gameplay Loot Packet");
            EMILoot.LOGGER.error("Thrown Error: ", e);
        }
    }

    void receiveArchaeologySender(PacketByteBuf buf) {
        try {
            LootReceiver table = ClientArchaeologyLootTable.INSTANCE.fromBuf(buf);
            loots.add(table);
            if (EMILoot.config.isDebug(EMILoot.Type.ARCHAEOLOGY)) EMILoot.LOGGER.info("received archaeology loot: {}", table.getId());
        } catch (Throwable e) {
            EMILoot.LOGGER.error("Critical error encountered while receiving Archaeology Loot Packet");
            EMILoot.LOGGER.error("Thrown Error: ", e);
        }
    }

}