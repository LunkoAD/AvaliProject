package com.lunkoashtail.avaliproject.compat.ae2;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

public class AE2Compat {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        LOGGER.info("Applied Energistics 2 detected - Nanoloom is accessible via ME Interface/IO Port through its vanilla item handler capability.");
        LOGGER.info("Applied Energistics 2 detected - Sync crystals can be shattered in half as a way of making AE2 quantum entangled singularities! ");
    }
}
