
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import com.example.debri_lize.databinding.CustomSpinnerBinding

class SpinnerAdapter(mContext: Context, var Data: List<String>?) : ArrayAdapter<SpinnerModel>() {

    lateinit var binding : CustomSpinnerBinding
    private var Inflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return if (Data != null) Data!!.size else 0
    }

    override fun getView( position: Int, convertView: View, parent: ViewGroup): View { //처음에 클릭전에 보여지는 레이아웃
        var convertView = convertView
        if (convertView == null) {
            convertView = Inflater.inflate(com.example.debri_lize.R.layout.custom_spinner, parent, false)
        }
        if (Data != null) {
            val text = Data!![position]
            binding.spinnerText.text = text
        }
        return convertView
    }

    override fun getDropDownView(
        position: Int,
        convertView: View,
        parent: ViewGroup
    ): View { //클릭 후 보여지는 레이아웃
        var convertView = convertView
        if (convertView == null) {
            convertView = Inflater.inflate(com.example.debri_lize.R.layout.item_spinner, parent, false)
        }
        val text = Data!![position]
        binding.spinnerText.text = text
        return convertView
    }

    override fun getItem(position: Int): Any {
        return Data!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}