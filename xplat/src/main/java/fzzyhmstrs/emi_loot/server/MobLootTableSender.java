package fzzyhmstrs.emi_loot.server;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.util.SimpleCustomPayload;
import fzzyhmstrs.emi_loot.util.TextKey;
import me.fzzyhmstrs.fzzy_config.api.ConfigApi;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MobLootTableSender implements LootSender<MobLootPoolBuilder> {

    public MobLootTableSender(Identifier id, Identifier mobId) {
        this.idToSend = LootSender.getIdToSend(id);
        this.mobIdToSend = LootSender.getIdToSend(mobId);
    }

    private final String idToSend;
    private final String mobIdToSend;
    final List<MobLootPoolBuilder> builderList = new LinkedList<>();
    public static Identifier MOB_SENDER = new Identifier("e_l", "m_s");
    boolean isEmpty = true;

    @Override
    public void build() {
        builderList.forEach((builder)-> {
            builder.build();
            if (!builder.isEmpty) {
                isEmpty = false;
            }
        });
    }

    @Override
    public String getId() {
        return idToSend;
    }

    @Override
    public void send(ServerPlayerEntity player) {
        if (!ConfigApi.INSTANCE.network().canSend(MOB_SENDER, player)) return;
        //pre-build the builders to do empty checks
        if (isEmpty) {
            if (EMILoot.config.isDebug(EMILoot.Type.MOB)) EMILoot.LOGGER.info("avoiding empty mob: " + idToSend);
            return;
        }
        PacketByteBuf buf = ConfigApi.INSTANCE.network().buf();
        //start with the loot pool ID and the number of builders to write
        buf.writeString(idToSend);
        buf.writeString(mobIdToSend);

        if (builderList.size() == 1 && builderList.get(0).isSimple) {
            if (EMILoot.config.isDebug(EMILoot.Type.MOB)) EMILoot.LOGGER.info("sending simple mob: " + idToSend);
            buf.writeShort(-1);
            buf.writeRegistryValue(Registries.ITEM, builderList.get(0).simpleStack.getItem());
            ConfigApi.INSTANCE.network().send(new SimpleCustomPayload(buf, MOB_SENDER), player);
            return;
        } else if (builderList.isEmpty()) {
            if (EMILoot.config.isDebug(EMILoot.Type.MOB)) EMILoot.LOGGER.info("avoiding empty mob: " + idToSend);
            return;
        }

        buf.writeShort(builderList.size());
        builderList.forEach((builder)-> {

            List<TextKey> totalConditions = new ArrayList<>();
            builder.conditions.forEach((lootConditionResult -> totalConditions.add(lootConditionResult.text())));
            builder.functions.forEach((lootFunctionResult)-> totalConditions.addAll(lootFunctionResult.conditions()));

            //write size of the builders condition set
            buf.writeShort(totalConditions.size());
            //write the textkey of each of those conditions
            totalConditions.forEach((lootConditionResult -> lootConditionResult.toBuf(buf)));

            //write size of the builders function set
            buf.writeShort(builder.functions.size());
            //write the textkey of the functions
            builder.functions.forEach((lootFunctionResult)-> lootFunctionResult.text().toBuf(buf));
            //write the size of the builtMap of individual chest pools
            Map<List<TextKey>, ChestLootPoolBuilder> lootPoolBuilderMap = builder.builtMap;
            buf.writeShort(lootPoolBuilderMap.size());
            lootPoolBuilderMap.forEach((key, chestBuilder)-> {

                //for each functional condition, write the size then list of condition textKeys
                buf.writeShort(key.size());
                key.forEach((textKey)->textKey.toBuf(buf));

                //for each functional condition, write the size of the actual itemstacks
                Map<ItemStack, Float> keyPoolMap = lootPoolBuilderMap.getOrDefault(key, new ChestLootPoolBuilder(1f)).builtMap;
                buf.writeShort(keyPoolMap.size());

                //for each itemstack, write the stack and weight
                keyPoolMap.forEach((stack, weight)-> {
                    writeItemStack(buf, stack, player.getWorld());
                    buf.writeFloat(weight);
                });
            });

        });
        ConfigApi.INSTANCE.network().send(new SimpleCustomPayload(buf, MOB_SENDER), player);
    }

    @Override
    public void addBuilder(MobLootPoolBuilder builder) {
        builderList.add(builder);
    }

    @Override
    public List<MobLootPoolBuilder> getBuilders() {
        return builderList;
    }
}