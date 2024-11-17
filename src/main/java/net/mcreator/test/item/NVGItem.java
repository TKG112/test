package net.mcreator.test.item;

import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.SlotContext;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;

import java.util.List;

public class NVGItem extends Item implements ICurioItem {
	public static final int OFF = 0;
	public static final int ON = 1;

	public NVGItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}

	/**
	 * Ensures the NBT tag is initialized and returns it.
	 */
	private CompoundTag checkAndGetTag(ItemStack stack) {
		if (!stack.hasTag()) {
			stack.setTag(new CompoundTag());
		}
		return stack.getTag();
	}

	/**
	 * Updates the NVG mode based on the "NvgCheck" NBT tag.
	 */
	private void updateNVGMode(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		if (tag.contains("NvgCheck")) {
			boolean nvgCheck = tag.getBoolean("NvgCheck");
			tag.putInt("nvg_mode", nvgCheck ? ON : OFF);
		} else {
			tag.putInt("nvg_mode", OFF); // Default to OFF if "NvgCheck" is not present
		}
	}

	/**
	 * Returns the current NVG mode (ON or OFF).
	 */
	public static int getNVGMode(ItemStack stack) {
		CompoundTag tag = stack.getTag();
		if (tag != null && tag.contains("nvg_mode")) {
			return tag.getInt("nvg_mode");
		}
		return OFF; // Default to OFF if "nvg_mode" is not present
	}

	/**
	 * Adds a tooltip to display the current NVG mode and state.
	 */
	@Override
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		int mode = getNVGMode(stack);
		tooltip.add(Component.literal("NVG Mode: " + (mode == ON ? "ON" : "OFF")));
		if (stack.getOrCreateTag().contains("NvgCheck")) {
			boolean nvgCheck = stack.getOrCreateTag().getBoolean("NvgCheck");
			tooltip.add(Component.literal("NvgCheck: " + (nvgCheck ? "TRUE" : "FALSE")));
		} else {
			tooltip.add(Component.literal("NvgCheck: NOT SET"));
		}
	}

	/**
	 * Curios tick method: Updates NVG mode dynamically based on the "NvgCheck" tag.
	 */
	@Override
	public void curioTick(SlotContext slotContext, ItemStack stack) {
		Entity entity = slotContext.entity();
		if (entity instanceof LivingEntity livingEntity) {
			return; // Only process on the server-side
		}
		updateNVGMode(stack);
	}
}
