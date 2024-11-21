package com.valentinegb.mindpowers;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

import java.util.OptionalInt;

public class MindPowersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyBinding blowUpKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.blowUp", GLFW.GLFW_KEY_B, KeyBinding.GAMEPLAY_CATEGORY)
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (blowUpKey.wasPressed()) {
                if (!client.player.isSpectator()) {
                    ClientPlayNetworking.send(new BlowUpPayload(client.targetedEntity != null
                            ? OptionalInt.of(client.targetedEntity.getId())
                            : OptionalInt.empty()));
                }
            }
        });
    }
}