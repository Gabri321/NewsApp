package gabrielhernandes.io.newsapp.presenter

import gabrielhernandes.io.newsapp.model.Article

interface ViewHome {

    interface View {
        fun showProgressBar()
        fun showFailure(message: String)
        fun hideProgressBar()
        fun showArticles(articles: List<Article>)
    }

    interface Favorite {
        fun showArticles(articles: List<Article>)
    }

}
