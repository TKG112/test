
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.test.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.mcreator.test.TestMod;

public class TestModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TestMod.MODID);
	public static final RegistryObject<CreativeModeTab> AAAAAA = REGISTRY.register("aaaaaa",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.test.aaaaaa")).icon(() -> new ItemStack(TestModItems.RANGEFINDER.get())).displayItems((parameters, tabData) -> {
				tabData.accept(TestModItems.RANGEFINDER.get());
				tabData.accept(TestModItems.BACKPACK.get());
				tabData.accept(TestModItems.NVG.get());
			})

					.build());
}
