package com.tristanvda.constraintsetupdatewith

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.transition.TransitionManager
import android.widget.Button
import com.tristanvda.constraintsetupdatewith.extension.copy
import com.tristanvda.constraintsetupdatewith.extension.updateWith

class MainActivity : AppCompatActivity() {

    private lateinit var originalConstraintSet: ConstraintSet
    private lateinit var constraintSet1: ConstraintSet
    private lateinit var constraintSet2: ConstraintSet
    private lateinit var constraintLayout: ConstraintLayout

    private var currentStatus: Int = STATUS_ORIGINAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        constraintLayout = findViewById(R.id.parent) as ConstraintLayout

        //Create an original ConstraintSet that has the default state
        originalConstraintSet = ConstraintSet()
        originalConstraintSet.clone(constraintLayout)

        //Create the 2 states by cloning the original set and updating it with the constraints of the states
        constraintSet1 = originalConstraintSet.copy().apply { updateWith(this@MainActivity, R.layout.main_state_1) }
        constraintSet2 = originalConstraintSet.copy().apply { updateWith(this@MainActivity, R.layout.main_state_2) }

        (findViewById(R.id.switch_button) as Button).setOnClickListener {
            currentStatus = if (currentStatus == STATUS_2) STATUS_ORIGINAL else currentStatus + 1

            TransitionManager.beginDelayedTransition(constraintLayout)
            when (currentStatus) {
                STATUS_ORIGINAL -> originalConstraintSet.applyTo(constraintLayout)
                STATUS_1 -> constraintSet1.applyTo(constraintLayout)
                STATUS_2 -> constraintSet2.applyTo(constraintLayout)
            }
        }
    }

    companion object {
        private const val STATUS_ORIGINAL = 0
        private const val STATUS_1 = 1
        private const val STATUS_2 = 2
    }
}
