package com.ryanhsueh.noodoehomeword.mvp.constract

import com.ryanhsueh.noodoehomeword.base.BasePresenter
import com.ryanhsueh.noodoehomeword.base.BaseView

/**
 * Created by ryanhsueh on 2019-12-05
 */
interface UserContract {

    interface IView : BaseView<IPresenter> {
        fun onLoginSuccess(sessionToken: String)
        fun onTimezoneUpdated(result: String)
    }

    interface IPresenter: BasePresenter {
        fun login(username: String, password: String)
        fun updateTimezone(timezone: Int)
        fun loginAndUpdateTimezone(username: String, password: String, timezone: Int)
    }

}