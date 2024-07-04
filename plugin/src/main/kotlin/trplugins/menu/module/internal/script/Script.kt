package trplugins.menu.module.internal.script

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.function.adaptPlayer
import trplugins.menu.TrMenu.actionHandle
import trplugins.menu.api.action.base.ActionEntry
import trplugins.menu.module.display.MenuSession
import trplugins.menu.util.EvalResult

/**
 * TrMenu
 * trmenu.module.internal.script.Script
 *
 * @author Score2
 * @since 2022/01/09 17:48
 */

fun EvalResult.asItemStack(): ItemStack? {
    return any as ItemStack?
}

// in Condition.Companion
fun MenuSession.evalScript(script: String?): EvalResult {
    return placeholderPlayer.evalScript(script)
}

// in Condition
fun String.evalScript(session: MenuSession): EvalResult {
    return if (isEmpty()) EvalResult.TRUE
    else session.placeholderPlayer.evalScript(this)
}

fun ProxyPlayer.evalScript(script: String?) =
    cast<Player>().evalScript(script)

fun Player.evalScript(script: String?): EvalResult {
    script ?: return EvalResult(null)
    val action = ActionEntry.of(actionHandle,script)
    return EvalResult(actionHandle.runAction(adaptPlayer(this),action))
}