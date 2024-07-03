package trplugins.menu.api.action.impl.script

import taboolib.common.platform.ProxyPlayer
import trplugins.menu.api.action.ActionHandle
import trplugins.menu.api.action.base.ActionBase
import trplugins.menu.api.action.base.ActionContents
import trplugins.menu.module.display.session
import trplugins.menu.module.internal.script.jexl.JexlAgent

class Jexl(handle: ActionHandle) : ActionBase(handle) {

    override val regex = "jexl".toRegex()

    override fun readContents(contents: Any): ActionContents {
        JexlAgent.preCompile(contents.toString())
        return super.readContents(contents)
    }

    override fun onExecute(contents: ActionContents, player: ProxyPlayer, placeholderPlayer: ProxyPlayer) {
        JexlAgent.eval(player.session(), contents.stringContent())
    }

}