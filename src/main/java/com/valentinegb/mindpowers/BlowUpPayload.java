package com.valentinegb.mindpowers;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.OptionalInt;

public record BlowUpPayload(OptionalInt targetedEntityId) implements CustomPayload {
    public static final CustomPayload.Id<BlowUpPayload> ID = new CustomPayload.Id<>(
            Identifier.of(MindPowers.MOD_ID, "blow_up")
    );
    public static final PacketCodec<RegistryByteBuf, BlowUpPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.OPTIONAL_INT, BlowUpPayload::targetedEntityId,
            BlowUpPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
