package hellfall.demonasm;

import hellfall.zeroconfig.Config;
import hellfall.zeroconfig.ZeroConfig;

@Config.File("config/demonasm.css")
@Config.DefaultBoolStyle(ZeroConfig.BoolStyle.ON_OFF)
@Config.DefaultCategory("tweaks")
@Config.Comment.AppendDefault(true)
public class DAConfig {
    @Config.Key("addNilmods")
    @Config.Comment("Add Nilmods to the mod list")
    public static boolean addToModList = true;

    @Config.Key("iguanaSkullContainerRemove")
    @Config.Comment("Removes the container item from Iguana Tweaks skulls")
    public static boolean removeIguanaSkullContainer = true;

    @Config.Key("seerSigilFix")
    @Config.Comment("Make the Blood Magic Seer Sigil a strict upgrade to the Divination Sigil")
    public static boolean fixSeerSigil = true;

    @Config.Key("corporeaBookmarks")
    @Config.Comment("Make Corporea requesting request the amount set in bookmarks if control+shift is held\n" +
            "If the output of a recipe is requested, all ingredients with an amount will be requested instead")
    public static boolean corporeaBookmarks = true;

    @Config.Key("corporeaIncludeNoAmount")
    @Config.Comment("Also request 1 of ingredients without an amount when requesting a bookmarked recipe")
    public static boolean corporeaIncludeNoAmount = false;

    @Config.Key("headRenderingFix")
    @Config.Comment("Fix player head rendering with Ears installed\n" +
            "This will cause rendering issues when Ears is not installed\n" +
            "Also fixes rendering of some mobs that use the player skin")
    @Config.Category("fixes")
    public static boolean fixHeadRendering = true;

    @Config.Key("greggy_greg_do_please_kindly_stuff_a_sock_in_it")
    @Config.Comment("Stop GT4 from yelling about Oredicts in the log")
    @Config.Category("shutup")
    public static boolean shutupGT = true;

    @Config.Key("quietRuins")
    @Config.Comment("Ruins can spam the log really hard sometimes, make it not [NYI]")
    @Config.Category("shutup")
    public static boolean shutupRuins = true;

    static {
        ZeroConfig.crossClassloader(DAConfig.class);
    }
}
