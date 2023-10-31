package trplugins.menu.api.event

import taboolib.platform.type.BukkitProxyEvent
import trplugins.menu.module.display.MenuSession

/**
 * @author Arasple
 * @date 2021/1/29 17:34
 */
class MenuCloseEvent(val session: MenuSession) : BukkitProxyEvent()