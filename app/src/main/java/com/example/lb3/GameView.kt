package com.example.lb3

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class GameView(var c: Context, var gameTask: GameTask) : View(c) {
    private var myPaint: Paint? = null
    private var speed = 1
    private var time = 0
    private var score = 0
    private var myZombiePosition = 0
    private val otherZombies = ArrayList<HashMap<String, Any>>()
    private var highScore = 0 // Added highScore variable

    var viewWidth = 0
    var viewHeight = 0

    init {
        myPaint = Paint()
    }

    fun resetGame() {
        score = 0
        speed = 1
        time = 0
        myZombiePosition = 0
        otherZombies.clear()
        invalidate() // Redraw the view to reflect the reset state
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if (time % 700 < 10 + speed) {
            val map = HashMap<String, Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            otherZombies.add(map)
        }

        time += 10 + speed
        val zombieWidth = viewWidth / 5
        val zombieHeight = zombieWidth + 10
        myPaint!!.style = Paint.Style.FILL
        val d = resources.getDrawable(R.drawable.scman, null)

        d.setBounds(
            myZombiePosition * viewWidth / 3 + viewWidth / 15 + 25,
            viewHeight - 2 - zombieHeight,
            myZombiePosition * viewWidth / 3 + viewWidth / 15 + zombieWidth - 25,
            viewHeight - 2
        )

        d.draw(canvas)
        myPaint!!.color = Color.GREEN

        for (i in otherZombies.indices) {
            try {
                val zombieX = otherZombies[i]["lane"] as Int * viewWidth / 3 + viewWidth / 15
                var zombieY = time - otherZombies[i]["startTime"] as Int
                val d2 = resources.getDrawable(R.drawable.zomb, null)

                d2.setBounds(
                    zombieX + 25, zombieY - zombieHeight, zombieX + zombieWidth - 25, zombieY
                )

                d2.draw(canvas)
                if (otherZombies[i]["lane"] as Int == myZombiePosition) {
                    if (zombieY > viewHeight - 2 - zombieHeight && zombieY < viewHeight - 2) {
                        gameTask.closeGame(score)
                    }
                }

                if (zombieY > viewHeight + zombieHeight) {
                    otherZombies.removeAt(i)
                    score++
                    speed = 1 + Math.abs(score / 8)
                    if (score > highScore) {
                        highScore = score
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        myPaint!!.color = Color.WHITE // Changed color.WHITE to Color.WHITE
        myPaint!!.textSize = 40f
        canvas.drawText("Score : $score", 80f, 80f, myPaint!!)
        canvas.drawText("Speed : $speed", 380f, 80f, myPaint!!)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x
                if (x1 < viewWidth / 2) {
                    if (myZombiePosition > 0) {
                        myZombiePosition--
                    }
                } else if (x1 > viewWidth / 2) {
                    if (myZombiePosition < 2) {
                        myZombiePosition++
                    }
                }

                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                // Handle ACTION_UP event if needed
            }
        }
        return true
    }
}


