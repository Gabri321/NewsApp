package gabrielhernandes.io.newsapp.presenter.favorite

import gabrielhernandes.io.newsapp.model.Article

interface FavoriteHome {
    interface Presenter {
        fun onSuccess(article: List<Article>)
    }
}