package net.dravigen.let_me_move_ex.animation.player.actions;

import net.dravigen.dranimation_lib.utils.AnimationUtils;
import net.dravigen.let_me_move.animation.player.poses.AnimCommon;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ResourceLocation;

public class AnimBaseAction extends AnimCommon {
	public AnimBaseAction(ResourceLocation id, float height, float speedModifier, boolean needYOffsetUpdate,
			int maxCooldown, int duration, boolean shouldAutoUpdate, float yOffset) {
		super(id, height, speedModifier, needYOffsetUpdate, maxCooldown, duration, shouldAutoUpdate, yOffset);
	}
	
	public AnimBaseAction(ResourceLocation id, float height, float speedModifier, boolean needYOffsetUpdate,
			int maxCooldown, int duration, boolean shouldAutoUpdate) {
		super(id, height, speedModifier, needYOffsetUpdate, maxCooldown, duration, shouldAutoUpdate);
	}
	
	public AnimBaseAction(ResourceLocation id, float height, float speedModifier, boolean needYOffsetUpdate,
			int maxCooldown, int duration) {
		super(id, height, speedModifier, needYOffsetUpdate, maxCooldown, duration);
	}
	
	public AnimBaseAction(ResourceLocation id, float height, float speedModifier, boolean needYOffsetUpdate,
			int maxCooldown) {
		super(id, height, speedModifier, needYOffsetUpdate, maxCooldown);
	}
	
	public AnimBaseAction(ResourceLocation id, float height, float speedModifier, boolean needYOffsetUpdate) {
		super(id, height, speedModifier, needYOffsetUpdate);
	}
	
	public AnimBaseAction(ResourceLocation id, float height, float speedModifier) {
		super(id, height, speedModifier);
	}
	
	public AnimBaseAction(ResourceLocation id, float height) {
		super(id, height);
	}
	
	public AnimBaseAction(ResourceLocation id) {
		super(id);
	}
	
	@Override
	public boolean shouldActivateAnimation(EntityPlayer player, AxisAlignedBB axisAlignedBB) {
		if (!AnimationUtils.extraIsPresent) return false;
		
		return super.shouldActivateAnimation(player, axisAlignedBB);
	}
}
