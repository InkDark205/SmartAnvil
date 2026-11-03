package inkdark.smartanvil.anvil;

/** Guard rails shared by the future screen automation loop. */
public final class AnvilSafety {
    private AnvilSafety() {}

    public static boolean mayTakeResult(boolean resultPresent, boolean operationValid,
                                        boolean enoughExperience, boolean requireEnoughExperience) {
        if (!resultPresent || !operationValid) return false;
        return !requireEnoughExperience || enoughExperience;
    }

    public static boolean shouldPauseForStorage(boolean chestMode, boolean chestAccessible) {
        return chestMode && !chestAccessible;
    }
}
