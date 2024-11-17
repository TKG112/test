
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.test.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;

import net.mcreator.test.item.RangefinderItem;
import net.mcreator.test.item.NVGItem;
import net.mcreator.test.item.BackpackItem;
import net.mcreator.test.TestMod;

public class TestModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, TestMod.MODID);
	public static final RegistryObject<Item> RANGEFINDER = REGISTRY.register("rangefinder", () -> new RangefinderItem());
	public static final RegistryObject<Item> BACKPACK = REGISTRY.register("backpack", () -> new BackpackItem());
	public static final RegistryObject<Item> NVG = REGISTRY.register("nvg", () -> new NVGItem());
	// Start of user code block custom items
	// End of user code block custom items
}
