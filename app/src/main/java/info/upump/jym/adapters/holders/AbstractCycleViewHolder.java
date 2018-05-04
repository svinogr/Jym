package info.upump.jym.adapters.holders;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import info.upump.jym.R;
import info.upump.jym.entity.Cycle;
import info.upump.jym.fragments.cycle.CRUD;

/**
 * Created by explo on 04.05.2018.
 */

abstract class AbstractCycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected TextView title;
    protected TextView date;
    protected ImageView imageView;
    protected Cycle cycle;
    protected View itemView;
    protected Context context;
    private CRUD crud; // interface for create intents

    public void bind(Cycle cycle) {
        this.cycle = cycle;
        title.setText(cycle.getTitle());
        date.setText(cycle.getStartStringFormatDate());
        setPic();
    }


    protected void setPic() {
        Uri uri = null;
        if (cycle.getImage() != null) {
            uri = Uri.parse(cycle.getImage());
        }
        RequestOptions options = new RequestOptions()
                .transforms(new RoundedCorners(50))
                .centerCrop()
                .error(R.color.colorTextLabel)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        if (cycle.getDefaultImg() != null) {
            int ident = context.getResources().getIdentifier(cycle.getDefaultImg(), "drawable", context.getPackageName());
            Glide.with(context).load(ident).apply(options).into(imageView);
        } else Glide.with(itemView.getContext()).load(uri).apply(options).into(imageView);

    }

}
