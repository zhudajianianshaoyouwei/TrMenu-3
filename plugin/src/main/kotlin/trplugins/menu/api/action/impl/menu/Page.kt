package trplugins.menu.api.action.impl.menu

import taboolib.common.platform.ProxyPlayer
import trplugins.menu.api.action.ActionHandle
import trplugins.menu.api.action.base.ActionBase
import trplugins.menu.api.action.base.ActionContents
import trplugins.menu.module.display.session
import kotlin.math.max
import kotlin.math.min

/**
 * TrMenu
 * trplugins.menu.api.action.impl.inventory.Page
 *
 * @author Score2
 * @since 2022/02/14 11:14
 */
class Page(handle: ActionHandle) : ActionBase(handle) {

    override val regex = "(set|switch)?-?(layout|shape|page)s?".toRegex()

    override fun onExecute(contents: ActionContents, player: ProxyPlayer, placeholderPlayer: ProxyPlayer) {
        val session = player.session()
        val menu = session.menu ?: return
        val split = contents.stringContent().parseContent(placeholderPlayer).split(' ', limit = 2)
        val page = min(split[0].toIntOrNull() ?: 0, menu.layout.getSize() - 1)

        menu.page(player.cast(), max(0, page), if (split.size > 1) split[1] else null)
    }

}