package com.yai.model

class Value : Comparable<Value> {
    private var value: Any

    private constructor() {
        value = Any()
    }

    constructor(v: Any?) {
        if (v == null) {
            throw RuntimeException("v == null")
        }
        value = v
        if (!(isBoolean() || isList() || isNumber() || isString())) {
            throw RuntimeException("invalid data type: " + v + " (" + v.javaClass + ")")
        }
    }

    fun asBoolean(): Boolean = value as Boolean

    fun asInteger(): Int = (value as Number).toInt()

    fun asDouble(): Double = (value as Number).toDouble()

    fun asLong(): Long = (value as Number).toLong()

    fun asList(): List<Value> = value as List<Value>

    fun asSequence(): List<Int> = value as List<Int>

    fun asString(): String = value as String

    override fun compareTo(that: Value): Int {
        return if (isNumber() && that.isNumber()) {
            if (this == that) {
                0
            } else {
                asDouble().compareTo(that.asDouble())
            }
        } else if (isString() && that.isString()) {
            asString().compareTo(that.asString())
        } else {
            throw RuntimeException("illegal expression: can't compare `$this` to `$that`")
        }
    }

    override fun equals(o: Any?): Boolean {
        if (this === VOID || o === VOID) {
            throw RuntimeException("can't use VOID: $this ==/!= $o")
        }
        if (this === o) {
            return true
        }
        if (o == null || this.javaClass != o.javaClass) {
            return false
        }
        val that = o as Value
        return if (isNumber() && that.isNumber()) {
            val diff = Math.abs(asDouble() - that.asDouble())
            diff < 0.00000000001
        } else {
            value == that.value
        }
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    fun isBoolean(): Boolean = value is Boolean
    fun isNumber(): Boolean = value is Number
    fun isList(): Boolean = value is List<*>
    fun isNull(): Boolean = this === NULL
    fun isVoid(): Boolean = this === VOID
    fun isString(): Boolean = value is String

    override fun toString(): String {
        return if (isNull()) "NULL" else if (isVoid()) "VOID" else value.toString()
    }

    companion object {
        val NULL = Value()
        val VOID = Value()
    }
}
