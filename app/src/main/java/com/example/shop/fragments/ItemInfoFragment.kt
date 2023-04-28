import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.shop.DataModel
import com.example.shop.R
import com.example.shop.databinding.FragmentItemInfoBinding
import com.example.shop.databinding.ListItemBinding
import com.example.shop.retrofit.Product
import com.squareup.picasso.Picasso

class ItemInfoFragment : Fragment() {

    private lateinit var binding : FragmentItemInfoBinding
    private val dataModel : DataModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataModel.product.observe(activity as LifecycleOwner) { product ->
            Picasso.get().load(Uri.parse(product.images[0])).into(binding.imageForItem)
            binding.titleForItem.text = product.title
            binding.descriptionForItem.text = product.description

            when (product.isFavourite) {
                true -> binding.favButt.text = "delete from cart"
                false -> binding.favButt.text = "add to cart"
            }
            binding.favButt.setOnClickListener {
                            when (product.isFavourite) {
                                true -> {
                                    product.isFavourite = false
                                    binding.titleForItem.text = product.title
                                    binding.favButt.text = "add to cart"
                                }
                                false -> {
                                    product.isFavourite = true
                                    binding.titleForItem.text = product.title
                                    binding.favButt.text = "delete from cart"
                                }
                }
            }
        }


    }
}