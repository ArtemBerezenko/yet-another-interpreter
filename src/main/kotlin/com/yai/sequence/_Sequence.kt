package com.yai.sequence

inline fun <T, R> map(source: Sequence<T>, function: (T) -> R): Sequence<R> {
    val target = Sequence<R>(source.size)

    for (item in source)
        target.add(function(item))

    return target
}