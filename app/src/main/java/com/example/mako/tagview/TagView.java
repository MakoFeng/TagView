package com.example.mako.tagview;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/5/7.
 */
public class TagView extends RelativeLayout implements View.OnClickListener {


    public Animation blackAnimation1;
    public Animation blackAnimation2;
    public Animation whiteAnimation;


    public TextView textview;// 文字描述显示View
    public ImageView blackIcon1;// 黑色圆圈View
    public ImageView blackIcon2;// 黑色圆圈View
    protected ImageView brandIcon;// 白色圆圈View
    public ImageView viewPointer;// 指向brandIcon或者geoIcon，根据设置的类型的不同

    public boolean isShow = false;

    private Handler handler = new Handler();

    private TagViewListener listener;

    public interface TagViewListener {
        public void onTagViewClicked(View view);
    }


    public TagView(Context context) {
        this(context, null);
    }

    public TagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.blackAnimation1 = AnimationUtils.loadAnimation(context, R.anim.black_anim);
        this.blackAnimation2 = AnimationUtils.loadAnimation(context, R.anim.black_anim);
        this.whiteAnimation = AnimationUtils.loadAnimation(context, R.anim.white_anim);


        LayoutInflater.from(context).inflate(R.layout.tag_view, this);
        textview = ((TextView) findViewById(R.id.text));
        textview.getBackground().setAlpha(78);
        blackIcon1 = ((ImageView) findViewById(R.id.blackIcon1));
        blackIcon2 = ((ImageView) findViewById(R.id.blackIcon2));
        brandIcon = ((ImageView) findViewById(R.id.brandIcon));
        this.viewPointer = brandIcon;
        this.setOnClickListener(this);




    }


    public final void clearAnim(){
        this.blackIcon1.clearAnimation();
        this.blackIcon2.clearAnimation();
        this.viewPointer.clearAnimation();
        this.isShow = false;
    }


    public final void startBlackAnimation1(final ImageView imageView) {
        blackAnimation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isShow) {
                    return;
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageView.clearAnimation();
                        blackAnimation1.reset();
                        startBlackAnimation2(blackIcon2);
                    }
                }, 10);
            }
        });
        imageView.clearAnimation();
        imageView.startAnimation(blackAnimation1);
    }

    public final void startBlackAnimation2(final ImageView imageView) {
        blackAnimation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isShow) {
                    return;
                }
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imageView.clearAnimation();
                        blackAnimation2.reset();
                        startWhiteAnimation(viewPointer);
                    }
                }, 10);
            }
        });
        imageView.clearAnimation();
        imageView.startAnimation(blackAnimation2);
    }

    public final void startWhiteAnimation(final ImageView imageView) {
        whiteAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isShow) {
                    return;
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageView.clearAnimation();
                        whiteAnimation.reset();
                        startBlackAnimation1(blackIcon1);
                    }
                }, 10);
            }
        });
        imageView.clearAnimation();
        imageView.startAnimation(whiteAnimation);
    }

    protected final void setVisible() {
        if ((textview != null) && (tagName != null)) {
            textview.setText(tagName);
            textview.setVisibility(View.VISIBLE);
        }
        clearAnim();
        show();
    }

    public void show() {
        if (this.isShow) {
            return;
        }
        this.isShow = true;
        startWhiteAnimation(viewPointer);
    }

    private String tagName;

    public void setData(String tagName) {
        this.tagName = tagName;
        setVisible();
    }

    public String getData() {
        return tagName;
    }

    public void setTagViewListener(TagViewListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (null != listener) {
            listener.onTagViewClicked(view);
        }
    }
}
