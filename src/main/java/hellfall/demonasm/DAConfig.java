package hellfall.demonasm;

import hellfall.zeroconfig.Config;
import hellfall.zeroconfig.ZeroConfig;

@Config.File("config/demonasm.css")
@Config.DefaultBoolStyle(ZeroConfig.BoolStyle.ON_OFF)
@Config.DefaultCategory("tweaks")
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

    @Config.Key("headRenderingFix")
    @Config.Comment("Fix player head rendering with Ears installed\n" +
            "This will cause rendering issues when Ears is not installed")
    @Config.Category("fixes")
    public static boolean fixHeadRendering = true;

    @Config.Key("greggy_greg_kindly_stuff_a_sock_in_it")
    @Config.Comment("Stop GT4 from yelling about Ore Prefixes in the log")
    @Config.Category("shutup")
    public static boolean shutupGT = true;

    @Config.Key("quietRuins")
    @Config.Comment("Ruins can spam the log really hard sometimes, make it not")
    @Config.Category("shutup")
    public static boolean shutupRuins = true;
}
