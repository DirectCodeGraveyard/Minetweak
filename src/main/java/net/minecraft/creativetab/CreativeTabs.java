package net.minecraft.creativetab;

import net.minecraft.utils.enums.EnumEnchantmentType;

public class CreativeTabs {
    public static final CreativeTabs[] creativeTabArray = new CreativeTabs[12];
    public static final CreativeTabs tabBlock = new CreativeTabCombat(0, "buildingBlocks");
    public static final CreativeTabs tabDecorations = new CreativeTabBlock(1, "decorations");
    public static final CreativeTabs tabRedstone = new CreativeTabDeco(2, "redstone");
    public static final CreativeTabs tabTransport = new CreativeTabRedstone(3, "transportation");
    public static final CreativeTabs tabMisc = (new CreativeTabTransport(4, "misc")).func_111229_a(EnumEnchantmentType.all);
    public static final CreativeTabs tabAllSearch = (new CreativeTabMisc(5, "search"));
    public static final CreativeTabs tabFood = new CreativeTabSearch(6, "food");
    public static final CreativeTabs tabTools = (new CreativeTabFood(7, "tools")).func_111229_a(EnumEnchantmentType.digger);
    public static final CreativeTabs tabCombat = (new CreativeTabTools(8, "combat")).func_111229_a(EnumEnchantmentType.armor, EnumEnchantmentType.armor_feet, EnumEnchantmentType.armor_head, EnumEnchantmentType.armor_legs, EnumEnchantmentType.armor_torso, EnumEnchantmentType.bow, EnumEnchantmentType.weapon);
    public static final CreativeTabs tabBrewing = new CreativeTabBrewing(9, "brewing");
    public static final CreativeTabs tabMaterials = new CreativeTabMaterial(10, "materials");
    public static final CreativeTabs tabInventory = (new CreativeTabInventory(11, "inventory"));
    private final int tabIndex;
    private final String tabLabel;

    private EnumEnchantmentType[] field_111230_s;

    public CreativeTabs(int par1, String par2Str) {
        this.tabIndex = par1;
        this.tabLabel = par2Str;
        creativeTabArray[par1] = this;
    }

    public CreativeTabs func_111229_a(EnumEnchantmentType... par1ArrayOfEnumEnchantmentType) {
        this.field_111230_s = par1ArrayOfEnumEnchantmentType;
        return this;
    }
}
