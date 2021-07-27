package gabrielhernandes.io.newsapp.presenter.favorite

import gabrielhernandes.io.newsapp.model.Article
import gabrielhernandes.io.newsapp.model.NewsDataSource
import gabrielhernandes.io.newsapp.presenter.ViewHome

class FavoritePresenter(val view: ViewHome.Favorite, private val dataSource: NewsDataSource) :
    FavoriteHome.Presenter {
    fun saveArticle(article: Article) {
        this.dataSource.saveArticle(article)
    }

    override fun onSuccess(article: List<Article>) {
        this.view.showArticles(article)
    }

    fun getAll(){
        this.dataSource.getAllArticles(this)
    }
    fun delete(article : Article){
        this.dataSource.delete(article)
    }
}