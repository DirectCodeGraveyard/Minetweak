package net.minecraft.player.score;

import java.util.*;

public class Scoreboard {
    /**
     * Map of objective names to ScoreObjective objects.
     */
    private final Map<String, ScoreObjective> scoreObjectives = new HashMap<String, ScoreObjective>();
    private final Map<ScoreObjectiveCriteria, ArrayList<ScoreObjective>> field_96543_b = new HashMap<ScoreObjectiveCriteria, ArrayList<ScoreObjective>>();
    private final Map<String, Map<ScoreObjective, Score>> field_96544_c = new HashMap<String, Map<ScoreObjective, Score>>();
    private final ScoreObjective[] field_96541_d = new ScoreObjective[3];
    private final Map<String, ScorePlayerTeam> field_96542_e = new HashMap<String, ScorePlayerTeam>();

    /**
     * Map of usernames to ScorePlayerTeam objects.
     */
    private final Map<String, ScorePlayerTeam> teamMemberships = new HashMap<String, ScorePlayerTeam>();

    /**
     * Returns a ScoreObjective for the objective name
     */
    public ScoreObjective getObjective(String par1Str) {
        return this.scoreObjectives.get(par1Str);
    }

    public ScoreObjective func_96535_a(String par1Str, ScoreObjectiveCriteria par2ScoreObjectiveCriteria) {
        ScoreObjective var3 = this.getObjective(par1Str);

        if (var3 != null) {
            throw new IllegalArgumentException("An objective with the name \'" + par1Str + "\' already exists!");
        } else {
            var3 = new ScoreObjective(this, par1Str, par2ScoreObjectiveCriteria);
            ArrayList<ScoreObjective> var4 = this.field_96543_b.get(par2ScoreObjectiveCriteria);

            if (var4 == null) {
                var4 = new ArrayList<ScoreObjective>();
                this.field_96543_b.put(par2ScoreObjectiveCriteria, var4);
            }

            (var4).add(var3);
            this.scoreObjectives.put(par1Str, var3);
            this.func_96522_a(var3);
            return var3;
        }
    }

    public Collection<ScoreObjective> func_96520_a(ScoreObjectiveCriteria par1ScoreObjectiveCriteria) {
        Collection<ScoreObjective> var2 = this.field_96543_b.get(par1ScoreObjectiveCriteria);
        return var2 == null ? new ArrayList<ScoreObjective>() : new ArrayList<ScoreObjective>(var2);
    }

    public Score func_96529_a(String par1Str, ScoreObjective par2ScoreObjective) {
        Map<ScoreObjective, Score> var3 = this.field_96544_c.get(par1Str);

        if (var3 == null) {
            var3 = new HashMap<ScoreObjective, Score>();
            this.field_96544_c.put(par1Str, var3);
        }

        Score var4 = (var3).get(par2ScoreObjective);

        if (var4 == null) {
            var4 = new Score(this, par2ScoreObjective, par1Str);
            (var3).put(par2ScoreObjective, var4);
        }

        return var4;
    }

    public Collection<Score> func_96534_i(ScoreObjective par1ScoreObjective) {
        ArrayList<Score> var2 = new ArrayList<Score>();

        for (Map<ScoreObjective, Score> var4 : this.field_96544_c.values()) {
            Score var5 = var4.get(par1ScoreObjective);

            if (var5 != null) {
                var2.add(var5);
            }
        }

        Collections.sort(var2, Score.field_96658_a);
        return var2;
    }

    public Collection<ScoreObjective> getScoreObjectives() {
        return this.scoreObjectives.values();
    }

    public Collection<String> getObjectiveNames() {
        return this.field_96544_c.keySet();
    }

    public void func_96515_c(String par1Str) {
        Map<ScoreObjective, Score> var2 = this.field_96544_c.remove(par1Str);

        if (var2 != null) {
            this.func_96516_a(par1Str);
        }
    }

    public Collection<Score> func_96528_e() {
        Collection<Map<ScoreObjective, Score>> var1 = this.field_96544_c.values();
        ArrayList<Score> var2 = new ArrayList<Score>();

        for (Map<ScoreObjective, Score> var4 : var1) {
            var2.addAll(var4.values());
        }

        return var2;
    }

    public Map<ScoreObjective, Score> func_96510_d(String par1Str) {
        Map<ScoreObjective, Score> var2 = this.field_96544_c.get(par1Str);

        if (var2 == null) {
            var2 = new HashMap<ScoreObjective, Score>();
        }

        return var2;
    }

