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

    public void receiveChestSender(PacketByteBuf buf) {
        LootReceiver table = ClientChestLootTable.INSTANCE.fromBuf(buf);
        loots.add(table);
        if (EMILoot.config.isDebug(EMILoot.Type.CHEST)) EMILoot.LOGGER.info("received chest " + table.getId());
    }

    public void receiveBlockSender(PacketByteBuf buf) {
        LootReceiver table = ClientBlockLootTable.INSTANCE.fromBuf(buf);
        loots.add(table);
        if (EMILoot.config.isDebug(EMILoot.Type.BLOCK)) EMILoot.LOGGER.info("received block " + table.getId());
    }

    public void receiveMobSender(PacketByteBuf buf) {
        LootReceiver table = ClientMobLootTable.INSTANCE.fromBuf(buf);
        loots.add(table);
        if (EMILoot.config.isDebug(EMILoot.Type.MOB)) EMILoot.LOGGER.info("received mob " + table.getId());
    }

    public void receiveGameplaySender(PacketByteBuf buf) {
        LootReceiver table = ClientGameplayLootTable.INSTANCE.fromBuf(buf);
        loots.add(table);
        if (EMILoot.config.isDebug(EMILoot.Type.GAMEPLAY)) EMILoot.LOGGER.info("received gameplay loot: " + table.getId());
    }

    public void receiveArchaeologySender(PacketByteBuf buf) {
        LootReceiver table = ClientArchaeologyLootTable.INSTANCE.fromBuf(buf);
        loots.add(table);
        if (EMILoot.config.isDebug(EMILoot.Type.ARCHAEOLOGY)) EMILoot.LOGGER.info("received archaeology loot: " + table.getId());
    }

}