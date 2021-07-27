package gabrielhernandes.io.newsapp.ui

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import gabrielhernandes.io.newsapp.R
import gabrielhernandes.io.newsapp.databinding.ActivityArticleBinding
import gabrielhernandes.io.newsapp.model.Article
import gabrielhernandes.io.newsapp.model.NewsDataSource
import gabrielhernandes.io.newsapp.presenter.ViewHome
import gabrielhernandes.io.newsapp.presenter.favorite.FavoritePresenter

class ArticleActivity() : AppCompatActivity(), ViewHome.Favorite {

    private lateinit var article: Article
    private var dataSource = NewsDataSource(this)
    private var presenter = FavoritePresenter(this, dataSource)
    lateinit var binding: ActivityArticleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        intent()

        var source = NewsDataSource(this)

        binding.webView.apply {
            webViewClient = WebViewClient()

            article.url?.let { url ->
                loadUrl(url)
            }
        }
        binding.fab.setOnClickListener {
            presenter.saveArticle(article)

            Snackbar.make(binding.fab, R.string.article_saved_successful, Snackbar.LENGTH_LONG)
                .show()
        }
    }


    private fun intent() {
        intent?.extras.let { articleSend ->
            if (articleSend != null) {
                article = articleSend.get("article") as Article
            }
        }
    }

    override fun showArticles(articles: List<Article>) {}
}