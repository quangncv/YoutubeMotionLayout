package com.example.youtubemotionlayout.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.youtubemotionlayout.R

class PlayerScreenMotionLayout(
    context: Context,
    attributeSet: AttributeSet? = null
) : MotionLayout(context, attributeSet) {

    private val viewToDetectTouch by lazy {
        findViewById<View>(R.id.playerLayout)
    }
    private val viewRect = Rect()
    private var hasTouchStarted = false

    private var listener: ITransitionListener? = null

    fun setListener(listener: ITransitionListener) {
        this.listener = listener
    }

    init {
        setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(
                p0: MotionLayout?,
                p1: Int,
                p2: Boolean,
                p3: Float
            ) {
            }

            override fun onTransitionStarted(
                p0: MotionLayout?,
                p1: Int,
                p2: Int
            ) {
                listener?.onTransitionStarted()
            }

            override fun onTransitionChange(
                p0: MotionLayout?,
                p1: Int,
                p2: Int,
                p3: Float
            ) {
                listener?.onTransitionChange(p3)
            }

            override fun onTransitionCompleted(
                p0: MotionLayout?,
                p1: Int
            ) {
                hasTouchStarted = false
                listener?.onTransitionCompleted()
            }
        })
    }

    private val gestureListener by lazy {
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                viewToDetectTouch.getHitRect(viewRect)
                return viewRect.contains(e1.x.toInt(), e1.y.toInt())
            }

//            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
//                Log.e("progress", "$progress")
//                if (progress == 1f) {
//                    transitionToStart()
//                }
//                return false
//            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                hasTouchStarted = false
                return super.onTouchEvent(event)
            }
        }
        if (!hasTouchStarted) {
            viewToDetectTouch.getHitRect(viewRect)
            hasTouchStarted = viewRect.contains(event.x.toInt(), event.y.toInt())
            Log.e("hasTouchStarted", hasTouchStarted.toString())
        }
        return hasTouchStarted && super.onTouchEvent(event)
    }

    private val gestureDetector by lazy {
        GestureDetector(context, gestureListener)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    interface ITransitionListener {
        fun onTransitionStarted()
        fun onTransitionChange(progress: Float)
        fun onTransitionCompleted()
    }
}