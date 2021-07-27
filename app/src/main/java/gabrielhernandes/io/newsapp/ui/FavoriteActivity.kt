package gabrielhernandes.io.newsapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import gabrielhernandes.io.newsapp.R
import gabrielhernandes.io.newsapp.adapter.MainAdapter
import gabrielhernandes.io.newsapp.databinding.ActivityFavoriteBinding
import gabrielhernandes.io.newsapp.model.Article
import gabrielhernandes.io.newsapp.model.NewsDataSource
import gabrielhernandes.io.newsapp.presenter.ViewHome
import gabrielhernandes.io.newsapp.presenter.favorite.FavoritePresenter
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity(), ViewHome.Favorite {

    lateinit var binding: ActivityFavoriteBinding
    lateinit var favoritePresenter: FavoritePresenter

    private val mainAdapter: MainAdapter by lazy {
        MainAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        var dataSource = NewsDataSource(this)

        favoritePresenter = FavoritePresenter(this, dataSource)
        favoritePresenter.getAll()
        configRecycle()
        clickAdapter()
//        callBackTouchHelper


        var callBackTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var position = viewHolder.adapterPosition
                var article = mainAdapter.differ.currentList[position]
                favoritePresenter.delete(article)

                Snackbar.make(
                    viewHolder.itemView, R.string.article_delete_successful,
                    Snackbar.LENGTH_SHORT
                ).apply {
                    setAction(getString(R.string.undo)) {
                        favoritePresenter.saveArticle(article)
                        mainAdapter.notifyDataSetChanged()
                    }
                    show()
                }
            }

        }
        ItemTouchHelper(callBackTouchHelper).apply {
            attachToRecyclerView(rvFavorite)
        }

        favoritePresenter.getAll()


    }


    private fun configRecycle() {
        with(binding.rvFavorite) {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@FavoriteActivity, DividerItemDecoration.VERTICAL
                )
            )
        }

    }

    private fun clickAdapter() {

        mainAdapter.setOnclickListener { article ->


            var intent = Intent(this, ArticleActivity::class.java)

            intent.putExtra("article", article)

            startActivity(intent)
        }
    }

    override fun showArticles(articles: List<Article>) {
        mainAdapter.differ.submitList(articles.toList())
    }


}
