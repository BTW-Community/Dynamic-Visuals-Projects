package net.dravigen.let_me_move_ex;

import net.dravigen.dranimation_lib.settings.DVS_ConfigManager;

public class LmmEx_Settings {
	public static DVS_ConfigManager.ConfigValue<Boolean> SHOULD_CRAWL;
	public static DVS_ConfigManager.ConfigValue<Boolean> SHOULD_DASH;
	public static DVS_ConfigManager.ConfigValue<Boolean> SHOULD_DIVE;
	public static DVS_ConfigManager.ConfigValue<Boolean> SHOULD_PULL_UP;
	public static DVS_ConfigManager.ConfigValue<Boolean> SHOULD_ROLL;
	public static DVS_ConfigManager.ConfigValue<Boolean> SHOULD_SKY_DIVE;
	public static DVS_ConfigManager.ConfigValue<Boolean> SHOULD_SWIM;
	public static DVS_ConfigManager.ConfigValue<Boolean> SHOULD_WALL_SLIDE;
	
	private static final String category = "Let Me Move Extra !";
	
	public static void registerAllSettings() {
		SHOULD_CRAWL = DVS_ConfigManager.registerBool("shouldCrawl",
															"Toggle 'Crawl'",
															true,
															"",
															category);
		
		SHOULD_DASH = DVS_ConfigManager.registerBool("shouldDash",
													  "Toggle 'Dash'",
													  true,
													  "",
													  category);
		
		SHOULD_DIVE = DVS_ConfigManager.registerBool("shouldDive",
													  "Toggle 'Dive'",
													  true,
													  "",
													  category);
		
		SHOULD_PULL_UP = DVS_ConfigManager.registerBool("shouldPullUp",
													  "Toggle 'Pull Up'",
													  true,
													  "",
													  category);
		
		SHOULD_ROLL = DVS_ConfigManager.registerBool("shouldRoll",
													  "Toggle 'Roll'",
													  true,
													  "",
													  category);
		
		SHOULD_SKY_DIVE = DVS_ConfigManager.registerBool("shouldSkyDive",
													  "Toggle 'Sky Dive'",
													  true,
													  "",
													  category);
		
		SHOULD_SWIM = DVS_ConfigManager.registerBool("shouldSwim",
														 "Toggle 'Swim'",
														 true,
														 "",
														 category);
		
		SHOULD_WALL_SLIDE = DVS_ConfigManager.registerBool("shouldWallSlide",
														 "Toggle 'Wall Slide'",
														 true,
														 "",
														 category);
	}
}
