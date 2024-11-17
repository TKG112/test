package net.mcreator.test.item;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;

import java.util.List;

public class NVGItem extends Item {
	public static final int OFF = 0;
	public static final int ON = 1;

	public NVGItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}

	private CompoundTag checkAndGetTag(ItemStack stack) {
		if (!stack.hasTag()) {
			stack.setTag(new CompoundTag());
		}
		return stack.getTag();
	}

	public static void toggleNVGMode(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		if (tag.contains("nvg_mode")) {
			int currentMode = tag.getInt("nvg_mode");
			tag.putInt("nvg_mode", (currentMode == ON) ? OFF : ON);
		} else {
			tag.putInt("nvg_mode", ON);
		}
	}

	public static int getNVGMode(ItemStack stack) {
		CompoundTag tag = stack.getTag();
		if (tag != null && tag.contains("nvg_mode")) {
			return tag.getInt("nvg_mode");
		}
		return OFF;
	}
}
