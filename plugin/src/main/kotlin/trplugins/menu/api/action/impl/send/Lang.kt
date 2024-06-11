package trplugins.menu.api.action.impl.send

import taboolib.common.platform.ProxyPlayer
import taboolib.common.util.replaceWithOrder
import taboolib.module.lang.Type
import taboolib.module.lang.getLocaleFile
import trplugins.menu.api.action.ActionHandle;
import trplugins.menu.api.action.base.ActionBase
import trplugins.menu.api.action.base.ActionContents
import trplugins.menu.module.display.session
import trplugins.menu.util.Regexs

/**
 * @author Rubenicos
 * @date 2024/6/10 18:06
 */
class Lang(handle: ActionHandle) : ActionBase(handle) {

    override val regex = "((send|tell)-?)lang(-?(message|msg))".toRegex()

    override fun onExecute(contents: ActionContents, player: ProxyPlayer, placeholderPlayer: ProxyPlayer) {
        val session = player.session()
        val menu = session.menu
        val split = contents.stringContent().parseContent(placeholderPlayer).split(' ', limit = 2)

        var node: Type? = menu?.getLocaleNode(session.locale, split[0])
        if (node == null) {
            val file = player.getLocaleFile()
            if (file != null) {
                node = file.nodes[split[0]]
            }
        }

        if (node == null) {
            player.sendMessage("{" + split[0] + "}")
        } else if (split.size > 1) {
            var arguments = split[1]
            val args: MutableList<String> = ArrayList()
            if (arguments.contains(' ')) {
                if (arguments.contains('`')) {
                    // Replace any "`some text`" with {index} and save value
                    val replacements = Regexs.SENTENCE.findAll(arguments).mapIndexed { index, result ->
                        arguments = arguments.replace(result.value, "{$index}")
                        index to result.groupValues[1].replace("\\s", " ")
                    }.toMap().values.toTypedArray()
                    // Split by space and replace any {index} with its respective indexed value
                    arguments.split(" ").toTypedArray().forEach { s -> args.add(s.replaceWithOrder(*replacements)) }
                } else {
                    args.addAll(arguments.split(" "))
                }
            } else {
                args.add(arguments)
            }

            node.send(player, *args.toTypedArray())
        } else {
            node.send(player)
        }
    }

}
