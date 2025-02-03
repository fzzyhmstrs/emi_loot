package fzzyhmstrs.emi_loot.client;

import fzzyhmstrs.emi_loot.util.SimpleCustomPayload;
import me.fzzyhmstrs.fzzy_config.networking.api.ClientPlayNetworkContext;

public class ClientLootTablesReceiver {

	public static void receiveChestSender(SimpleCustomPayload payload, ClientPlayNetworkContext ctx) {
		ClientLootTables.INSTANCE.receiveChestSender(payload.buf(), ctx.player().getWorld());
	}

	public static void receiveBlockSender(SimpleCustomPayload payload, ClientPlayNetworkContext ctx) {
		ClientLootTables.INSTANCE.receiveBlockSender(payload.buf(), ctx.player().getWorld());
	}

	public static void receiveMobSender(SimpleCustomPayload payload, ClientPlayNetworkContext ctx) {
		ClientLootTables.INSTANCE.receiveMobSender(payload.buf(), ctx.player().getWorld());
	}

	public static void receiveGameplaySender(SimpleCustomPayload payload, ClientPlayNetworkContext ctx) {
		ClientLootTables.INSTANCE.receiveGameplaySender(payload.buf(), ctx.player().getWorld());
	}

	public static void receiveArchaeologySender(SimpleCustomPayload payload, ClientPlayNetworkContext ctx) {
		ClientLootTables.INSTANCE.receiveArchaeologySender(payload.buf(), ctx.player().getWorld());
	}

}