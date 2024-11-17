package net.mcreator.test.client.events;

import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.CuriosApi;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.Minecraft;

import net.mcreator.test.item.NVGItem;
import net.mcreator.test.init.TestModItems;

import java.util.List;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class RenderEvents {
	private static PostChain nightVisionShader = null;
	private static int lastWidth = -1;
	private static int lastHeight = -1;

	@SubscribeEvent
	public static void onRenderTick(TickEvent.RenderTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			Minecraft mc = Minecraft.getInstance();
			if (mc.level == null || mc.player == null) {
				return;
			}
			Player player = mc.player;
			boolean hasNvgItem = false;
			boolean nvgOn = false;
			for (SlotResult slot : CuriosApi.getCuriosHelper().findCurios(player, TestModItems.NVG.get())) {
				hasNvgItem = true;
				nvgOn = NVGItem.getNVGMode(slot.stack()) == NVGItem.ON;
				break;
			}
			float nightVisionEnabled = (hasNvgItem && nvgOn) ? 1.0f : 0.0f;
			try {
				if (nightVisionShader == null) {
					nightVisionShader = new PostChain(mc.textureManager, mc.getResourceManager(), mc.getMainRenderTarget(), new ResourceLocation("minecraft", "shaders/post/night-vision.json"));
					lastWidth = mc.getWindow().getWidth();
					lastHeight = mc.getWindow().getHeight();
					nightVisionShader.resize(lastWidth, lastHeight);
				} else {
					int width = mc.getWindow().getWidth();
					int height = mc.getWindow().getHeight();
					if (width != lastWidth || height != lastHeight) {
						lastWidth = width;
						lastHeight = height;
						nightVisionShader.resize(width, height);
					}
				}
				List<PostPass> passes = getShaderPasses(nightVisionShader);
				if (passes != null) {
					for (PostPass pass : passes) {
						if (pass.getEffect().getUniform("NightVisionEnabled") != null) {
							pass.getEffect().safeGetUniform("NightVisionEnabled").set(nightVisionEnabled);
						}
					}
				}
				nightVisionShader.process(mc.getFrameTime());
				mc.getMainRenderTarget().bindWrite(false);
				if (nightVisionEnabled == 0.0f) {
					clearShader();
				}
			} catch (Exception e) {
				e.printStackTrace();
				clearShader();
			}
		}
	}

	private static void clearShader() {
		if (nightVisionShader != null) {
			nightVisionShader.close();
			nightVisionShader = null;
		}
	}

	private static List<PostPass> getShaderPasses(PostChain postChain) {
		try {
			Field passesField = PostChain.class.getDeclaredField("passes");
			passesField.setAccessible(true);
			return (List<PostPass>) passesField.get(postChain);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
