package com.iznan.githubsearch.util

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.CallSuper
import com.google.common.base.Optional

abstract class SearchTextWatcher(private val waitingTime: Long = 600L) : TextWatcher {

    private var counter = Optional.absent<CountDownTimer>()

    @CallSuper
    override fun afterTextChanged(s: Editable?) {
        if (counter.isPresent) {
            counter.get().cancel()
        }

        counter = Optional.of(object : CountDownTimer(waitingTime, waitingTime) {

            override fun onFinish() {
                typingStateStopped(s)
            }

            override fun onTick(millisUntilFinished: Long) {

            }

        })
        counter.get().start()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    abstract fun typingStateStopped(s: Editable?)

}