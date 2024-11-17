package net.mcreator.test.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import java.util.Optional;
import java.util.List;

public class RangefinderRightclickedProcedure {
	public static void execute(Player player, Level world) {
		if (player == null || world == null)
			return;
		// Perform block raycast
		Vec3 eyePosition = player.getEyePosition(1.0F);
		Vec3 lookDirection = player.getLookAngle();
		Vec3 endPosition = eyePosition.add(lookDirection.scale(150)); // Adjust range as needed
		BlockHitResult blockHitResult = world.clip(new ClipContext(eyePosition, endPosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
		// Perform entity raycast
		EntityHitResult entityHitResult = null;
		Vec3 hitVec = null;
		List<Entity> entities = world.getEntities(player, player.getBoundingBox().expandTowards(lookDirection.scale(100)).inflate(1.0, 1.0, 1.0));
		double closestDistance = 100.0; // Initial large distance
		for (Entity entity : entities) {
			if (entity != player) {
				AABB entityBox = entity.getBoundingBox().inflate(0.3); // Slightly inflate to make hitting easier
				Optional<Vec3> optionalHitVec = entityBox.clip(eyePosition, endPosition);
				if (optionalHitVec.isPresent()) {
					double distance = eyePosition.distanceTo(optionalHitVec.get());
					if (distance < closestDistance) {
						closestDistance = distance;
						entityHitResult = new EntityHitResult(entity, optionalHitVec.get());
						hitVec = optionalHitVec.get();
					}
				}
			}
		}
		// Determine which hit result to use
		String message;
		if (blockHitResult.getType() == HitResult.Type.BLOCK && (entityHitResult == null || eyePosition.distanceTo(blockHitResult.getLocation()) < closestDistance)) {
			double distance = blockHitResult.getLocation().distanceTo(eyePosition);
			message = String.format("%.1f blocks away", distance);
		} else if (entityHitResult != null) {
			double distance = hitVec.distanceTo(eyePosition);
			message = String.format("%.1f blocks away", distance);
		} else {
			message = "??? blocks away";
		}
		// Display the message in the action bar
		if (player instanceof net.minecraft.server.level.ServerPlayer) {
			((net.minecraft.server.level.ServerPlayer) player).displayClientMessage(Component.literal(message), true);
		}
	}
}
