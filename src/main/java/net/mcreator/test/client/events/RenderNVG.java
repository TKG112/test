package net.mcreator.test.client.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.mcreator.test.init.TestModItems;
import net.mcreator.test.item.NVGItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.lang.reflect.Field;
import java.util.List;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class RenderNVG {
    private static PostChain nvgShader = null;
    private static int lastWidth = -1;
    private static int lastHeight = -1;

    @SubscribeEvent
    public static void renderNVGEffect(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || mc.level == null) {
            return;
        }

        boolean hasNvgItem = false;
        boolean nvgOn = false;
        for (SlotResult slot : CuriosApi.getCuriosHelper().findCurios(player, TestModItems.NVG.get())) {
            hasNvgItem = true;
            nvgOn = NVGItem.getNVGMode(slot.stack()) == NVGItem.ON;
            break;
        }

        float nightVisionEnabled = (hasNvgItem && nvgOn) ? 1.0f : 0.0f;

        if (nvgShader == null) {
            initializeShader(mc);
        }

        if (nvgShader != null) {
            updateShaderUniforms(nightVisionEnabled);
        }
    }

    private static void initializeShader(Minecraft mc) {
        try {
            nvgShader = new PostChain(mc.textureManager, mc.getResourceManager(),
                    mc.getMainRenderTarget(), new ResourceLocation("minecraft", "shaders/post/night-vision.json"));
            lastWidth = mc.getWindow().getWidth();
            lastHeight = mc.getWindow().getHeight();
            nvgShader.resize(lastWidth, lastHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateShaderUniforms(float nightVisionEnabled) {
        Minecraft mc = Minecraft.getInstance();

        try {
            int width = mc.getWindow().getWidth();
            int height = mc.getWindow().getHeight();
            if (width != lastWidth || height != lastHeight) {
                lastWidth = width;
                lastHeight = height;
                nvgShader.resize(width, height);
            }

            List<PostPass> passes = getShaderPasses(nvgShader);
            if (passes != null) {
                for (PostPass pass : passes) {
                    if (pass.getEffect().getUniform("NightVisionEnabled") != null) {
                        pass.getEffect().safeGetUniform("NightVisionEnabled").set(nightVisionEnabled);
                    }
                }
            }

            nvgShader.process(mc.getFrameTime());
            mc.getMainRenderTarget().bindWrite(false);

        } catch (Exception e) {
            e.printStackTrace();
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
