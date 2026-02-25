package net.dravigen.let_me_move.animation.player.poses;

import api.item.items.ProgressiveCraftingItem;
import net.minecraft.src.*;

public class AnimGui extends AnimCommon {
	public static final ResourceLocation id = new ResourceLocation("LMM", "busy");
	
	public AnimGui() {
		super(id);
	}
	
	@Override
	public boolean isGeneralConditonsMet(EntityPlayer player, AxisAlignedBB axisAlignedBB) {
		ItemStack heldItem = player.getHeldItem();
		
		return player == Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().currentScreen != null ||
				player.isEating() && heldItem != null && heldItem.getItem() instanceof ProgressiveCraftingItem;
	}
}
