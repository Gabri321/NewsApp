package gabrielhernandes.io.newsapp.presenter.search

import gabrielhernandes.io.newsapp.model.NewsDataSource
import gabrielhernandes.io.newsapp.model.NewsResponse
import gabrielhernandes.io.newsapp.presenter.ViewHome

class SearchPresenter(val view: ViewHome.View, private val datasource: NewsDataSource) :
    SearchHome.Presenter {
    override fun search(term: String) {
        this.view.showProgressBar()
        this.datasource.getSearchNews(term, this)
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