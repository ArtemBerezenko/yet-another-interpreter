package com.yai.sequence

open class Sequence<T> : ArrayList<T> {
    constructor()
    constructor(capacity: Int) : super(capacity)
    constructor(elements: Collection<T>) : super(elements)
}