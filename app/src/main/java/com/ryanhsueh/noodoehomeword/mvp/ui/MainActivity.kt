package com.ryanhsueh.noodoehomeword.mvp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.ryanhsueh.noodoehomeword.R
import com.ryanhsueh.noodoehomeword.mvp.constract.UserContract
import com.ryanhsueh.noodoehomeword.mvp.presenter.UserPresenter

class MainActivity : AppCompatActivity(), UserContract.IView {

    private val tag = MainActivity::class.java.simpleName

    private val username = "test2@qq.com"
    private val password = "test1234qq"


    override lateinit var presenter: UserContract.IPresenter

    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.text_view)

        presenter = UserPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    fun onCaseOneClicked(view: View) {
        Log.d(tag, "onCaseOneClicked")

        presenter.login(username, password)
    }

    fun onCaseTwoClicked(view: View) {
        Log.d(tag, "onCaseTwoClicked")

        presenter.updateTimezone(2)
    }

    fun onCaseOnePlusTwoClicked(view: View) {
        Log.d(tag, "onCaseOnePlusTwoClicked")

        presenter.loginAndUpdateTimezone(username, password, 4)
    }

    override fun onLoginSuccess(sessionToken: String) {
        textView!!.text = sessionToken
    }

    override fun onTimezoneUpdated(result: String) {
        textView!!.text = result
    }

}
