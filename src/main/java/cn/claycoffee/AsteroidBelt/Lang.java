package cn.claycoffee.AsteroidBelt;

import java.util.Arrays;
import java.util.List;

import cn.claycoffee.ClayTech.utils.Utils;

public class Lang {
	public static String[] LocaleList = { "zh-CN", "zh-TW", "en-UK", "en-US" };

	public static String readItemText(String name) {
		if (AsteroidBelt.currentLangYML.getCustomConfig().getString("Items." + name) == null) {
			return "Missing locale.";
		} else {
			return AsteroidBelt.currentLangYML.getCustomConfig().getString("Items." + name).replaceAll("&", "§");
		}
	}

	@SuppressWarnings("unchecked")
	public static List<String> readItemLore(String name) {
		if (AsteroidBelt.currentLangYML.getCustomConfig().getString("Items." + name + "_LORE") == null) {
			return Arrays.asList(new String[] { "Missing locale." });
		} else {
			return Utils.replaceList(
					((List<String>) AsteroidBelt.currentLangYML.getCustomConfig().getList("Items." + name + "_LORE")),
					"&", "§");
		}
	}

	public static String readGeneralText(String name) {
		if (AsteroidBelt.currentLangYML.getCustomConfig().getString("General." + name) == null) {
			return "Missing locale.";
		} else {
			return AsteroidBelt.currentLangYML.getCustomConfig().getString("General." + name).replaceAll("&", "§");
		}
	}

	public static String readCategoriesText(String name) {
		if (AsteroidBelt.currentLangYML.getCustomConfig().getString("Categories." + name) == null) {
			return "Missing locale.";
		} else {
			return AsteroidBelt.currentLangYML.getCustomConfig().getString("Categories." + name).replaceAll("&", "§");
		}
	}

	public static String readResearchesText(String name) {
		if (AsteroidBelt.currentLangYML.getCustomConfig().getString("Researches." + name) == null) {
			return "Missing locale.";
		} else {
			return AsteroidBelt.currentLangYML.getCustomConfig().getString("Researches." + name).replaceAll("&", "§");
		}
	}

	public static String readMachinesText(String name) {
		if (AsteroidBelt.currentLangYML.getCustomConfig().getString("Machines." + name) == null) {
			return "Missing locale.";
		} else {
			return AsteroidBelt.currentLangYML.getCustomConfig().getString("Machines." + name).replaceAll("&", "§");
		}
	}

	public static String readMachineRecipesText(String name) {
		if (AsteroidBelt.currentLangYML.getCustomConfig().getString("MachineRecipes." + name) == null) {
			return "Missing locale.";
		} else {
			return AsteroidBelt.currentLangYML.getCustomConfig().getString("MachineRecipes." + name).replaceAll("&",
					"§");
		}
	}

	public static String readPlanetsText(String name) {
		if (AsteroidBelt.currentLangYML.getCustomConfig().getString("Planets." + name) == null) {
			return "Missing locale.";
		} else {
			return AsteroidBelt.currentLangYML.getCustomConfig().getString("Planets." + name).replaceAll("&", "§");
		}
	}

	public static String readResourcesText(String name) {
		if (AsteroidBelt.currentLangYML.getCustomConfig().getString("Resources." + name) == null) {
			return "Missing locale.";
		} else {
			return AsteroidBelt.currentLangYML.getCustomConfig().getString("Resources." + name).replaceAll("&", "§");
		}
	}
}
