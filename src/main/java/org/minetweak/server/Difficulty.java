package org.minetweak.server;

public enum Difficulty {
    PEACEFUL(0, "peaceful"),
    EASY(1, "easy"),
    NORMAL(2, "normal"),
    HARD(3, "hard");

    String name;
    int id;

    private Difficulty(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public static Difficulty getByID(int id) {
        Difficulty[] difficulties = values();
        for (Difficulty difficulty : difficulties) {
            if (difficulty.id == id) {
                return difficulty;
            }
        }
        return NORMAL;
    }

    public static Difficulty getByName(String name) {
        Difficulty[] difficulties = values();
        for (Difficulty difficulty : difficulties) {
            if (difficulty.name.equals(name)) {
                return difficulty;
            }
        }
        return NORMAL;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public boolean isPeaceful() {
        return this.id == PEACEFUL.id;
    }

    public boolean isHard() {
        return this.id == HARD.id;
    }

    public boolean isNormal() {
        return this.id == NORMAL.id;
    }

    public boolean isEasy() {
        return this.id == EASY.id;
    }
}
