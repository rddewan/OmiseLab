package com.richarddewan.omiselab.ui.Charity.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.richarddewan.omiselab.R
import com.richarddewan.omiselab.data.remote.response.CharityResponseList
import com.richarddewan.omiselab.ui.Charity.CharityFragmentDirections
import com.richarddewan.omiselab.ui.Charity.detail.CharityDetailFragment
import kotlinx.android.synthetic.main.charity_list_view.view.*

class CharityListAdaptor(val context: Context, private val mList: ArrayList<CharityResponseList>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val charity = mList[position]
        view = if (convertView == null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.charity_list_view,parent,false)
        }
        else {
            convertView
        }
        val id = view.lbCharityId
        val name = view.lbCharityName
        val logo = view.charityLogo

        id.text = charity.id.toString()
        name.text = charity.name
        Glide.with(context)
            .load(charity.logoUrl)
            .into(logo)

        view.setOnClickListener {
            view.findNavController().navigate(CharityFragmentDirections.actionNavigationCharityToCharityDetailFragment(
                charity.id,
                charity.name,
                charity.logoUrl
            ))
        }

        return view
    }

    override fun getItem(position: Int): Any = mList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = mList.size
}