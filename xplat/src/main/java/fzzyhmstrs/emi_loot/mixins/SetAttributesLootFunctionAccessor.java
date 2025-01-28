package fzzyhmstrs.emi_loot.mixins;

import net.minecraft.loot.function.SetAttributesLootFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(SetAttributesLootFunction.class)
public interface SetAttributesLootFunctionAccessor {
	@Accessor
	List<SetAttributesLootFunction.Attribute> getAttributes();
}