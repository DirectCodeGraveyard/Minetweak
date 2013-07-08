package org.minetweak.block;

/**
 * Represents the face of a block
 */
public enum TweakBlockFace {
    NORTH(0, 0, -1),
    EAST(1, 0, 0),
    SOUTH(0, 0, 1),
    WEST(-1, 0, 0),
    UP(0, 1, 0),
    DOWN(0, -1, 0),
    NORTH_EAST(NORTH, EAST),
    NORTH_WEST(NORTH, WEST),
    SOUTH_EAST(SOUTH, EAST),
    SOUTH_WEST(SOUTH, WEST),
    WEST_NORTH_WEST(WEST, NORTH_WEST),
    NORTH_NORTH_WEST(NORTH, NORTH_WEST),
    NORTH_NORTH_EAST(NORTH, NORTH_EAST),
    EAST_NORTH_EAST(EAST, NORTH_EAST),
    EAST_SOUTH_EAST(EAST, SOUTH_EAST),
    SOUTH_SOUTH_EAST(SOUTH, SOUTH_EAST),
    SOUTH_SOUTH_WEST(SOUTH, SOUTH_WEST),
    WEST_SOUTH_WEST(WEST, SOUTH_WEST),
    SELF(0, 0, 0);

    private final int modX;
    private final int modY;
    private final int modZ;

    private TweakBlockFace(final int modX, final int modY, final int modZ) {
        this.modX = modX;
        this.modY = modY;
        this.modZ = modZ;
    }

    private TweakBlockFace(final TweakBlockFace face1, final TweakBlockFace face2) {
        this.modX = face1.getModX() + face2.getModX();
        this.modY = face1.getModY() + face2.getModY();
        this.modZ = face1.getModZ() + face2.getModZ();
    }

    /**
     * Get the amount of X-coordinates to modify to get the represented block
     *
     * @return Amount of X-coordinates to modify
     */
    public int getModX() {
        return modX;
    }

    /**
     * Get the amount of Y-coordinates to modify to get the represented block
     *
     * @return Amount of Y-coordinates to modify
     */
    public int getModY() {
        return modY;
    }

    /**
     * Get the amount of Z-coordinates to modify to get the represented block
     *
     * @return Amount of Z-coordinates to modify
     */
    public int getModZ() {
        return modZ;
    }

    public TweakBlockFace getOppositeFace() {
        switch (this) {
            case NORTH:
                return TweakBlockFace.SOUTH;

            case SOUTH:
                return TweakBlockFace.NORTH;

            case EAST:
                return TweakBlockFace.WEST;

            case WEST:
                return TweakBlockFace.EAST;

            case UP:
                return TweakBlockFace.DOWN;

            case DOWN:
                return TweakBlockFace.UP;

            case NORTH_EAST:
                return TweakBlockFace.SOUTH_WEST;

            case NORTH_WEST:
                return TweakBlockFace.SOUTH_EAST;

            case SOUTH_EAST:
                return TweakBlockFace.NORTH_WEST;

            case SOUTH_WEST:
                return TweakBlockFace.NORTH_EAST;

            case WEST_NORTH_WEST:
                return TweakBlockFace.EAST_SOUTH_EAST;

            case NORTH_NORTH_WEST:
                return TweakBlockFace.SOUTH_SOUTH_EAST;

            case NORTH_NORTH_EAST:
                return TweakBlockFace.SOUTH_SOUTH_WEST;

            case EAST_NORTH_EAST:
                return TweakBlockFace.WEST_SOUTH_WEST;

            case EAST_SOUTH_EAST:
                return TweakBlockFace.WEST_NORTH_WEST;

            case SOUTH_SOUTH_EAST:
                return TweakBlockFace.NORTH_NORTH_WEST;

            case SOUTH_SOUTH_WEST:
                return TweakBlockFace.NORTH_NORTH_EAST;

            case WEST_SOUTH_WEST:
                return TweakBlockFace.EAST_NORTH_EAST;

            case SELF:
                return TweakBlockFace.SELF;
        }

        return TweakBlockFace.SELF;
    }
}