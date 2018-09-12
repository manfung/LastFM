package app.news.mc.com.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tephra.mc.lastfm.R
import com.tephra.mc.lastfm.data.model.Artist
import com.tephra.mc.lastfm.ui.search.ISearchItemOnClickListener
import kotlinx.android.synthetic.main.search_list_item.view.*

class SearchListAdapter(private val articles: List<Artist>,
                        private val searchItemOnClickListener: ISearchItemOnClickListener) :
        RecyclerView.Adapter<SearchListAdapter.SearchListItemViewHolder>() {

    class SearchListItemViewHolder(view: View, private val searchItemOnClickListener: ISearchItemOnClickListener) : RecyclerView.ViewHolder(view) {

        fun bind(artist: Artist, position: Int) {
            with(itemView) {
                tv_name.text = artist.name
                loadImage(artist)

//                tv_subtitle.text = article.subtitle
//                tv_date.text = SimpleDateFormat(APP_DATE_FORMAT).format(article.date).toString()
//                setOnClickListener { searchItemOnClickListener.onClick(tv_title, article.id) }
//                tv_title.transitionName = context.getString(R.string.view_transition) + position
            }

        }

        private fun loadImage(artist: Artist) {

            artist?.images?.get(0)?.imageUrl.let {

                Glide.with(itemView.context)
                        .load(it)
                        .apply(RequestOptions()
                                //.placeholder(R.mipmap.ic_loading_image)
                            .centerCrop()

                        )
                        .into(itemView.iv_image)
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
        holder.bind(article, position)
    }

    override fun getItemCount() = articles.size

}
