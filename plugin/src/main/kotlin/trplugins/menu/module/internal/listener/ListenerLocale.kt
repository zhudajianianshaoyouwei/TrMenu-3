package trplugins.menu.module.internal.listener

import org.bukkit.event.player.PlayerLocaleChangeEvent
import taboolib.common.platform.event.OptionalEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.nms.MinecraftVersion
import trplugins.menu.module.display.MenuSession
import trplugins.menu.module.display.session

/**
 * @author Rubenicos
 * @date 2024/5/29 15:03
 */
object ListenerLocale {

    @SubscribeEvent(bind = "org.bukkit.event.player.PlayerLocaleChangeEvent")
    fun onLocaleChange(e: OptionalEvent) {
        if (MinecraftVersion.majorLegacy >= 11200 && MenuSession.langPlayer.isBlank()) {
            val event = e.get<PlayerLocaleChangeEvent>()
            event.player.session().locale = event.locale
        }
    }

}