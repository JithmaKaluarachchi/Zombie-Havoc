package com.example.lb3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), GameTask {
    private lateinit var rootLayout: LinearLayout
    private lateinit var starBtn: Button
    private lateinit var mGameView: GameView
    private lateinit var score: TextView
    private lateinit var highScoreTextView: TextView
    private var highScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        starBtn = findViewById(R.id.starBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        highScoreTextView = findViewById(R.id.highScoreTextView)
        mGameView = GameView(this, this)

        starBtn.setOnClickListener {
            mGameView.setBackgroundResource(R.drawable.home)
            mGameView.resetGame()
            rootLayout.addView(mGameView)
            starBtn.visibility = View.GONE
            score.visibility = View.GONE
        }
    }

    override fun closeGame(mScore: Int) {
        score.text = "Score : $mScore"
        if (mScore > highScore) {
            highScore = mScore
            highScoreTextView.text = "High Score : $highScore"
        }
        rootLayout.removeView(mGameView)
        starBtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
    }
}
