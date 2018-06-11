package com.example.wangtao.wangtao_zhoukaoyi20180611;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangtao on 2018/6/11.
 * 创建日期: on 2018/5/8.
 * 描述:
 * 作者:wangtao
 */
public class FlowLayout extends ViewGroup{
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //进行测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //赋初始值
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //获取屏幕的精确值和模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //定义属性
        int width=0;
        int height=0;
        int totalHeight=0;
        int lineWidth=0;
        int lineHeight=0;
        int childWidth=0;
        int childHeight=0;
        View childView;
        for (int i = 0; i <getChildCount() ; i++) {
              //获取子控件
             childView =getChildAt(i);
             childHeight =childView.getMeasuredHeight();
             childWidth =childView.getMeasuredWidth();

             if (childWidth > widthSize){
                   throw new IllegalArgumentException("出现异常");
             }

             if (lineWidth + childWidth > widthSize){
                   //换行
                   width =widthSize;
                   totalHeight += lineHeight;
                   lineHeight =childHeight;
                   lineWidth =childWidth;
             }else{
                 // 不换行
                 lineWidth += childWidth;
                 lineHeight=Math.max(lineHeight,childHeight);
                 width =Math.max(lineWidth,width);
             }
             //判断最后一个
             if (i == getChildCount() -1){
                  totalHeight +=lineHeight;
                  height =totalHeight;
             }
        }

        width = widthMode == MeasureSpec.EXACTLY?widthSize:width;
        height = heightMode == MeasureSpec.EXACTLY?heightSize:height;
        //设置最终值
        setMeasuredDimension(width,height);
    }
   //进行布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //定义属性
        int totalHeight=0;
        int lineWidth=0;
        int lineHeight=0;
        int childWidth=0;
        int childHeight=0;
        View childView;
        for (int i = 0; i <getChildCount() ; i++) {
            //获取子控件
            childView =getChildAt(i);
            childHeight =childView.getMeasuredHeight();
            childWidth =childView.getMeasuredWidth();

            //设置margin
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) childView.getLayoutParams();
           //获取左右上下的外边距
            int leftMargin= marginLayoutParams.leftMargin;
            int rightMargin= marginLayoutParams.rightMargin;
            int topMargin= marginLayoutParams.topMargin;
            int bottomMargin= marginLayoutParams.bottomMargin;

            if (lineWidth + childWidth+leftMargin+rightMargin > getMeasuredWidth()-getPaddingLeft()-getPaddingRight()){
                //换行
                totalHeight += lineHeight;
                lineWidth =0+leftMargin;
                 getViewLayout(childView,lineWidth,totalHeight+topMargin,lineWidth+childWidth+rightMargin,totalHeight+childHeight+bottomMargin);
                lineHeight =childHeight+topMargin+bottomMargin;
                lineWidth =childWidth+leftMargin+rightMargin;
            }else{
                // 不换行

                getViewLayout(childView,lineWidth+leftMargin,totalHeight+topMargin,lineWidth+childWidth+rightMargin,totalHeight+childHeight+bottomMargin);
                lineWidth += childWidth+leftMargin+rightMargin;
                lineHeight=Math.max(lineHeight,childHeight+topMargin+bottomMargin);
            }
        }
    }

    private void getViewLayout(View childView, int lineWidth, int totalHeight, int i, int i1) {
         childView.layout(lineWidth,totalHeight,i,i1);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
