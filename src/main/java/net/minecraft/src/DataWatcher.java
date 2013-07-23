package net.minecraft.src;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.packet.Packet;
import net.minecraft.world.chunk.ChunkCoordinates;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DataWatcher {
    /**
     * When isBlank is true the DataWatcher is not watching any objects
     */
    private boolean isBlank = true;
    private static final HashMap<AnnotatedElement, Integer> dataTypes = new HashMap<AnnotatedElement, Integer>();
    private final Map<Number, WatchableObject> watchedObjects = new HashMap<Number, WatchableObject>();

    /**
     * true if one or more object was changed
     */
    private boolean objectChanged;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * adds a new object to dataWatcher to watch, to update an already existing object see updateObject. Arguments: data
     * Value Id, Object to add
     */
    public void addObject(int par1, Serializable par2Obj) {
        Integer var3 = dataTypes.get(par2Obj.getClass());

        if (var3 == null) {
            throw new IllegalArgumentException("Unknown data type: " + par2Obj.getClass());
        } else if (par1 > 31) {
            throw new IllegalArgumentException("Data value id is too big with " + par1 + "! (Max is " + 31 + ")");
        } else if (this.watchedObjects.containsKey(par1)) {
            throw new IllegalArgumentException("Duplicate id value for " + par1 + "!");
        } else {
            WatchableObject var4 = new WatchableObject(var3, par1, par2Obj);
            this.lock.writeLock().lock();
            this.watchedObjects.put(par1, var4);
            this.lock.writeLock().unlock();
            this.isBlank = false;
        }
    }

    /**
     * Add a new object for the DataWatcher to watch, using the specified data type.
     */
    public void addObjectByDataType(int par1, int par2) {
        WatchableObject var3 = new WatchableObject(par2, par1, null);
        this.lock.writeLock().lock();
        this.watchedObjects.put(par1, var3);
        this.lock.writeLock().unlock();
        this.isBlank = false;
    }

    /**
     * gets the bytevalue of a watchable object
     */
    public byte getWatchableObjectByte(int par1) {
        return (Byte) this.getWatchedObject(par1).getObject();
    }

    public short getWatchableObjectShort(int par1) {
        return (Short) this.getWatchedObject(par1).getObject();
    }

    /**
     * gets a watchable object and returns it as a Integer
     */
    public int getWatchableObjectInt(int par1) {
        return (Integer) this.getWatchedObject(par1).getObject();
    }

    public float func_111145_d(int par1) {
        return (Float) this.getWatchedObject(par1).getObject();
    }

    /**
     * gets a watchable object and returns it as a String
     */
    public String getWatchableObjectString(int par1) {
        return (String) this.getWatchedObject(par1).getObject();
    }

    /**
     * Get a watchable object as an ItemStack.
     */
    public ItemStack getWatchableObjectItemStack(int par1) {
        return (ItemStack) this.getWatchedObject(par1).getObject();
    }

    /**
     * is threadsafe, unless it throws an exception, then
     */
    private WatchableObject getWatchedObject(int par1) {
        this.lock.readLock().lock();
        WatchableObject var2;

        try {
            var2 = this.watchedObjects.get(par1);
        } catch (Throwable var6) {
            CrashReport var4 = CrashReport.makeCrashReport(var6, "Getting synched entity data");
            CrashReportCategory var5 = var4.makeCategory("Synched entity data");
            var5.addCrashSection("Data ID", par1);
            throw new ReportedException(var4);
        }

        this.lock.readLock().unlock();
        return var2;
    }

    /**
     * updates an already existing object
     */
    public void updateObject(int par1, Object par2Obj) {
        WatchableObject var3 = this.getWatchedObject(par1);

        if (!par2Obj.equals(var3.getObject())) {
            var3.setObject(par2Obj);
            var3.setWatched(true);
            this.objectChanged = true;
        }
    }

    public void setObjectWatched(int par1) {
        WatchableObject.setWatchableObjectWatched(this.getWatchedObject(par1), true);
        this.objectChanged = true;
    }

    /**
     * true if one or more object was changed
     */
    public boolean hasObjectChanged() {
        return this.objectChanged;
    }

    /**
     * writes every object in passed list to dataoutputstream, terminated by 0x7F
     */
    public static void writeObjectsInListToStream(List par0List, DataOutput par1DataOutput) throws IOException {
        if (par0List != null) {

            for (Object aPar0List : par0List) {
                WatchableObject var3 = (WatchableObject) aPar0List;
                writeWatchableObject(par1DataOutput, var3);
            }
        }

        par1DataOutput.writeByte(127);
    }

    public List<WatchableObject> unwatchAndReturnAllWatched() {
        ArrayList<WatchableObject> var1 = null;

        if (this.objectChanged) {
            this.lock.readLock().lock();

            for (WatchableObject var3 : this.watchedObjects.values()) {
                if (var3.isWatched()) {
                    var3.setWatched(false);

                    if (var1 == null) {
                        var1 = new ArrayList<WatchableObject>();
                    }

                    var1.add(var3);
                }
            }

            this.lock.readLock().unlock();
        }

        this.objectChanged = false;
        return var1;
    }

    public void writeWatchableObjects(DataOutput par1DataOutput) throws IOException {
        this.lock.readLock().lock();

        for (WatchableObject var3 : this.watchedObjects.values()) {
            writeWatchableObject(par1DataOutput, var3);
        }

        this.lock.readLock().unlock();
        par1DataOutput.writeByte(127);
    }

    public List<WatchableObject> getAllWatched() {
        ArrayList<WatchableObject> var1 = null;
        this.lock.readLock().lock();
        WatchableObject var3;

        for (Iterator<WatchableObject> var2 = this.watchedObjects.values().iterator(); var2.hasNext(); var1.add(var3)) {
            var3 = var2.next();

            if (var1 == null) {
                var1 = new ArrayList<WatchableObject>();
            }
        }

        this.lock.readLock().unlock();
        return var1;
    }

    private static void writeWatchableObject(DataOutput par0DataOutput, WatchableObject par1WatchableObject) throws IOException {
        int var2 = (par1WatchableObject.getObjectType() << 5 | par1WatchableObject.getDataValueId() & 31) & 255;
        par0DataOutput.writeByte(var2);

        switch (par1WatchableObject.getObjectType()) {
            case 0:
                par0DataOutput.writeByte((Byte) par1WatchableObject.getObject());
                break;

            case 1:
                par0DataOutput.writeShort((Short) par1WatchableObject.getObject());
                break;

            case 2:
                par0DataOutput.writeInt((Integer) par1WatchableObject.getObject());
                break;

            case 3:
                par0DataOutput.writeFloat((Float) par1WatchableObject.getObject());
                break;

            case 4:
                Packet.writeString((String) par1WatchableObject.getObject(), par0DataOutput);
                break;

            case 5:
                ItemStack var4 = (ItemStack) par1WatchableObject.getObject();
                Packet.writeItemStack(var4, par0DataOutput);
                break;

            case 6:
                ChunkCoordinates var3 = (ChunkCoordinates) par1WatchableObject.getObject();
                par0DataOutput.writeInt(var3.posX);
                par0DataOutput.writeInt(var3.posY);
                par0DataOutput.writeInt(var3.posZ);
        }
    }

    public static List<WatchableObject> readWatchableObjects(DataInput par0DataInput) throws IOException {
        ArrayList<WatchableObject> var1 = null;

        for (byte var2 = par0DataInput.readByte(); var2 != 127; var2 = par0DataInput.readByte()) {
            if (var1 == null) {
                var1 = new ArrayList<WatchableObject>();
            }

            int var3 = (var2 & 224) >> 5;
            int var4 = var2 & 31;
            WatchableObject var5 = null;

            switch (var3) {
                case 0:
                    var5 = new WatchableObject(var3, var4, par0DataInput.readByte());
                    break;

                case 1:
                    var5 = new WatchableObject(var3, var4, par0DataInput.readShort());
                    break;

                case 2:
                    var5 = new WatchableObject(var3, var4, par0DataInput.readInt());
                    break;

                case 3:
                    var5 = new WatchableObject(var3, var4, par0DataInput.readFloat());
                    break;

                case 4:
                    var5 = new WatchableObject(var3, var4, Packet.readString(par0DataInput, 64));
                    break;

                case 5:
                    var5 = new WatchableObject(var3, var4, Packet.readItemStack(par0DataInput));
                    break;

                case 6:
                    int var6 = par0DataInput.readInt();
                    int var7 = par0DataInput.readInt();
                    int var8 = par0DataInput.readInt();
                    var5 = new WatchableObject(var3, var4, new ChunkCoordinates(var6, var7, var8));
            }

            var1.add(var5);
        }

        return var1;
    }

    public boolean getIsBlank() {
        return this.isBlank;
    }

    public void func_111144_e() {
        this.objectChanged = false;
    }

    static {
        dataTypes.put(Byte.class, 0);
        dataTypes.put(Short.class, 1);
        dataTypes.put(Integer.class, 2);
        dataTypes.put(Float.class, 3);
        dataTypes.put(String.class, 4);
        dataTypes.put(ItemStack.class, 5);
        dataTypes.put(ChunkCoordinates.class, 6);
    }
}
