package app.news.mc.com.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tephra.mc.lastfm.extension.loadFromUrl
import com.tephra.mc.lastfm.R
import com.tephra.mc.lastfm.data.model.Artist
import com.tephra.mc.lastfm.ui.search.ISearchItemOnClickListener
import kotlinx.android.synthetic.main.search_list_item.view.*

class SearchListAdapter(private var articles: MutableList<Artist>,
                        private val searchItemOnClickListener: ISearchItemOnClickListener) :
        RecyclerView.Adapter<SearchListAdapter.SearchListItemViewHolder>() {

    class SearchListItemViewHolder(view: View, private val searchItemOnClickListener: ISearchItemOnClickListener) : RecyclerView.ViewHolder(view) {

        fun bind(artist: Artist) {
            with(itemView) {
                tv_name.text = artist.name
                loadImage(artist)
                setOnClickListener { searchItemOnClickListener.onClick(iv_image, artist.mbid, getLargeImageUrl(artist)) }
            }

        }

        private fun loadImage(artist: Artist) {

            artist.images[0].imageUrl.let {
                itemView.iv_image.loadFromUrl(it)
            }
        }

        private fun getLargeImageUrl(artist: Artist): String {

            artist.images[2].imageUrl.let {
                return it
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): SearchListAdapter.SearchListItemViewHolder {

        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.search_list_item, parent, false) as View

        return SearchListItemViewHolder(itemView, searchItemOnClickListener)
    }

    override fun onBindViewHolder(holder: SearchListItemViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount() = articles.size

    fun addItems(newArticles: List<Artist>) {
        articles.addAll(newArticles)
    }

}
