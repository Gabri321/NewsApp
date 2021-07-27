package gabrielhernandes.io.newsapp.presenter.news

import gabrielhernandes.io.newsapp.model.NewsDataSource
import gabrielhernandes.io.newsapp.model.NewsResponse
import gabrielhernandes.io.newsapp.presenter.ViewHome

class NewsPresenter(val view: ViewHome.View, private val datasource: NewsDataSource) :
    NewsHome.Presenter {
    override fun requestAll() {
        this.view.showProgressBar()
        this.datasource.getBreakingNews(this)
    }

    override fun onSuccess(newsResponse: NewsResponse) {
        this.view.showArticles(newsResponse.articles)
    }

    override fun onError(message: String) {
        this.view.showFailure(message)
    }

    override fun onComplete() {
        this.view.hideProgressBar()
    }
}