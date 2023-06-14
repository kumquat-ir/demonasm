package hellfall.zeroconfig;

import java.util.function.Supplier;

/**
 * An enum to represent a config option that can have the value "auto".
 * <p>
 * Either true/false or on/off can be used to specify static values
 * </p>
 */
@SuppressWarnings("unused")
public enum Trilean {
    AUTO,
    TRUE, ON,
    FALSE, OFF;

    private Supplier<Boolean> resolver = null;

    /**
     * Sets a method to invoke to resolve AUTO to true or false.
     * When used in a config class, this should be called in its static constructor.
     * @param resolver The supplier to invoke
     */
    public void setResolver(Supplier<Boolean> resolver) {
        this.resolver = resolver;
    }

    /**
     * If this is set to AUTO, return the provided default value. Otherwise, return if this is true or false.
     * @param def The default value
     * @return The resolved value of this Trilean
     */
    public boolean resolve(boolean def) {
        if (this == AUTO) return def;
        return this == ON || this == TRUE;
    }

    /**
     * If this is set to AUTO, invoke the supplier set by {@link #setResolver}.
     * Otherwise, return if this is true or false.
     * If the resolver was not set, this method will return false.
     * @return The resolved value of this Trilean
     * @see #resolve(boolean)
     */
    public boolean resolve() {
        if (resolver != null && this == AUTO) return resolver.get();
        return this == ON || this == TRUE;
    }
}
