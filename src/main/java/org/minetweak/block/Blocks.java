package org.minetweak.block;

/**
 * Incomplete Representations of Blocks as a whole
 */
public enum Blocks {
    AIR(0),
    STONE(1);

    int id;

    Blocks(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