    public void func_96519_k(ScoreObjective par1ScoreObjective) {
        this.scoreObjectives.remove(par1ScoreObjective.getName());

        for (int var2 = 0; var2 < 3; ++var2) {
            if (this.func_96539_a(var2) == par1ScoreObjective) {
                this.func_96530_a(var2, null);
            }
        }

        List<ScoreObjective> var5 = this.field_96543_b.get(par1ScoreObjective.getCriteria());

        if (var5 != null) {
            var5.remove(par1ScoreObjective);
        }

        for (Map<ScoreObjective, Score> o : this.field_96544_c.values()) {
            o.remove(par1ScoreObjective);
        }

        this.func_96533_c(par1ScoreObjective);
    }

    public void func_96530_a(int par1, ScoreObjective par2ScoreObjective) {
        this.field_96541_d[par1] = par2ScoreObjective;
    }

    public ScoreObjective func_96539_a(int par1) {
        return this.field_96541_d[par1];
    }

    public ScorePlayerTeam func_96508_e(String par1Str) {
        return this.field_96542_e.get(par1Str);
    }

    public ScorePlayerTeam func_96527_f(String par1Str) {
        ScorePlayerTeam var2 = this.func_96508_e(par1Str);

        if (var2 != null) {
            throw new IllegalArgumentException("An objective with the name \'" + par1Str + "\' already exists!");
        } else {
            var2 = new ScorePlayerTeam(this, par1Str);
            this.field_96542_e.put(par1Str, var2);
            this.func_96523_a(var2);
            return var2;
        }
    }

    public void func_96511_d(ScorePlayerTeam par1ScorePlayerTeam) {
        this.field_96542_e.remove(par1ScorePlayerTeam.func_96661_b());

        for (Object o : par1ScorePlayerTeam.getMembershipCollection()) {
            String var3 = (String) o;
            this.teamMemberships.remove(var3);
        }

        this.func_96513_c(par1ScorePlayerTeam);
    }

    public void func_96521_a(String par1Str, ScorePlayerTeam par2ScorePlayerTeam) {
        if (this.getPlayersTeam(par1Str) != null) {
            this.func_96524_g(par1Str);
        }

        this.teamMemberships.put(par1Str, par2ScorePlayerTeam);
        par2ScorePlayerTeam.getMembershipCollection().add(par1Str);
    }

    public boolean func_96524_g(String par1Str) {
        ScorePlayerTeam var2 = this.getPlayersTeam(par1Str);

        if (var2 != null) {
            this.removePlayerFromTeam(par1Str, var2);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes the given username from the given ScorePlayerTeam. If the player is not on the team then an
     * IllegalStateException is thrown.
     */
    public void removePlayerFromTeam(String par1Str, ScorePlayerTeam par2ScorePlayerTeam) {
        if (this.getPlayersTeam(par1Str) != par2ScorePlayerTeam) {
            throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team \'" + par2ScorePlayerTeam.func_96661_b() + "\'.");
        } else {
            this.teamMemberships.remove(par1Str);
            par2ScorePlayerTeam.getMembershipCollection().remove(par1Str);
        }
    }

    public Collection<String> func_96531_f() {
        return this.field_96542_e.keySet();
    }

    public Collection<ScorePlayerTeam> func_96525_g() {
        return this.field_96542_e.values();
    }

    /**
     * Gets the ScorePlayerTeam object for the given username.
     */
    public ScorePlayerTeam getPlayersTeam(String par1Str) {
        return this.teamMemberships.get(par1Str);
    }

    public void func_96522_a(ScoreObjective par1ScoreObjective) {
    }

    public void func_96532_b(ScoreObjective par1ScoreObjective) {
    }

    public void func_96533_c(ScoreObjective par1ScoreObjective) {
    }

    public void func_96536_a(Score par1Score) {
    }

    public void func_96516_a(String par1Str) {
    }

    public void func_96523_a(ScorePlayerTeam par1ScorePlayerTeam) {
    }

    public void func_96538_b(ScorePlayerTeam par1ScorePlayerTeam) {
    }

    public void func_96513_c(ScorePlayerTeam par1ScorePlayerTeam) {
    }

    /**
     * Returns 'list' for 0, 'sidebar' for 1, 'belowName for 2, otherwise null.
     */
    public static String getObjectiveDisplaySlot(int par0) {
        switch (par0) {
            case 0:
                return "list";
            case 1:
                return "sidebar";
            case 2:
                return "belowName";
            default:
                return null;
        }
    }

    /**
     * Returns 0 for (case-insensitive) 'list', 1 for 'sidebar', 2 for 'belowName', otherwise -1.
     */
    public static int getObjectiveDisplaySlotNumber(String par0Str) {
        return par0Str.equalsIgnoreCase("list") ? 0 : (par0Str.equalsIgnoreCase("sidebar") ? 1 : (par0Str.equalsIgnoreCase("belowName") ? 2 : -1));
    }
}
