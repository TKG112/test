
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.test.init;

import org.lwjgl.glfw.GLFW;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import net.mcreator.test.network.ToggleNVGMessage;
import net.mcreator.test.network.OpenBackpackMessage;
import net.mcreator.test.network.NVGTGIncreaseMessage;
import net.mcreator.test.network.NVGTGDecreaseMessage;
import net.mcreator.test.TestMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class TestModKeyMappings {
	public static final KeyMapping OPEN_BACKPACK = new KeyMapping("key.test.open_backpack", GLFW.GLFW_KEY_B, "key.categories.test") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				TestMod.PACKET_HANDLER.sendToServer(new OpenBackpackMessage(0, 0));
				OpenBackpackMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping TOGGLE_NVG = new KeyMapping("key.test.toggle_nvg", GLFW.GLFW_KEY_N, "key.categories.misc") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				TestMod.PACKET_HANDLER.sendToServer(new ToggleNVGMessage(0, 0));
				ToggleNVGMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping NVGTG_INCREASE = new KeyMapping("key.test.nvgtg_increase", GLFW.GLFW_KEY_UP, "key.categories.misc") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				TestMod.PACKET_HANDLER.sendToServer(new NVGTGIncreaseMessage(0, 0));
				NVGTGIncreaseMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping NVGTG_DECREASE = new KeyMapping("key.test.nvgtg_decrease", GLFW.GLFW_KEY_DOWN, "key.categories.misc") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				TestMod.PACKET_HANDLER.sendToServer(new NVGTGDecreaseMessage(0, 0));
				NVGTGDecreaseMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(OPEN_BACKPACK);
		event.register(TOGGLE_NVG);
		event.register(NVGTG_INCREASE);
		event.register(NVGTG_DECREASE);
	}

	@Mod.EventBusSubscriber({Dist.CLIENT})
	public static class KeyEventListener {
		@SubscribeEvent
		public static void onClientTick(TickEvent.ClientTickEvent event) {
			if (Minecraft.getInstance().screen == null) {
				OPEN_BACKPACK.consumeClick();
				TOGGLE_NVG.consumeClick();
				NVGTG_INCREASE.consumeClick();
				NVGTG_DECREASE.consumeClick();
			}
		}
	}
}
