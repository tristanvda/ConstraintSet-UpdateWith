package com.tristanvda.constraintsetupdatewith.extension

import android.content.Context
import android.support.constraint.ConstraintSet
import kotlin.collections.HashMap


fun ConstraintSet.copy(): ConstraintSet = ConstraintSet().apply { clone(this@copy) }

fun ConstraintSet.updateWith(context: Context, constraintLayoutId: Int) {

    val newConstraintSet = ConstraintSet()
    newConstraintSet.clone(context, constraintLayoutId)
    updateWith(newConstraintSet)
}

@Suppress("UNCHECKED_CAST")
fun ConstraintSet.updateWith(constraintSet: ConstraintSet) {

    val field = ConstraintSet::class.java.getDeclaredField("mConstraints")
    field.isAccessible = true

    val theseConstraints: HashMap<Int, Any> = field.get(this) as HashMap<Int, Any>
    val newConstraints: HashMap<Int, Any> = field.get(constraintSet) as HashMap<Int, Any>

    theseConstraints.putAll(newConstraints)
}
