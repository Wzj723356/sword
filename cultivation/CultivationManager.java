package net.mcreator.sword.cultivation;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.mcreator.sword.SwordMod;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CultivationManager {
    private static final String CULTIVATION_DATA_KEY = SwordMod.MODID + ":cultivation_data";
    private static final String SAVED_DATA_NAME = SwordMod.MODID + ":cultivation_saved_data";

    public static class CultivationSavedData extends SavedData {
        private final Map<UUID, CompoundTag> playerData = new HashMap<>();

        public static CultivationSavedData load(CompoundTag tag) {
            CultivationSavedData data = new CultivationSavedData();
            for (String key : tag.getAllKeys()) {
                data.playerData.put(UUID.fromString(key), tag.getCompound(key));
            }
            return data;
        }

        @Override
        public CompoundTag save(CompoundTag tag) {
            for (Map.Entry<UUID, CompoundTag> entry : playerData.entrySet()) {
                tag.put(entry.getKey().toString(), entry.getValue());
            }
            return tag;
        }

        public CompoundTag getPlayerData(UUID uuid) {
            return playerData.computeIfAbsent(uuid, k -> new CompoundTag());
        }

        public void setPlayerData(UUID uuid, CompoundTag data) {
            playerData.put(uuid, data);
            setDirty();
        }
    }

    private static CultivationSavedData getSavedData(LevelAccessor level) {
        if (level instanceof ServerLevel serverLevel) {
            return serverLevel.getDataStorage().computeIfAbsent(
                CultivationSavedData::load,
                CultivationSavedData::new,
                SAVED_DATA_NAME
            );
        }
        return null;
    }

    public static CultivationData getCultivationData(Player player) {
        CultivationSavedData savedData = getSavedData(player.level());
        if (savedData == null) {
            CultivationData newData = new CultivationData();
            newData.setSpiritualRoot(SpiritualRoot.randomRoot(player.getRandom()));
            newData.setHasStartedCultivation(true);
            return newData;
        }

        CompoundTag playerData = savedData.getPlayerData(player.getUUID());
        
        if (!playerData.contains(CULTIVATION_DATA_KEY)) {
            CultivationData newData = new CultivationData();
            newData.setSpiritualRoot(SpiritualRoot.randomRoot(player.getRandom()));
            newData.setHasStartedCultivation(true);
            playerData.put(CULTIVATION_DATA_KEY, newData.toNBT());
            savedData.setPlayerData(player.getUUID(), playerData);
            return newData;
        }
        
        CultivationData data = new CultivationData();
        data.fromNBT(playerData.getCompound(CULTIVATION_DATA_KEY));
        if (!data.hasStartedCultivation() && data.getSpiritualRoot() != SpiritualRoot.NONE) {
            data.setHasStartedCultivation(true);
            saveCultivationData(player, data);
        }
        return data;
    }

    public static void saveCultivationData(Player player, CultivationData data) {
        CultivationSavedData savedData = getSavedData(player.level());
        if (savedData != null) {
            CompoundTag playerData = savedData.getPlayerData(player.getUUID());
            playerData.put(CULTIVATION_DATA_KEY, data.toNBT());
            savedData.setPlayerData(player.getUUID(), playerData);
        }
    }

    public static void addExperience(Player player, int amount) {
        CultivationData data = getCultivationData(player);
        data.addExp(amount);
        saveCultivationData(player, data);
        
        if (player instanceof ServerPlayer serverPlayer) {
            syncToClient(serverPlayer);
        }
    }

    public static boolean checkLevelUp(Player player) {
        CultivationData data = getCultivationData(player);
        boolean leveledUp = data.checkLevelUp();
        if (leveledUp) {
            saveCultivationData(player, data);
            if (player instanceof ServerPlayer serverPlayer) {
                syncToClient(serverPlayer);
            }
        }
        return leveledUp;
    }

    public static void syncToClient(ServerPlayer player) {
        net.mcreator.sword.network.CultivationPacketHandler.sendCultivationDataToClient(player);
    }
}
