package net.mcreator.sword.sect;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.*;

public class SectData extends SavedData {
    private static final String DATA_NAME = "sword_sect_data";
    
    private Map<String, Sect> sects = new HashMap<>();
    private Map<UUID, String> playerSects = new HashMap<>();
    
    public static SectData get(MinecraftServer server) {
        DimensionDataStorage storage = server.overworld().getDataStorage();
        return storage.computeIfAbsent(SectData::load, SectData::new, DATA_NAME);
    }
    
    public SectData() {
    }
    
    public static SectData load(CompoundTag tag) {
        SectData data = new SectData();
        
        CompoundTag sectsTag = tag.getCompound("Sects");
        for (String key : sectsTag.getAllKeys()) {
            CompoundTag sectTag = sectsTag.getCompound(key);
            Sect sect = Sect.fromNBT(sectTag);
            data.sects.put(key, sect);
        }
        
        CompoundTag playerSectsTag = tag.getCompound("PlayerSects");
        for (String key : playerSectsTag.getAllKeys()) {
            data.playerSects.put(UUID.fromString(key), playerSectsTag.getString(key));
        }
        
        return data;
    }
    
    @Override
    public CompoundTag save(CompoundTag tag) {
        CompoundTag sectsTag = new CompoundTag();
        for (Map.Entry<String, Sect> entry : sects.entrySet()) {
            sectsTag.put(entry.getKey(), entry.getValue().toNBT());
        }
        tag.put("Sects", sectsTag);
        
        CompoundTag playerSectsTag = new CompoundTag();
        for (Map.Entry<UUID, String> entry : playerSects.entrySet()) {
            playerSectsTag.putString(entry.getKey().toString(), entry.getValue());
        }
        tag.put("PlayerSects", playerSectsTag);
        
        return tag;
    }
    
    public Sect createSect(String name, UUID founder) {
        if (sects.containsKey(name)) {
            return null;
        }
        
        Sect sect = new Sect(name, founder);
        sects.put(name, sect);
        playerSects.put(founder, name);
        setDirty();
        return sect;
    }
    
    public boolean joinSect(String sectName, UUID player) {
        Sect sect = sects.get(sectName);
        if (sect == null) return false;
        
        if (playerSects.containsKey(player)) {
            leaveSect(player);
        }
        
        sect.addMember(player);
        playerSects.put(player, sectName);
        setDirty();
        return true;
    }
    
    public void leaveSect(UUID player) {
        String sectName = playerSects.get(player);
        if (sectName != null) {
            Sect sect = sects.get(sectName);
            if (sect != null) {
                sect.removeMember(player);
                if (sect.getMembers().isEmpty()) {
                    sects.remove(sectName);
                }
            }
            playerSects.remove(player);
            setDirty();
        }
    }
    
    public Sect getSect(String name) {
        return sects.get(name);
    }
    
    public Sect getPlayerSect(UUID player) {
        String sectName = playerSects.get(player);
        return sectName != null ? sects.get(sectName) : null;
    }
    
    public Collection<Sect> getAllSects() {
        return sects.values();
    }
    
    public static class Sect {
        private String name;
        private UUID founder;
        private Set<UUID> members = new HashSet<>();
        private Set<UUID> elders = new HashSet<>();
        private int level = 1;
        private int reputation = 0;
        
        public Sect(String name, UUID founder) {
            this.name = name;
            this.founder = founder;
            this.members.add(founder);
        }
        
        public static Sect fromNBT(CompoundTag tag) {
            String name = tag.getString("Name");
            UUID founder = tag.getUUID("Founder");
            Sect sect = new Sect(name, founder);
            
            ListTag membersTag = tag.getList("Members", Tag.TAG_STRING);
            for (int i = 0; i < membersTag.size(); i++) {
                sect.members.add(UUID.fromString(membersTag.getString(i)));
            }
            
            ListTag eldersTag = tag.getList("Elders", Tag.TAG_STRING);
            for (int i = 0; i < eldersTag.size(); i++) {
                sect.elders.add(UUID.fromString(eldersTag.getString(i)));
            }
            
            sect.level = tag.getInt("Level");
            sect.reputation = tag.getInt("Reputation");
            
            return sect;
        }
        
        public CompoundTag toNBT() {
            CompoundTag tag = new CompoundTag();
            tag.putString("Name", name);
            tag.putUUID("Founder", founder);
            
            ListTag membersTag = new ListTag();
            for (UUID member : members) {
                membersTag.add(StringTag.valueOf(member.toString()));
            }
            tag.put("Members", membersTag);
            
            ListTag eldersTag = new ListTag();
            for (UUID elder : elders) {
                eldersTag.add(StringTag.valueOf(elder.toString()));
            }
            tag.put("Elders", eldersTag);
            
            tag.putInt("Level", level);
            tag.putInt("Reputation", reputation);
            
            return tag;
        }
        
        public void addMember(UUID player) {
            members.add(player);
        }
        
        public void removeMember(UUID player) {
            members.remove(player);
            elders.remove(player);
        }
        
        public void promoteToElder(UUID player) {
            if (members.contains(player)) {
                elders.add(player);
            }
        }
        
        public void demoteElder(UUID player) {
            elders.remove(player);
        }
        
        public String getName() { return name; }
        public UUID getFounder() { return founder; }
        public Set<UUID> getMembers() { return members; }
        public Set<UUID> getElders() { return elders; }
        public int getLevel() { return level; }
        public int getReputation() { return reputation; }
        
        public boolean isFounder(UUID player) {
            return founder.equals(player);
        }
        
        public boolean isElder(UUID player) {
            return elders.contains(player);
        }
        
        public boolean isMember(UUID player) {
            return members.contains(player);
        }
    }
}
