package com.example.qq.mycoordinatordemo.view.circle_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.qq.mycoordinatordemo.R;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
public class CircleMenu extends ViewGroup implements MenuController.ControllerListener,ItemSelectionAnimator.AnimationDrawController{

    public interface OnItemClickListener{
        void onItemClick(CircleMenuButton menuButton);
    }
    public interface OnStateUpdateListener{
        void onMenuExpanded();
        void onMenuCollapsed();
    }
    private float mRadius = -1;
    private int mCircleStartAngle;
    private CenterMenuButton mCenterButton;
    private MenuController mMenuController;
    private ItemSelectionAnimator mItemSelectionAnimator;
    private OnStateUpdateListener mListener;
    private OnItemClickListener mOnItemClickListener;

    public CircleMenu(Context context) {
        this(context,null);
    }

    public CircleMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CircleMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircleMenu);
        try {
            mCircleStartAngle = typedArray.getInteger(R.styleable.CircleMenu_start_angle,270);
            mRadius = typedArray.getDimension(R.styleable.CircleMenu_distance,getResources().getDimension(R.dimen.circle_menu_distance));
        }finally {
            typedArray.recycle();
        }
        mMenuController = new MenuController(this);
        mItemSelectionAnimator = new ItemSelectionAnimator(mMenuController, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addChildrenToController();
        createCenterButton(getContext());
    }

    private void addChildrenToController(){
        for (int i = 0; i < getChildCount();i++){
            View child = getChildAt(i);
            if (child != mCenterButton){
                mMenuController.addButton((CircleMenuButton)child);
            }
        }
    }
    private void createCenterButton(Context context){
        mCenterButton = new CenterMenuButton(context);
        mCenterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mMenuController.toggle();
            }
        });
        addView(mCenterButton,super.generateDefaultLayoutParams());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int maxChildWidth = 0;
        int maxChildHeight = 0;
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.AT_MOST);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST);
        final int childCount = getChildCount();
        for (int i = 0;i < childCount;i++){
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE){
                continue;
            }
            measureChild(child,childWidthMeasureSpec,childHeightMeasureSpec);
            maxChildWidth = Math.max(maxChildWidth,child.getMeasuredWidth());
            maxChildHeight = Math.max(maxChildHeight,child.getMeasuredHeight());
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else if (widthMode == MeasureSpec.AT_MOST){
            width = Math.min(widthSize,heightSize);
        }else {
            width = maxChildWidth * 3;
        }
        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else if (heightMode == MeasureSpec.AT_MOST){
            height = Math.min(heightSize,widthSize);
        }else {
            height = maxChildHeight * 3;
        }
        setMeasuredDimension(resolveSize(width,widthMeasureSpec),resolveSize(height,heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int circleHeight = getHeight();
        int circleWidth = getWidth();
        int centerButtonWidth = mCenterButton.getMeasuredWidth();
        int centerButtonHeight = mCenterButton.getMeasuredHeight();
        int centerButtonLeft = Math.round((float) ((circleWidth / 2.0) - centerButtonWidth / 2.0));
        int centerButtonTop = Math.round((float) ((circleHeight / 2.0) - centerButtonHeight / 2.0));
        mCenterButton.layout(centerButtonLeft,centerButtonTop,centerButtonLeft + centerButtonWidth,centerButtonTop + centerButtonHeight);

        mMenuController.calculateButtonsVertices(mRadius,mCircleStartAngle,circleWidth,circleHeight,centerButtonLeft, centerButtonTop);
        mItemSelectionAnimator.setCircleRadius(mRadius,circleWidth,circleHeight);
    }



    @Override
    public void onStartExpanding() {
        mCenterButton.setExpanded(true);
        mCenterButton.setClickable(false);
    }

    @Override
    public void onExpanded() {
        mCenterButton.setClickable(true);
        if (mListener != null){
            mListener.onMenuExpanded();
        }
    }

    @Override
    public void onSelectAnimationStarted() {
        mCenterButton.setClickable(false);
    }

    @Override
    public void onSelectAnimationFinished() {

    }

    @Override
    public void onExitAnimationStarted() {

    }

    @Override
    public void onExitAnimationFinished() {
        mCenterButton.setClickable(true);
    }

    @Override
    public void onStartCollapsing() {
        mCenterButton.setExpanded(false);
        mCenterButton.setClickable(false);
    }

    @Override
    public void onCollapsed() {
        mCenterButton.setClickable(true);
        if (mListener != null){
            mListener.onMenuCollapsed();
        }
    }
    @Override
    public void onItemClick(CircleMenuButton menuButton) {
        mCenterButton.setExpanded(false);
        mItemSelectionAnimator.onItemClick(menuButton,mMenuController.getButtonsPoint(menuButton));
        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(menuButton);
        }
    }
    @Override
    public void redrawView() {
        invalidate();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }
    public void setStateUpdateListener(OnStateUpdateListener listener){
        this.mListener = listener;
    }
    public boolean isExpanded(){
        return mMenuController.isExpanded();
    }
    public void addButton(CircleMenuButton menuButton){
        addView(menuButton,getChildCount() - 1);
        mMenuController.addButton(menuButton);
    }
}
