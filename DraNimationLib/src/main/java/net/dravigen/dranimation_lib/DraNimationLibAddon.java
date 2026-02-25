package net.dravigen.dranimation_lib;

import api.AddonHandler;
import api.BTWAddon;
import net.dravigen.dranimation_lib.settings.DVS_ConfigManager;

public class DraNimationLibAddon extends BTWAddon {
	public static DVS_ConfigManager.ConfigValue<Double> SMOOTHNESS;
	
	public DraNimationLibAddon() {
		super();
	}
	
	@Override
	public void initialize() {
		AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
		
		DVS_ConfigManager.loadFromFile();
		
		SMOOTHNESS = DVS_ConfigManager.registerDouble("smoothness",
													  "Smoothness",
													  1.0,
													  0.0,
													  20.0,
													  "Handles how smooth movements are",
													  "");
		
		
		DVS_ConfigManager.save();
		
	}
}