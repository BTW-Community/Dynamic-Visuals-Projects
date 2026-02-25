package net.dravigen.let_me_move;

import api.AddonHandler;
import api.BTWAddon;
import api.world.data.DataEntry;
import api.world.data.DataProvider;
import net.dravigen.dranimation_lib.interfaces.ICustomMovementEntity;
import net.dravigen.dranimation_lib.utils.AnimationUtils;
import net.dravigen.let_me_move.animation.AnimRegistry;
import net.dravigen.let_me_move.animation.player.poses.AnimStanding;
import net.minecraft.src.*;

public class LetMeMoveAddon extends BTWAddon {
	private static final String CURRENT_ANIMATION_NAME = "CurrentAnimation";
	public static final DataEntry.PlayerDataEntry<String> CURRENT_ANIMATION = DataProvider.getBuilder(String.class)
			.name(CURRENT_ANIMATION_NAME)
			.defaultSupplier(() -> String.valueOf(AnimStanding.id))
			.readNBT(NBTTagCompound::getString)
			.writeNBT(NBTTagCompound::setString)
			.player()
			.syncPlayer()
			.buildPlayer();
	public static int prevTick = 0;
	
	public static boolean isExtraLoaded() {
		return AddonHandler.isModInstalled("let_me_move_ex") && AnimationUtils.extraIsPresent;
	}
	
	public static boolean serverHasLetMeMove() {
		return AnimationUtils.serverHasLMM;
	}
	
	public static void updateModelInfo(Entity entity, EntityPlayer player, ICustomMovementEntity customEntity,
			ModelBiped modelBiped) {
		if (Minecraft.getMinecraft().thePlayer.ticksExisted > prevTick) {
			if (player != Minecraft.getMinecraft().thePlayer) {
				if (!entity.isRiding() && !entity.inWater) {
					boolean onGround = player.posY == player.prevPosY &&
							player.worldObj.checkBlockCollision(player.boundingBox.copy().offset(0, -0.1f, 0));
					
					if (customEntity.lmm_$getOnGround() && !onGround) {
						customEntity.lmm_$setJumpSwing();
					}
					
					customEntity.lmm_$setOnGround(onGround);
				}
				else {
					customEntity.lmm_$setOnGround(true);
				}
			}
			
			if (!customEntity.lmm_$getOnGround() && !customEntity.lmm_$getIsFlying() && !modelBiped.isRiding) {
				customEntity.lmm_$setJumpTime(customEntity.lmm_$getJumpTime() + 1);
			}
			else {
				customEntity.lmm_$setJumpTime(0);
			}
		}
	}
	
	@Override
	public void preInitialize() {
		CURRENT_ANIMATION.register();
	}
	
	@Override
	public void initialize() {
		AnimRegistry.registerAllAnimation();
		AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
	}
}