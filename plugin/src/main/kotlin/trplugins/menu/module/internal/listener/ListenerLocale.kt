package trplugins.menu.module.internal.listener

import org.bukkit.event.player.PlayerLocaleChangeEvent
import taboolib.common.platform.event.SubscribeEvent
import trplugins.menu.module.display.MenuSession
import trplugins.menu.module.display.session

/**
 * @author Rubenicos
 * @date 2024/5/29 15:03
 */
object ListenerLocale {

    @SubscribeEvent
    fun onLocaleChange(e: PlayerLocaleChangeEvent) {
        if (MenuSession.langPlayer.isBlank()) {
            e.player.session().locale = e.locale
        }
    }

}