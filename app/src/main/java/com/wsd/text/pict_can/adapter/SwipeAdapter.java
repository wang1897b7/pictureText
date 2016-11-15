package com.wsd.text.pict_can.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.model.Customer;
import com.wsd.text.pict_can.view.ShapeImageView;

import java.util.List;

/**
 * Created by Sun on 2016/11/14.
 */

public class SwipeAdapter extends BaseItemDraggableAdapter<Customer, BaseViewHolder> {

    public SwipeAdapter(List<Customer> data) {
        super(R.layout.list_item_customer,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Customer customer) {
        ShapeImageView avatar = baseViewHolder.getView(R.id.customer_avatar);
        avatar.setImageUrl(customer.name,R.drawable.bg_avatar,R.drawable.bg_avatar);
        TextView name = baseViewHolder.getView(R.id.customer_name);
        name.setText(customer.name);
    }
}
