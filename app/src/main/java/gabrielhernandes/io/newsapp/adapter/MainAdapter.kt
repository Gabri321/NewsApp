package gabrielhernandes.io.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gabrielhernandes.io.newsapp.databinding.ItemListBinding
import gabrielhernandes.io.newsapp.model.Article

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)


    private val differCallBack = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            with(differ.currentList[position]) {
                Glide.with(holder.itemView.context).load(urlToImage).into(binding.imageIcon)
                binding.tvTitle.text = author ?: source?.name
                binding.source.text = source?.name ?: author
                binding.tvDescription.text = description
                binding.date.text = publishedAt




                holder.itemView.setOnClickListener {
                    onItemClickListener?.let { click ->
                        click(this)

                    }
                }
            }
        }

    }


    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnclickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}