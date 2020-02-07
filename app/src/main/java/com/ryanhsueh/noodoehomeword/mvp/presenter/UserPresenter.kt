package com.ryanhsueh.noodoehomeword.mvp.presenter

import android.util.Log
import com.ryanhsueh.noodoehomeword.api.HomeworkService
import com.ryanhsueh.noodoehomeword.api.ServiceManager
import com.ryanhsueh.noodoehomeword.bean.Model
import com.ryanhsueh.noodoehomeword.mvp.constract.UserContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

/**
 * Created by ryanhsueh on 2019-12-05
 */
class UserPresenter(kodein: Kodein, private val view: UserContract.IView): UserContract.IPresenter {

    private val serviceManager: ServiceManager by kodein.instance()

//    private val homeworkService by lazy {
//        HomeworkService.create()
//    }

    private var compositeDisposable: CompositeDisposable
    private var user: Model.UserInfo? = null

    companion object {
        val TAG = UserPresenter::class.java.simpleName
    }

    init {
        view.presenter = this
        compositeDisposable= CompositeDisposable()
    }

    override fun subscribe() {
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

    override fun login(username: String, password: String) {
//        val disposable = homeworkService.login(username, password)
        val disposable = serviceManager.userService.login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.d(TAG, "sessionToken = ${result.sessionToken}")
                    user = result
                    HomeworkService.sessionToken = user!!.sessionToken

                    view.onLoginSuccess(user!!.sessionToken)
                },
                { error ->
                    Log.e(TAG, error.message)
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun updateTimezone(timezone: Int) {
        if (user != null) {
            val body = Model.Timezone(timezone)
            val disposable = serviceManager.userService.updateUser(user!!.objectId, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        Log.d(TAG, result.toString())
                        view.onTimezoneUpdated(result.toString())
                    },
                    { error ->
                        Log.e(TAG, error.message)
                    }
                )
            compositeDisposable.add(disposable)
        }
    }

    override fun loginAndUpdateTimezone(username: String, password: String, timezone: Int) {
        val disposable = serviceManager.userService.login(username, password)
            .subscribeOn(Schedulers.io())
            .flatMap { result ->
                Log.d(TAG, "sessionToken = ${result.sessionToken}")
                user = result
                HomeworkService.sessionToken = user!!.sessionToken

                val body = Model.Timezone(timezone)
                serviceManager.userService.updateUser(user!!.objectId, body)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.d(TAG, result.toString())
                    view.onTimezoneUpdated(result.toString())
                },
                { error ->
                    Log.e(TAG, error.message)
                }
            )
        compositeDisposable.add(disposable)
    }
}