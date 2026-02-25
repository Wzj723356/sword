package net.mcreator.sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.mcreator.sword.SwordMod;

public interface CultivationPacket {
    void encode(FriendlyByteBuf buffer);
    void handle(CultivationPacket.Context context);
    
    ResourceLocation getId();
    
    class Context {
        private final net.minecraft.world.level.Level level;
        private final net.minecraft.world.entity.player.Player player;
        
        public Context(net.minecraft.world.level.Level level, net.minecraft.world.entity.player.Player player) {
            this.level = level;
            this.player = player;
        }
        
        public net.minecraft.world.level.Level getLevel() {
            return level;
        }
        
        public net.minecraft.world.entity.player.Player getPlayer() {
            return player;
        }
    }
}