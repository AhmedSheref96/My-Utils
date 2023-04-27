package com.el3asas.utils.utils

import android.animation.Animator
import android.view.View
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton


private fun hideBottomNavigationView(
    view: View, fab: FloatingActionButton? = null
) {
    view.animate().translationY(view.height.toFloat())
        .setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                view.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    fab?.hide()

}

private fun showBottomNavigationView(
    view: View, fab: FloatingActionButton? = null
) {
    view.animate().translationY(0f).setListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            view.visibility = View.VISIBLE
        }

        override fun onAnimationEnd(animation: Animator) {}
        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    })

    fab?.show()
    fab?.visibility = View.VISIBLE

}