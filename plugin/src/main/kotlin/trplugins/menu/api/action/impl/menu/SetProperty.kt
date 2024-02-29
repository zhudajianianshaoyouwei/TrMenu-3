package trplugins.menu.api.action.impl.menu

import org.bukkit.inventory.InventoryView
import taboolib.common.platform.ProxyPlayer
import trplugins.menu.api.action.ActionHandle
import trplugins.menu.api.action.base.ActionBase
import trplugins.menu.api.action.base.ActionContents
import trplugins.menu.module.display.session

/**
 * TrMenu
 * trplugins.menu.api.action.impl.menu.SetProperty
 *
 * @author Rubenicos
 * @since 2024/02/29 08:42
 */
class SetProperty(handle: ActionHandle) : ActionBase(handle) {

    override val regex = "set-?property".toRegex()

    override fun onExecute(contents: ActionContents, player: ProxyPlayer, placeholderPlayer: ProxyPlayer) {
        val session = player.session()
        val receptacle = session.receptacle ?: return

        val args = contents.stringContent().parseContent(placeholderPlayer).split(" ", limit = 2)
        if (args.size < 2) return

        val name = args[0].replace('-', '_')
        val id = name.toIntOrNull() ?: (InventoryView.Property.entries.find { it.name.equals(name, ignoreCase = true) } ?: return).id
        val value = args[1].toIntOrNull() ?: return

        receptacle.property(id, value)
    }

}