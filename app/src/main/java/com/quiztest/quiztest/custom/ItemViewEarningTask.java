package com.quiztest.quiztest.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.quiztest.quiztest.R;

public class ItemViewEarningTask extends LinearLayout {

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public ItemViewEarningTask(Context context) {
        super(context);
    }

    public ItemViewEarningTask(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyAttributes(context, attrs);
    }

    public ItemViewEarningTask(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyAttributes(context, attrs);
    }

    public ItemViewEarningTask(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyAttributes(context, attrs);
    }


    @SuppressLint("ResourceAsColor")
    private void applyAttributes(Context context, AttributeSet attrs) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.item_earning_task, null);
        CardView cardView = view.findViewById(R.id.card_view);
        ExtTextView extTitle = view.findViewById(R.id.ext_title);
        ImageView imgItem = view.findViewById(R.id.img_item);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EarningTask,
                0, 0);

        view.setOnClickListener(view1 -> {
            if (listener != null){
                listener.onClick();
            }
        });

        try {
            cardView.setCardBackgroundColor(a.getColor(R.styleable.EarningTask_backgroundColor, 0));
            extTitle.setText(a.getString(R.styleable.EarningTask_title));
            imgItem.setImageDrawable(a.getDrawable(R.styleable.EarningTask_image));
        } finally {
            a.recycle();
        }
        addView(view);
    }

    public interface Listener{
        void onClick();
    }
}
