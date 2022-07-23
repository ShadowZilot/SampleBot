package core

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

interface CallHolding : Callback {

    fun updateCall(call: Call)

    fun cancel()

    class Base(
        private val mListener: Callback
    ) : CallHolding {
        private lateinit var mCall: Call
        private var mIsExecuting: Boolean = false

        override fun updateCall(call: Call) {
            if (!mIsExecuting) {
                mCall = call
                mCall.enqueue(this)
                mIsExecuting = true
            }
        }

        override fun cancel() {
            mCall.cancel()
        }

        override fun onFailure(call: Call, e: IOException) {
            mIsExecuting = false
            mListener.onFailure(call, e)
        }

        override fun onResponse(call: Call, response: Response) {
            mIsExecuting = false
            mListener.onResponse(call, response)
        }
    }
}