package com.yai.sequence

open class Sequence<T> : ArrayList<T> {
    constructor()
    constructor(elements: Collection<T>) : super(elements)
}