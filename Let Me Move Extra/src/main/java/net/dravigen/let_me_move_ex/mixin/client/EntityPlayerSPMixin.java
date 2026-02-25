package net.dravigen.let_me_move_ex.mixin.client;

import net.dravigen.dranimation_lib.animation.BaseAnimation;
import net.dravigen.dranimation_lib.interfaces.ICustomMovementEntity;
import net.dravigen.dranimation_lib.packet.PacketUtils;
import net.dravigen.dranimation_lib.utils.AnimationUtils;
import net.dravigen.dranimation_lib.utils.GeneralUtils;
import net.dravigen.let_me_move.animation.player.poses.*;
import net.dravigen.let_me_move_ex.animation.player.actions.AnimCrawling;
import net.dravigen.let_me_move_ex.animation.player.actions.AnimDiving;
import net.dravigen.let_me_move_ex.animation.player.actions.AnimSwimming;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.dravigen.let_me_move.animation.AnimRegistry.CROUCHING;
import static net.dravigen.let_me_move_ex.animation.AnimRegistry.CRAWLING;
import static net.dravigen.let_me_move_ex.animation.AnimRegistry.DIVING;

@Mixin(EntityPlayerSP.class)
public abstract class EntityPlayerSPMixin extends AbstractClientPlayer {
	
	public EntityPlayerSPMixin(World par1World, String par2Str) {
		super(par1World, par2Str);
	}
	
	@Shadow
	protected abstract boolean isBlockTranslucent(int par1, int par2, int par3);
	
	@Inject(method = "onLivingUpdate", at = @At(value = "HEAD"))
	private void updateAnimation(CallbackInfo ci) {
		if (!AnimationUtils.extraIsPresent) return;
		
		ICustomMovementEntity customPlayer = (ICustomMovementEntity) this;
		
		customPlayer.lmm_$getAnimation().updateLeaning(this);
		
		if (customPlayer.lmm_$getTimeRendered() == Integer.MAX_VALUE) {
			customPlayer.lmm_$setTimeRendered(0);
		}
		
		for (BaseAnimation animation : AnimationUtils.getAnimationsMap().values()) {
			animation.updateAnimationTime(customPlayer.lmm_$getAnimationID(), this);
		}
		
		if (Minecraft.getMinecraft().gameSettings.keyBindLeft.pressed) {
			customPlayer.lmm_$setSide(ICustomMovementEntity.side.LEFT);
		}
		else if (Minecraft.getMinecraft().gameSettings.keyBindRight.pressed) {
			customPlayer.lmm_$setSide(ICustomMovementEntity.side.RIGHT);
		}
		
		ResourceLocation newID = new ResourceLocation("");
		
		if ((!customPlayer.lmm_$getAnimation().hasCooldown() ||
				customPlayer.lmm_$getAnimation().hasCooldown() &&
						customPlayer.lmm_$getCooldown(customPlayer.lmm_$getAnimationID()) > 0)) {
			for (BaseAnimation animationb : AnimationUtils.getAnimationsMap().values()) {
				if (!animationb.shouldActivateAnimation(this, this.boundingBox)) continue;
				if (animationb.isGeneralConditonsMet(this, this.boundingBox)) {
					newID = animationb.getID();
					
					break;
				}
			}
			
			newID = newID.equals(new ResourceLocation("")) ? AnimStanding.id : newID;
			
			AxisAlignedBB bounds = new AxisAlignedBB(this.boundingBox.minX,
													 this.boundingBox.minY,
													 this.boundingBox.minZ,
													 this.boundingBox.maxX,
													 this.boundingBox.minY + customPlayer.lmm_$getAnimation().height,
													 this.boundingBox.maxZ);
			
			boolean noCollisionWithBlock = this.worldObj.getCollidingBlockBounds(bounds).isEmpty();
			
			if (!newID.equals(customPlayer.lmm_$getAnimationID())) {
				BaseAnimation newAnimation = AnimationUtils.getAnimationFromID(newID);
				float dHeight = newAnimation.height - customPlayer.lmm_$getAnimation().height;
				
				if (dHeight > 0) {
					noCollisionWithBlock = this.worldObj.getCollidingBlockBounds(bounds.addCoord(0, dHeight, 0))
							.isEmpty();
				}
				
				if (noCollisionWithBlock) {
					customPlayer.lmm_$setAnimation(newID);
				}
				else if (!this.isPlayerSleeping()) {
					if (this.worldObj.getCollidingBlockBounds(new AxisAlignedBB(this.boundingBox.minX,
																				this.boundingBox.minY,
																				this.boundingBox.minZ,
																				this.boundingBox.maxX,
																				this.boundingBox.minY + CRAWLING.height,
																				this.boundingBox.maxZ)).isEmpty()) {
						
						if (this.worldObj.getCollidingBlockBounds(new AxisAlignedBB(this.boundingBox.minX,
																					this.boundingBox.minY,
																					this.boundingBox.minZ,
																					this.boundingBox.maxX,
																					this.boundingBox.minY +
																							CROUCHING.height,
																					this.boundingBox.maxZ)).isEmpty()) {
							if (CROUCHING.isGeneralConditonsMet(this, this.boundingBox))
								customPlayer.lmm_$setAnimation(AnimCrouching.id);
						}
						else {
							if (CRAWLING.isGeneralConditonsMet(this, this.boundingBox))
								customPlayer.lmm_$setAnimation(AnimCrawling.id);
						}
					}
				}
			}
			else if (!this.isPlayerSleeping() &&
					!this.worldObj.getCollidingBlockBounds(new AxisAlignedBB(this.boundingBox.minX + 1 / 16d,
																			 MathHelper.floor_double(this.boundingBox.maxY) -
																					 0.2,
																			 this.boundingBox.minZ + 1 / 16d,
																			 this.boundingBox.maxX - 1 / 16d,
																			 this.boundingBox.maxY,
																			 this.boundingBox.maxZ - 1 / 16d))
							.isEmpty() &&
					!GeneralUtils.isEntityFeetInsideBlock(this) &&
					this.worldObj.getCollidingBlockBounds(new AxisAlignedBB(this.boundingBox.minX,
																			this.boundingBox.minY,
																			this.boundingBox.minZ,
																			this.boundingBox.maxX,
																			this.boundingBox.minY + 0.8d,
																			this.boundingBox.maxZ)).isEmpty()) {
				if (this.capabilities.isFlying) {
					if (DIVING.isGeneralConditonsMet(this, this.boundingBox))
						customPlayer.lmm_$setAnimation(AnimDiving.id);
				}
				else {
					if (CRAWLING.isGeneralConditonsMet(this, this.boundingBox))
						customPlayer.lmm_$setAnimation(AnimCrawling.id);
				}
			}
		}
		
		if (customPlayer.lmm_$getOnGround() && !this.onGround) {
			customPlayer.lmm_$setJumpSwing();
		}
		
		customPlayer.lmm_$setOnGround(this.onGround);
		customPlayer.lmm_$setIsFlying(this.capabilities.isFlying);
		
		PacketUtils.sendAnimationDataToServer((EntityPlayerSP) (Object) this);
	}
	
