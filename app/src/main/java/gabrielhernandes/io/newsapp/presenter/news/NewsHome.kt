package gabrielhernandes.io.newsapp.presenter.news

import gabrielhernandes.io.newsapp.model.NewsResponse

interface NewsHome {

    interface Presenter {

        fun requestAll()
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message:String)
        fun onComplete()

    }
}