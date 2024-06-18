package trplugins.menu.api.reaction

import taboolib.common.platform.ProxyPlayer
import trplugins.menu.api.action.ActionHandle
import trplugins.menu.api.action.base.ActionEntry

/**
 * @author Arasple
 * @date 2021/1/29 17:51
 */
data class Reactions(val handle: ActionHandle, private val reacts: MutableList<Reaction>) {

    fun eval(player: ProxyPlayer): Boolean {
        if (isEmpty()) return true

        return handle.runAction(player, getIterator(player))
    }

    fun getIterator(player: ProxyPlayer): Iterator<ActionEntry> {
        if (reacts.isEmpty()) {
            return emptyList<ActionEntry>().iterator()
        }
        val sorted = reacts.sortedBy { it.priority }
        val iterator = object : Iterator<ActionEntry> {
            var position: Int = 0
            var current: Iterator<ActionEntry> = sorted.first().getIterator(player)

            override fun hasNext(): Boolean {
                if (current.hasNext()) {
                    return true
                } else if (position + 1 < sorted.size) {
                    position++
                    current = sorted[position].getIterator(player)
                    return hasNext()
                } else {
                    return false
                }
            }

            override fun next(): ActionEntry {
                return current.next()
            }
        }
        return iterator
    }

    fun isEmpty(): Boolean {
        return reacts.isEmpty() || reacts.all { it.isEmpty() }
    }

    fun andThen(reaction: Reactions): Reactions {
        reaction.reacts.forEach {
            it.priority += reacts.size
            reacts.add(it)
        }
        return this
    }

    fun copyAndThen(reaction: Reactions): Reactions {
        return Reactions(handle, mutableListOf<Reaction>().also { it.addAll(reacts) }).andThen(reaction)
    }

    companion object {
        fun ofReaction(handle: ActionHandle, any: Any?): Reactions {
            val reacts = mutableListOf<Reaction>()
            any ?: return Reactions(handle, reacts)
            if (any is List<*>) {
                var order = 0
                any.filterNotNull().forEach { Reaction.of(handle, order++, it)?.let { react -> reacts.add(react) } }
            } else Reaction.of(handle, -1, any)?.let { reacts.add(it) }

            return Reactions(handle, reacts)
        }
    }

}