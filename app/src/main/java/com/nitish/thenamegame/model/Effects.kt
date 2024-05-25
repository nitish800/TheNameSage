package com.nitish.thenamegame.model

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.nitish.thenamegame.R


object animObj {

    private var mMediaPlayer: MediaPlayer?= null

    fun soundClick(con: Context){
        vibrate(context = con)
        if(mMediaPlayer == null){
            mMediaPlayer = MediaPlayer.create(con, R.raw.click)
            mMediaPlayer!!.isLooping = false
            mMediaPlayer!!.start()
        }else mMediaPlayer!!.start()

        mMediaPlayer!!.setOnCompletionListener {
            if (mMediaPlayer != null) {
                mMediaPlayer!!.stop()
                mMediaPlayer!!.release()
                mMediaPlayer = null
            }
        }

    }



    private fun vibrate(context: Context){

        val vib = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
        vib.cancel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vib.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
        } else {
            @Suppress("DEPRECATION")
            vib.vibrate(50)
        }
    }




}