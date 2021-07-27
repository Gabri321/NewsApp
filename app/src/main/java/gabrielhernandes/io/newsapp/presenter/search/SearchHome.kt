package gabrielhernandes.io.newsapp.presenter.search

import gabrielhernandes.io.newsapp.model.NewsResponse

interface SearchHome {
    interface Presenter {

        fun search(term: String)
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()

    }
}