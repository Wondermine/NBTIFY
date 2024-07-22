package io.wondermine.nbtify.forge;

import io.wondermine.nbtify.Nbtify;
import io.wondermine.nbtify.utils.TooltipModifier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(Nbtify.MOD_ID)
public final class NbtifyForge {
    public NbtifyForge() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        TooltipModifier.getTooltip(event.getItemStack(), event.getFlags(), event.getToolTip());
    }
}
