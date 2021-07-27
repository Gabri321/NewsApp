package gabrielhernandes.io.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import gabrielhernandes.io.newsapp.adapter.MainAdapter
import gabrielhernandes.io.newsapp.databinding.ActivitySearchBinding
import gabrielhernandes.io.newsapp.model.Article
import gabrielhernandes.io.newsapp.model.NewsDataSource
import gabrielhernandes.io.newsapp.presenter.ViewHome
import gabrielhernandes.io.newsapp.presenter.search.SearchPresenter
import gabrielhernandes.io.newsapp.util.UtilQueryListener


class SearchActivity : AppCompatActivity(), ViewHome.View {

    private val mainAdapter by lazy {
        MainAdapter()
    }

    lateinit var binding: ActivitySearchBinding

    private lateinit var presenter: SearchPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dataSource = NewsDataSource(this)

        presenter = SearchPresenter(this, dataSource)

        configRecycle()

        search()

        clickAdapter()

    }


    private fun configRecycle() {
        with(binding.rvSearch) {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@SearchActivity, DividerItemDecoration.VERTICAL
                )
            )
        }

    }

    private fun search() {
        binding.searchNews.setOnQueryTextListener(
            UtilQueryListener(

                this.lifecycle

            ) { newText ->
                newText?.let { query ->
                    if (query.isNotEmpty()) {
                        presenter.search(query)
                        binding.progressBarSearch.visibility = View.VISIBLE
                    }
                }
            }
        )

    }

    override fun showProgressBar() {
        binding.progressBarSearch.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        binding.progressBarSearch.visibility = View.INVISIBLE
    }

    override fun showArticles(articles: List<Article>) {
        mainAdapter.differ.submitList(articles.toList())
    }

    private fun clickAdapter() {

        mainAdapter.setOnclickListener { article ->


            var intent = Intent(this, ArticleActivity::class.java)

            intent.putExtra("article", article)

            startActivity(intent)
        }
    }


}