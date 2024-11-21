package com.valentinegb.mindpowers;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MindPowers implements ModInitializer {
    public static final String MOD_ID = "mind-powers";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Mind powers activated");

        PayloadTypeRegistry.playC2S().register(BlowUpPayload.ID, BlowUpPayload.CODEC);
        ServerPlayConnectionEvents.INIT.register((handler, server) ->
                ServerPlayNetworking.registerReceiver(handler, BlowUpPayload.ID, (payload, context) ->
                        context.player().server.execute(() -> {
                            Vec3d pos = payload.targetedEntityId().isPresent()
                                    ? context.player().getWorld().getEntityById(payload.targetedEntityId().getAsInt()).getPos()
                                    : context.player().raycast(context.player().getViewDistance() * 16, 0, false).getPos();

                            context.player().getWorld().createExplosion(
                                    null,
                                    context.player().getWorld().getDamageSources().explosion(context.player(), context.player()),
                                    null,
                                    pos,
                                    4,
                                    false,
                                    World.ExplosionSourceType.MOB
                            );
                        })));
    }
}