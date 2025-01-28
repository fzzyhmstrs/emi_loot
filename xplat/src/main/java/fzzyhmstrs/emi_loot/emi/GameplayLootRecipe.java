package fzzyhmstrs.emi_loot.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.EMILootAgnos;
import fzzyhmstrs.emi_loot.EMILootClientAgnos;
import fzzyhmstrs.emi_loot.client.ClientBuiltPool;
import fzzyhmstrs.emi_loot.client.ClientGameplayLootTable;
import fzzyhmstrs.emi_loot.util.ConditionalStack;
import fzzyhmstrs.emi_loot.util.FloatTrimmer;
import fzzyhmstrs.emi_loot.util.IconGroupEmiWidget;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.SymbolText;
import fzzyhmstrs.emi_loot.util.TrimmedTitle;
import fzzyhmstrs.emi_loot.util.WidgetRowBuilder;
import me.fzzyhmstrs.fzzy_config.util.FcText;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class GameplayLootRecipe implements EmiRecipe {

    public GameplayLootRecipe(ClientGameplayLootTable loot) {
        this.loot = loot;
        loot.build(MinecraftClient.getInstance().world, Blocks.AIR);
        List<EmiStack> list = new LinkedList<>();
        loot.builtItems.forEach((builtPool)-> {
                builtPool.stacks().forEach(stack -> {
                    list.addAll(stack.ingredient());
                });
                addWidgetBuilders(builtPool, false);
            }
        );
        outputStacks = list;
        String key = "emi_loot.gameplay." + loot.id.toString();
        Text text = LText.translatable(key);
        Text rawTitle;
        if (!I18n.hasTranslation(key)) {
            StringBuilder gameplayName = new StringBuilder();
            String[] chestPathTokens = loot.id.getPath().split("[/_]");
            for (String str : chestPathTokens) {
                if (!gameplayName.isEmpty()) {
                    gameplayName.append(" ");
                }
                if (str.length() <= 1) {
                    gameplayName.append(str);
                } else {
                    gameplayName.append(str.substring(0, 1).toUpperCase()).append(str.substring(1));
                }
            }
            if(EMILootAgnos.isModLoaded(loot.id.getNamespace())) {
                rawTitle = LText.translatable("emi_loot.gameplay.unknown_gameplay", gameplayName.toString());
            } else {
                Text unknown = LText.translatable("emi_loot.gameplay.unknown");
                rawTitle = LText.translatable("emi_loot.gameplay.unknown_gameplay", gameplayName + " " + unknown.getString());
            }
            if (EMILoot.config.isLogI18n(EMILoot.Type.GAMEPLAY)) {
				EMILoot.LOGGER.warn("Untranslated gameplay loot table \"{}\" (key: \"{}\")", loot.id, key);
            }
        } else {
            rawTitle = text;
        }
        name = TrimmedTitle.of(rawTitle, EMILoot.config.isTooltipStyle() ? 138 : 148);
    }

    private final ClientGameplayLootTable loot;
    private final List<EmiStack> outputStacks;
    private final TrimmedTitle name;
    private final List<WidgetRowBuilder> rowBuilderList = new LinkedList<>();

    private void addWidgetBuilders(ClientBuiltPool newPool, boolean recursive) {
        if (recursive || rowBuilderList.isEmpty()) {
            rowBuilderList.add(new WidgetRowBuilder(154));
        }
        boolean added = false;
        for (WidgetRowBuilder builder : rowBuilderList) {
            if (builder.canAddPool(newPool)) {
                builder.addAndTrim(newPool);
                added = true;
                break;
            }
        }
        if (!added) {
            @SuppressWarnings("SequencedCollectionMethodCanBeUsed")
            Optional<ClientBuiltPool> opt = rowBuilderList.get(rowBuilderList.size() - 1).addAndTrim(newPool);
            opt.ifPresent(clientMobBuiltPool -> addWidgetBuilders(clientMobBuiltPool, true));
        }


    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EmiClientPlugin.GAMEPLAY_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return Identifier.of(EMILoot.MOD_ID, "/" + getCategory().id.getPath() + "/" + loot.id.getNamespace() + "/" + loot.id.getPath());
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return new LinkedList<>();
    }

    @Override
    public List<EmiIngredient> getCatalysts() {
                    return EmiRecipe.super.getCatalysts();
                }

    @Override
    public List<EmiStack> getOutputs() {
        return outputStacks;
    }

    @Override
    public int getDisplayWidth() {
        return EMILoot.config.isTooltipStyle() ? 144 : 154;
    }

    @Override
    public int getDisplayHeight() {
        if (EMILoot.config.isTooltipStyle()) {
            int stacks = outputStacks.size();
            if (stacks <= 8) {
                return 18 + 11;
            } else {
                if (EMILoot.config.isCompact(EMILoot.Type.GAMEPLAY)) {
                    int ingredients = 0;
                    for (WidgetRowBuilder builder: rowBuilderList) {
                        ingredients += builder.ingredientCount();
                    }
                    if (ingredients <= 8) {
                        return 29;
                    } else {
                        return 11 + 18 * ((ingredients + 7) / 8);
                    }
                } else {
                    return 11 + 18 * ((stacks + 7) / 8);
                }
            }
        } else {
            return rowBuilderList.size() * 29 + 11;
        }
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        int x = 0;
        int y = 0;

        //draw the gameplay name
        widgets.addText(name.title(), 0, 0, 0x404040, false);
        if (EMILootAgnos.isModLoaded(loot.id.getNamespace())) {
            widgets.addTooltip(LText.components(name.rawTitle(), loot.id.getNamespace()), 0, 0, 144, 10);
        } else {
            widgets.addTooltipText(List.of(name.rawTitle()), 0, 0, 144, 10);
        }
        if (EMILoot.config.isTooltipStyle()) {
            List<ConditionalStack> stacks = (outputStacks.size() <= 4 || !EMILoot.config.isCompact(EMILoot.Type.GAMEPLAY))
                    ?
                    rowBuilderList.stream().map(WidgetRowBuilder::stacks).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll)
                    :
                    rowBuilderList.stream().map(WidgetRowBuilder::ingredients).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
            int i = 0;
            int j = 0;
            for (ConditionalStack stack: stacks) {
                SlotWidget widget = widgets.addSlot(stack.getIngredient(), i * 18, 11 + (18 * j));
                String rounded = FloatTrimmer.trimFloatString(stack.weight() / 100f, EMILoot.config.chanceDecimalPlaces.get());
                widget.appendTooltip(FcText.INSTANCE.translatable("emi_loot.rolls", rounded).formatted(Formatting.GRAY));
                if (EMILoot.config.isNotPlain()) {
                    for (Pair<Integer, Text> pair : stack.conditions()) {
                        widget.appendTooltip(SymbolText.of(pair.getLeft(), pair.getRight()));
                    }
                }
                ++i;
                if (i > 7) {
                    i = 0;
                    ++j;
                }
            }
        } else {
            y += 11;
            for (WidgetRowBuilder builder : rowBuilderList) {
                for (ClientBuiltPool pool : builder.getPoolList()) {
                    IconGroupEmiWidget widget = EMILootClientAgnos.createIconGroupEmiWidget(x, y, pool);
                    widgets.add(widget);
                    x += widget.getWidth() + 6;
                }
                y += 29;
                x = 0;
            }
        }
    }

    //may revisit later
    @Override
    public boolean supportsRecipeTree() {
        return false;
    }

    @Override
    public boolean hideCraftable() {
        return EmiRecipe.super.hideCraftable();
    }
}