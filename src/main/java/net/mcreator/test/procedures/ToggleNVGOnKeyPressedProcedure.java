package net.mcreator.test.procedures;

import top.theillusivec4.curios.api.CuriosApi;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.test.init.TestModItems;

public class ToggleNVGOnKeyPressedProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity lv ? CuriosApi.getCuriosHelper().findEquippedCurio(TestModItems.NVG.get(), lv).isPresent() : false) {
			if (entity instanceof LivingEntity lv) {
				CuriosApi.getCuriosHelper().findCurios(lv, TestModItems.NVG.get()).forEach(item -> {
					ItemStack itemstackiterator = item.stack();
					if (itemstackiterator.getOrCreateTag().getBoolean("NvgCheck") == true) {
						itemstackiterator.getOrCreateTag().putBoolean("NvgCheck", false);
					} else if (itemstackiterator.getOrCreateTag().getBoolean("NvgCheck") == false) {
						itemstackiterator.getOrCreateTag().putBoolean("NvgCheck", true);
					}
				});
			}
		}
	}
}
