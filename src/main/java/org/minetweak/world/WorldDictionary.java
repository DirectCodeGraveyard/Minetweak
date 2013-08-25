package org.minetweak.world;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;

/**
 * Allows a emulation of the Forge Ore Dictionary
 * however it can be used for anything globally
 */
public class WorldDictionary {
    private static Multimap<String, Object> entries = ArrayListMultimap.create();


    /**
     * Registers a Value for an ID
     *
     * @param id    ID
     * @param value Value
     */
    public static void register(String id, Object value) {
        entries.get(id).add(value);
    }

    /**
     * Gets the Entries for an ID
     *
     * @param id id
     * @return entries
     */
    public static Collection<Object> getEntriesFor(String id) {
        return entries.get(id);
    }

    /**
     * Gets all the Entries
     *
     * @return Entries
     */
    public static Multimap<String, Object> getEntries() {
        return entries;
    }
}