	@Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayerSP;isSneaking()Z"))
	private boolean disableSprintOnCrawl(EntityPlayerSP instance) {
		ICustomMovementEntity customEntity = (ICustomMovementEntity) instance;
		if (instance.isSneaking() ||
				!(customEntity.lmm_$isAnimation(AnimRunning.id) ||
						customEntity.lmm_$isAnimation(AnimWalking.id) ||
						customEntity.lmm_$isAnimation(AnimStanding.id) ||
						customEntity.lmm_$isAnimation(AnimSwimming.id) ||
						customEntity.lmm_$isAnimation(AnimLowFalling.id))) {
			instance.setSprinting(false);
			
			return true;
		}
		
		return false;
	}
	
	@Redirect(method = "pushOutOfBlocks", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayerSP;isBlockTranslucent(III)Z", ordinal = 0))
	private boolean customCollisionOne(EntityPlayerSP instance, int par1, int par2, int par3) {
		AxisAlignedBB bb = instance.boundingBox;
		
		return (this.height > 1 &&
				!instance.worldObj.getCollidingBoundingBoxes(instance,
															 new AxisAlignedBB(bb.minX,
																			   bb.minY + 1,
																			   bb.minZ,
																			   bb.maxX,
																			   bb.maxY,
																			   bb.maxZ)).isEmpty() &&
				this.isBlockTranslucent(par1, MathHelper.floor_double(bb.maxY), par3) ||
				this.isBlockTranslucent(par1, MathHelper.floor_double(bb.minY), par3));
	}
	
	@Redirect(method = "pushOutOfBlocks", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayerSP;isBlockTranslucent(III)Z", ordinal = 1))
	private boolean disableUselessCheck(EntityPlayerSP instance, int par1, int par2, int par3) {
		return false;
		//return this.isBlockTranslucent(par1, (int) (par2 - 1 + this.height - 0.1), par3);
	}
}
