package trplugins.menu.api.action.impl.menu

import taboolib.common.platform.ProxyPlayer
import trplugins.menu.api.action.ActionHandle
import trplugins.menu.api.action.base.ActionBase
import trplugins.menu.api.action.base.ActionContents
import trplugins.menu.module.display.MenuSession
import trplugins.menu.module.display.session

/**
 * @author Rubenicos
 * @date 2024/6/14 10:24
 */
class UpdateLang(handle: ActionHandle) : ActionBase(handle) {

    override val regex = "update-?lang".toRegex()

    override fun onExecute(contents: ActionContents, player: ProxyPlayer, placeholderPlayer: ProxyPlayer) {
        val session = player.session()
        if (MenuSession.langPlayer.isBlank()) {
            session.locale = player.locale
        } else {
            session.locale = session.parse(MenuSession.langPlayer)
        }
    }

}