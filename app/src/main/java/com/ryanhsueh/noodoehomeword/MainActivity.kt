package com.ryanhsueh.noodoehomeword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private val tag = MainActivity::class.java.simpleName

    private val homeworkService by lazy {
        HomeworkService.create()
    }

    private var disposable: Disposable? = null
    private var user: Model.UserInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    fun onCaseOneClicked(view: View) {
        Log.d(tag, "onCaseOneClicked")

        disposable = homeworkService.login("test2@qq.com", "test1234qq")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.d(tag, "sessionToken = ${result.sessionToken}")
                    user = result
                    HomeworkService.setToken(user!!.sessionToken)
                },
                { error ->
                    Log.e(tag, error.message)
                }
            )
    }

    fun onCaseTwoClicked(view: View) {
        Log.d(tag, "onCaseTwoClicked")

        if (user != null) {
            val body = Model.Timezone(2)
            disposable = homeworkService.updateUser(user!!.objectId, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        Log.d(tag, result.toString())
                    },
                    { error ->
                        Log.e(tag, error.message)
                    }
                )
        }
    }

    fun onCaseOnePlusTwoClicked(view: View) {
        Log.d(tag, "onCaseOnePlusTwoClicked")

        disposable = homeworkService.login("test2@qq.com", "test1234qq")
            .subscribeOn(Schedulers.io())
            .flatMap { result ->
                Log.d(tag, "sessionToken = ${result.sessionToken}")
                user = result
                HomeworkService.setToken(user!!.sessionToken)

                val body = Model.Timezone(2)
                homeworkService.updateUser(user!!.objectId, body)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.d(tag, result.toString())
                },
                { error ->
                    Log.e(tag, error.message)
                }
            )
    }

}
