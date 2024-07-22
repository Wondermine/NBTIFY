package io.wondermine.nbtify.fabric;

import io.wondermine.nbtify.utils.TooltipModifier;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

public final class NbtifyFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ItemTooltipCallback.EVENT.register(TooltipModifier::getTooltip);
    }
}
