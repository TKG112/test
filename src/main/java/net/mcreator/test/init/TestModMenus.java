
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.test.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.extensions.IForgeMenuType;

import net.minecraft.world.inventory.MenuType;

import net.mcreator.test.world.inventory.BackpackGUIMenu;
import net.mcreator.test.world.inventory.BackpackGUI2Menu;
import net.mcreator.test.TestMod;

public class TestModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, TestMod.MODID);
	public static final RegistryObject<MenuType<BackpackGUIMenu>> BACKPACK_GUI = REGISTRY.register("backpack_gui", () -> IForgeMenuType.create(BackpackGUIMenu::new));
	public static final RegistryObject<MenuType<BackpackGUI2Menu>> BACKPACK_GUI_2 = REGISTRY.register("backpack_gui_2", () -> IForgeMenuType.create(BackpackGUI2Menu::new));
}
