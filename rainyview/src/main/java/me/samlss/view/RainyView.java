package me.samlss.view;

import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.Component.DrawTask;
import ohos.agp.components.Component.EstimateSizeListener;
import ohos.agp.components.Component.LayoutRefreshedListener;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.render.Path;
import ohos.agp.utils.Color;
import ohos.agp.utils.Matrix;
import ohos.agp.utils.RectFloat;
import ohos.app.Context;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import com.hmos.compact.utils.AttrUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Rainyview class.
 *
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description A rainy rainy rainy view. ( ˘•灬•˘ )
 */
public class RainyView extends Component implements DrawTask, EstimateSizeListener, LayoutRefreshedListener {

    //the default size if set "wrap_content"
    private static final int DEFAULT_SIZE = 300;
    //Number of raindrops that can coexist at the same time
    private static final int DEFAULT_DROP_MAX_NUMBER = 30;
    //the default drop creation interval in millis
    private static final int DEFAULT_DROP_CREATION_INTERVAL = 50;
    //the default max length of drop
    private static final int DEFAULT_DROP_MAX_LENGTH = 50;
    //the default max length of drop
    private static final int DEFAULT_DROP_MIN_LENGTH = 10;
    //the default drop size of drop
    private static final int DEFAULT_DROP_SIZE = 15;
    //the default max speech value
    private static final float DEFAULT_DROP_MAX_SPEECH = 5f;
    //the default max speech value
    private static final float DEFAULT_DROP_MIN_SPEECH = 1f;
    // the default drop slope
    private static final float DEFAULT_DROP_SLOPE = -3f;
    private static final int DEFAULT_LEFT_CLOUD_COLOR = Color.getIntColor("#B0B0B0");
    private static final int DEFAULT_RIGHT_CLOUD_COLOR = Color.getIntColor("#DFDFDF");
    private static final int DEFAULT_RAIN_COLOR = Color.getIntColor("#80B9C5");
    private static final float CLOUD_SCALE_RATIO = 0.85f;
    private Paint mLeftCloudPaint;
    private Paint mRightCloudPaint;
    private Paint mRainPaint;

    private int mLeftCloudColor = DEFAULT_LEFT_CLOUD_COLOR;

    private int mRightCloudColor = DEFAULT_RIGHT_CLOUD_COLOR;

    private int mRainColor = DEFAULT_RAIN_COLOR;

    //There are two clouds in this view, includes the left cloud & right cloud
    //the left cloud's path
    private Path mLeftCloudPath;

    //the right cloud's path
    private Path mRightCloudPath;

    //the rain rect
    private RectFloat mRainRect;

    //the rain clip rect
    private RectFloat mRainClipRect;

    private AnimatorValue mLeftCloudAnimator;

    private AnimatorValue mRightCloudAnimator;

    private long mLeftCloudAnimatorPlayTime;

    private long mRightCloudAnimatorPlayTime;

    //The max translation x when do animation.
    private float mMaxTranslationX;

    //The left cloud animator value
    private float mLeftCloudAnimatorValue;

    //The right cloud animator value
    private float mRightCloudAnimatorValue;

    //The path for computing
    private Path mComputePath = new Path();

    //The matrix for computing
    private Matrix mComputeMatrix = new Matrix();

    //all the rain drops
    private List<RainDrop> mRainDrops;

    //help to record the removed drops, avoid "java.util.ConcurrentModificationException"
    private List<RainDrop> mRemovedRainDrops;

    private Stack<RainDrop> mRecycler;

    //the only random object
    private Random mOnlyRandom = new Random();

    private EventHandler mHandler = new EventHandler(EventRunner.getMainEventRunner());

    private int mRainDropMaxNumber = DEFAULT_DROP_MAX_NUMBER;

    private int mRainDropCreationInterval = DEFAULT_DROP_CREATION_INTERVAL;

    private int mRainDropMinLength = DEFAULT_DROP_MIN_LENGTH;

    private int mRainDropMaxLength = DEFAULT_DROP_MAX_LENGTH;

    private int mRainDropSize = DEFAULT_DROP_SIZE;

    private float mRainDropMaxSpeed = DEFAULT_DROP_MAX_SPEECH;

    private float mRainDropMinSpeed = DEFAULT_DROP_MIN_SPEECH;

    private float mRainDropSlope = DEFAULT_DROP_SLOPE;

    private long mRainDropCreationTime;

    /**
     * RainyView Constructor.
     *
     *  @param context - context for RainyView constructor
     *
     */
    public RainyView(Context context) {
        this(context, null);
        setEstimateSizeListener(this);
        setLayoutRefreshedListener(this);
        addDrawTask(this);

    }

    public RainyView(Context context, AttrSet attrSet) {

        this(context, attrSet, 0);
    }

    /**
     * RainyView Constructor.
     *
     *  @param context - context for RainyView constructor
     *  @param attrSet - attributes
     *  @param defStyleAttr - defStyle attribute
     *
     */
    public RainyView(Context context, AttrSet attrSet, int defStyleAttr) {
        super(context, attrSet, defStyleAttr);
        parseAttrs(attrSet);
        init();
        setEstimateSizeListener(this);
        setLayoutRefreshedListener(this);
        addDrawTask(this);
    }

    /**
     * RainyView Constructor.
     *
     *  @param context - context for RainyView constructor
     *  @param attrSet - attributes
     *  @param defStyleAttr - defStyle attribute
     *  @param defStyleRes - defStyle Res
     *
     */
    public RainyView(Context context, AttrSet attrSet, int defStyleAttr, int defStyleRes) {
        super(context, attrSet, defStyleRes);
        parseAttrs(attrSet);
        init();
        setEstimateSizeListener(this);
        setLayoutRefreshedListener(this);
        addDrawTask(this);
    }

    @Override
    public boolean onEstimateSize(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = EstimateSpec.getSize(widthMeasureSpec);
        int widthSpecMode = EstimateSpec.getMode(widthMeasureSpec);
        int heightSpecSize = EstimateSpec.getSize(heightMeasureSpec);
        int heightSpecMode = EstimateSpec.getMode(heightMeasureSpec);
        int w = widthSpecSize;
        int h = heightSpecSize;

        if (widthSpecMode == EstimateSpec.NOT_EXCEED && heightSpecMode == EstimateSpec.NOT_EXCEED) {
            w = DEFAULT_SIZE;
            h = DEFAULT_SIZE;
        } else if (widthSpecMode == EstimateSpec.NOT_EXCEED) {
            w = DEFAULT_SIZE;
            h = heightSpecSize;
        } else if (heightSpecMode == EstimateSpec.PRECISE) {
            w = widthSpecSize;
            h = DEFAULT_SIZE;
        }
        setEstimatedSize(w, h);
        return false;
    }

    private void parseAttrs(AttrSet attrs) {
        if (attrs == null) {
            return;
        }
        mLeftCloudColor = AttrUtils.getColorFromAttr(attrs, "left_cloud_color", DEFAULT_LEFT_CLOUD_COLOR);
        mRightCloudColor = AttrUtils.getColorFromAttr(attrs, "right_cloud_color", DEFAULT_RIGHT_CLOUD_COLOR);
        mRainColor = AttrUtils.getColorFromAttr(attrs, "raindrop_color", DEFAULT_RAIN_COLOR);
        mRainDropMaxNumber = AttrUtils.getIntFromAttr(attrs, "raindrop_max_number", DEFAULT_DROP_MAX_NUMBER);
        mRainDropMinLength = AttrUtils.getIntFromAttr(attrs, "raindrop_min_length", DEFAULT_DROP_MIN_LENGTH);
        mRainDropMaxLength = AttrUtils.getIntFromAttr(attrs, "raindrop_max_length", DEFAULT_DROP_MAX_LENGTH);
        mRainDropMinSpeed = AttrUtils.getFloatFromAttr(attrs, "raindrop_min_speed", DEFAULT_DROP_MIN_SPEECH);
        mRainDropMaxSpeed = AttrUtils.getFloatFromAttr(attrs, "raindrop_max_speed", DEFAULT_DROP_MAX_SPEECH);
        mRainDropCreationInterval = AttrUtils.getIntFromAttr(attrs, "raindrop_creation_interval",
                DEFAULT_DROP_CREATION_INTERVAL);
        mRainDropSize = AttrUtils.getIntFromAttr(attrs, "raindrop_size", DEFAULT_DROP_SIZE);
        mRainDropSlope = AttrUtils.getFloatFromAttr(attrs, "raindrop_slope", DEFAULT_DROP_SLOPE);
        checkValue();
    }

    private void init() {
        mLeftCloudPaint = new Paint();
        Color hmosColor = RainyView.changeParamToColor(mLeftCloudColor);
        mLeftCloudPaint.setColor(hmosColor);
        mLeftCloudPaint.setStyle(Paint.Style.FILL_STYLE);

        mRightCloudPaint = new Paint();
        Color hmosColor1 = RainyView.changeParamToColor(mRightCloudColor);
        mRightCloudPaint.setColor(hmosColor1);
        mRightCloudPaint.setStyle(Paint.Style.FILL_STYLE);

        mRainPaint = new Paint();
        mRainPaint.setStrokeCap(Paint.StrokeCap.ROUND_CAP);
        Color hmosColor2 = RainyView.changeParamToColor(mRainColor);
        mRainPaint.setColor(hmosColor2);
        mRainPaint.setStyle(Paint.Style.STROKE_STYLE);
        mRainPaint.setStrokeWidth(mRainDropSize);

        mLeftCloudPath  = new Path();
        mRightCloudPath = new Path();
        mRainRect = new RectFloat();
        mRainClipRect = new RectFloat();

        mRainDrops = new ArrayList<>(mRainDropMaxNumber);
        mRemovedRainDrops = new ArrayList<>(mRainDropMaxNumber);
        mRecycler = new Stack<>();

    }

    @Override
    public void onRefreshed(Component component) {

        int w;
        w = component.getWidth();
        int h;
        h = component.getHeight();
        int oldweight = 0;
        int oldheight = 0;
        stop();

        mLeftCloudPath.reset();
        mRightCloudPath.reset();

        //view's center x coordinate
        float centerX;
        centerX = w / 2;
        //get the min size
        float minSize = Math.min(w, h);
        //************************compute left cloud**********************
        //the width of cloud
        float leftCloudWidth = minSize / 2.5f;
        //the bottom height of cloud
        float leftCloudBottomHeight = leftCloudWidth / 3f;
        //the bottom round radius of cloud
        float leftCloudBottomRoundRadius = leftCloudBottomHeight;
        //the distance of the cloud on the right
        float rightCloudTranslateX;
        rightCloudTranslateX = leftCloudWidth * 2 / 3;
        //the left cloud end x coordinate
        float leftCloudEndX = (w - leftCloudWidth - leftCloudWidth * CLOUD_SCALE_RATIO / 2) / 2 + leftCloudWidth;
        //clouds' end y coordinate
        float leftCloudEndY = h / 3;
        //add the bottom round rect
        mLeftCloudPath.addRoundRect(new RectFloat(leftCloudEndX - leftCloudWidth, leftCloudEndY - leftCloudBottomHeight,
                leftCloudEndX, leftCloudEndY), leftCloudBottomHeight,
                leftCloudBottomRoundRadius, Path.Direction.CLOCK_WISE);

        float leftCloudTopCenterY = leftCloudEndY - leftCloudBottomHeight;

        float leftCloudRightTopCenterX = leftCloudEndX - leftCloudBottomRoundRadius;

        float leftCloudLeftTopCenterX  = leftCloudEndX - leftCloudWidth + leftCloudBottomRoundRadius;


        mLeftCloudPath.addCircle(leftCloudRightTopCenterX, leftCloudTopCenterY,
                leftCloudBottomRoundRadius * 3 / 4, Path.Direction.CLOCK_WISE);
        mLeftCloudPath.addCircle(leftCloudLeftTopCenterX, leftCloudTopCenterY,
                leftCloudBottomRoundRadius / 2, Path.Direction.CLOCK_WISE);
        //*******************************Done*****************************
        //************************compute right cloud**********************
        //The cloud on the right is CLOUD_SCALE_RATIO size of the left
        //the right cloud center x
        float rightCloudCenterX;
        rightCloudCenterX = rightCloudTranslateX + centerX - leftCloudWidth / 2;
        RectFloat calculateRect = new RectFloat();
        //compute the left cloud's path bounds
        mLeftCloudPath.computeBounds(calculateRect);
        mComputeMatrix.reset();
        mComputeMatrix.preTranslate(rightCloudTranslateX, -calculateRect.getHeight() * (1 - CLOUD_SCALE_RATIO) / 2);
        mComputeMatrix.postScale(CLOUD_SCALE_RATIO, CLOUD_SCALE_RATIO, rightCloudCenterX, leftCloudEndY);
        mLeftCloudPath.transformToNewPath(mComputeMatrix, mRightCloudPath);

        float left = calculateRect.left + leftCloudBottomRoundRadius;
        //compute the right cloud's path bounds
        mRightCloudPath.computeBounds(calculateRect);
        float right = calculateRect.right;
        float top   = calculateRect.bottom;

        //************************compute right cloud**********************
        //compute the rect of rain...

        mRainRect.modify(left, top, right, (h * 3 / 4f));
        mRainClipRect.modify(0, mRainRect.top, w, mRainRect.bottom);
        mMaxTranslationX = leftCloudBottomRoundRadius / 2;
        setupAnimator();
    }

    private void setupAnimator() {
        mLeftCloudAnimatorPlayTime = 0;
        mRightCloudAnimatorPlayTime = 0;
        mLeftCloudAnimator = new AnimatorValue();
        mLeftCloudAnimator.setLoopedCount(-1);
        mLeftCloudAnimator.setDuration(1000);
        mLeftCloudAnimator.setCurveType(ohos.agp.animation.Animator.CurveType.LINEAR);
        mLeftCloudAnimator.setValueUpdateListener(new AnimatorValue.ValueUpdateListener() {
            @Override
            public void onUpdate(AnimatorValue animatorValue, float value) {
                mLeftCloudAnimatorValue = value;
                invalidate();
            }
        });

        mRightCloudAnimator = new AnimatorValue();
        mRightCloudAnimator.setLoopedCount(-1);
        mRightCloudAnimator.setDuration(800);
        mRightCloudAnimator.setCurveType(ohos.agp.animation.Animator.CurveType.LINEAR);
        mRightCloudAnimator.setValueUpdateListener(new AnimatorValue.ValueUpdateListener() {
            @Override
            public void onUpdate(AnimatorValue animatorValue, float v) {
                mRightCloudAnimatorValue = v;

                invalidate();
            }
        });

        mLeftCloudAnimator.start();
        mRightCloudAnimator.start();
        mHandler.postTask(mTask);
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {

        canvas.save();
        //canvas.drawRect(mRainRect, new Paint());
        canvas.clipRect(mRainClipRect);
        drawRainDrops(canvas);
        canvas.restore();
        mComputeMatrix.reset();
        mComputeMatrix.postTranslate((mMaxTranslationX / 2) * mRightCloudAnimatorValue, 0);
        mRightCloudPath.transformToNewPath(mComputeMatrix, mComputePath);
        canvas.drawPath(mComputePath, mRightCloudPaint);
        mComputeMatrix.reset();
        mComputeMatrix.postTranslate(mMaxTranslationX * mLeftCloudAnimatorValue, 0);
        mLeftCloudPath.transformToNewPath(mComputeMatrix, mComputePath);
        canvas.drawPath(mComputePath, mLeftCloudPaint);

    }

    /**
     * Start the animation.
     * */
    public void start() {

        if (mLeftCloudAnimator != null && !mLeftCloudAnimator.isRunning()) {
            mLeftCloudAnimator.start();
        }

        if (mRightCloudAnimator != null && !mRightCloudAnimator.isRunning()) {
            mRightCloudAnimator.start();
        }

        mHandler.removeTask(mTask);
        mHandler.postTask(mTask);
    }

    /**
     * Stop the animation.
     * */
    public void stop() {

        if (mLeftCloudAnimator != null && mLeftCloudAnimator.isRunning()) {
            mLeftCloudAnimator.cancel();
        }

        if (mRightCloudAnimator != null && mRightCloudAnimator.isRunning()) {
            mRightCloudAnimator.cancel();
        }

        mHandler.removeTask(mTask);
    }

    /**
     * Release this view.
     * */
    public void release() {
        stop();

        if (mLeftCloudAnimator != null) {
            //Need not implement
        }

        if (mRightCloudAnimator != null) {
            //Need not implement
        }

        mRemovedRainDrops.clear();
        mRainDrops.clear();
        mRecycler.clear();
        mHandler = null;
    }

    /**
     * To optimize performance, use recycler {@link #mRecycler}.
     * */
    private RainDrop obtainRainDrop() {
        if (mRecycler.isEmpty()) {
            return new RainDrop();
        }

        return mRecycler.pop();
    }

    /**
     * Recycling the drop that are no longer in use.
     * */
    private void recycle(RainDrop rainDrop) {
        if (rainDrop == null) {
            return;
        }

        if (mRecycler.size() >= mRainDropMaxNumber) {
            mRecycler.pop();
        }

        mRecycler.push(rainDrop);
    }

    /**
     * The drop's handled task.
     * Call handler to schedule the task.
     * */
    private Runnable mTask = new Runnable() {
        @Override
        public void run() {
            createRainDrop();
            updateRainDropState();
            mHandler.postTask(this, 20);
        }
    };

    /**
     * Now create a random raindrop.
     * */
    private void createRainDrop() {

        if (mRainDrops.size() >= mRainDropMaxNumber || mRainRect.isEmpty()) {
            return;
        }

        long current = System.currentTimeMillis();
        if ((current - mRainDropCreationTime) < mRainDropCreationInterval) {
            return;
        }

        if (mRainDropMinLength > mRainDropMaxLength || mRainDropMinSpeed > mRainDropMaxSpeed) {
            throw new IllegalArgumentException("The minimum value cannot be greater than the maximum value.");
        }

        mRainDropCreationTime = current;

        RainDrop rainDrop = obtainRainDrop();
        rainDrop.slope = mRainDropSlope;
        rainDrop.speedX = mRainDropMinSpeed + mOnlyRandom.nextFloat() * mRainDropMaxSpeed;
        rainDrop.speedY = rainDrop.speedX * Math.abs(rainDrop.slope);

        float rainDropLength = mRainDropMinLength + mOnlyRandom.nextInt(mRainDropMaxLength - mRainDropMinLength);
        double degree = Math.toDegrees(Math.atan(rainDrop.slope));

        rainDrop.mxLength = (float) Math.abs(Math.cos(degree * Math.PI / 180) * rainDropLength);
        rainDrop.myLength = (float) Math.abs(Math.sin(degree * Math.PI / 180) * rainDropLength);

        //random x coordinate
        rainDrop.mx = mRainRect.left + mOnlyRandom.nextInt((int) mRainRect.getWidth());
        //the fixed y coordinate
        rainDrop.my = mRainRect.top - rainDrop.myLength;

        mRainDrops.add(rainDrop);

    }

    /**
     * Update all the raindrops state.
     * */
    private void updateRainDropState() {
        mRemovedRainDrops.clear();

        for (RainDrop rainDrop : mRainDrops) {
            if (rainDrop.my - rainDrop.myLength > mRainRect.bottom) {
                mRemovedRainDrops.add(rainDrop);
                recycle(rainDrop);
            } else {
                if (rainDrop.slope >= 0) {
                    rainDrop.mx += rainDrop.speedX;
                } else {
                    rainDrop.mx -= rainDrop.speedX;
                }
                rainDrop.my += rainDrop.speedY;
            }
        }

        if (!mRemovedRainDrops.isEmpty()) {
            mRainDrops.removeAll(mRemovedRainDrops);
        }

        if (!mRainDrops.isEmpty()) {
            invalidate();
        }
    }

    private void drawRainDrops(Canvas canvas) {

        for (RainDrop rainDrop : mRainDrops) {
            canvas.drawLine(rainDrop.mx, rainDrop.my,
                    rainDrop.slope > 0 ? rainDrop.mx + rainDrop.mxLength : rainDrop.mx - rainDrop.mxLength,
                    rainDrop.my + rainDrop.myLength,
                    mRainPaint);
        }

    }

    /**
     * The rain drop class.
     * */
    private class RainDrop {
        //the drop's x coordinate speed
        float speedX;
        //the drop's y coordinate speed
        float speedY;
        //the drop's x length
        float mxLength;
        //the drop's y length
        float myLength;
        //the drop's start x
        float mx;
        //the drop's start y
        float my;
        //the drop's slope
        float slope;
    }

    private void checkValue() {
        checkRainDropCreationIntervalValue();
        checkRainDropLengthValue();
        checkRainDropManNumberValue();
        checkRainDropSizeValue();
        checkRainDropSpeedValue();
        checkRainDropSlopeValue();
    }

    private void checkRainDropManNumberValue() {
        if (mRainDropMaxNumber < 0) {
            mRainDropMaxNumber = DEFAULT_DROP_MAX_NUMBER;
        }
    }

    private void checkRainDropSizeValue() {
        if (mRainDropSize < 0) {
            mRainDropSize = DEFAULT_DROP_SIZE;
        }
    }

    private void checkRainDropCreationIntervalValue() {
        if (mRainDropCreationInterval < 0) {
            mRainDropCreationInterval = DEFAULT_DROP_CREATION_INTERVAL;
        }
    }

    private void checkRainDropLengthValue() {
        if (mRainDropMinLength < 0
                || mRainDropMaxLength < 0) {
            mRainDropMinLength = DEFAULT_DROP_MIN_LENGTH;
            mRainDropMaxLength = DEFAULT_DROP_MAX_LENGTH;
        }
    }

    private void checkRainDropSpeedValue() {
        if (mRainDropMinSpeed < 0
                || mRainDropMaxSpeed < 0) {
            mRainDropMinSpeed = DEFAULT_DROP_MIN_SPEECH;
            mRainDropMaxSpeed = DEFAULT_DROP_MAX_SPEECH;
        }
    }

    private void checkRainDropSlopeValue() {
        if (mRainDropSlope < 0) {
            mRainDropSlope = DEFAULT_DROP_SLOPE;
        }
    }

    /**
     * Set the color of the left cloud.
     *
     * @param leftCloudColor - value to set left cloud colour
     *
     */
    public void setLeftCloudColor(int leftCloudColor) {
        this.mLeftCloudColor = leftCloudColor;
        Color hmosColor = RainyView.changeParamToColor(mLeftCloudColor);
        mLeftCloudPaint.setColor(hmosColor);
        invalidate();
    }

    /**
     * Get the color of the left cloud.
     * */
    public int getLeftCloudColor() {
        return mLeftCloudColor;
    }

    /**
     * Set the color of the right cloud.
     *
     * @param rightCloudColor - value to set right cloud colour
     *
     */
    public void setRightCloudColor(int rightCloudColor) {
        this.mRightCloudColor = rightCloudColor;
        Color hmosColor = RainyView.changeParamToColor(mRightCloudColor);
        mRightCloudPaint.setColor(hmosColor);
        invalidate();
    }

    /**
     * Get the color of the right cloud.
     * */
    public int getRightCloudColor() {
        return mRightCloudColor;
    }

    /**
     * Set the color of all the raindrops.
     *
     * @param rainDropColor - value to set Raindrop colour
     *
     */
    public void setRainDropColor(int rainDropColor) {
        this.mRainColor = rainDropColor;
        Color hmosColor = RainyView.changeParamToColor(mRainColor);
        mRainPaint.setColor(hmosColor);
        invalidate();
    }

    /**
     * Get the color of all the raindrops.
     * */
    public int getRainDropColor() {
        return mRainColor;
    }

    /**
     * Get the max number of the {@link RainDrop}.
     * */
    public int getRainDropMaxNumber() {
        return mRainDropMaxNumber;
    }

    /**
     * Set the max number of the {@link RainDrop}.
     *
     * @param rainDropMaxNumber - value to set maximum number of raindrops
     *
     */
    public void setRainDropMaxNumber(int rainDropMaxNumber) {
        this.mRainDropMaxNumber = rainDropMaxNumber;
        checkRainDropManNumberValue();
    }

    /**
     * Get the creation interval of the {@link RainDrop}.
     * */
    public int getRainDropCreationInterval() {
        return mRainDropCreationInterval;
    }


    /**
     * Set the creation interval of the {@link RainDrop}.
     *
     * @param rainDropCreationInterval - value to set Raindrop creation interval
     *
     */
    public void setRainDropCreationInterval(int rainDropCreationInterval) {
        this.mRainDropCreationInterval = rainDropCreationInterval;
        checkRainDropCreationIntervalValue();
        invalidate();
    }

    /**
     * Get the min length of the {@link RainDrop}.
     * */
    public int getRainDropMinLength() {
        return mRainDropMinLength;
    }

    /**
     * Set the min length of the {@link RainDrop}.
     *
     * @param rainDropMinLength - value to set minimum length of RainDrop
     *
     */
    public void setRainDropMinLength(int rainDropMinLength) {
        this.mRainDropMinLength = rainDropMinLength;
        checkRainDropLengthValue();
    }

    /**
     * Get the max length of the {@link RainDrop}.
     * */
    public int getRainDropMaxLength() {
        return mRainDropMaxLength;
    }

    /**
     * Set the max length of the {@link RainDrop}.
     *
     * @param rainDropMaxLength - value to set maximum length of RainDrop
     *
     */
    public void setRainDropMaxLength(int rainDropMaxLength) {
        this.mRainDropMaxLength = rainDropMaxLength;
        checkRainDropLengthValue();
    }

    /**
     * Get the size of the {@link RainDrop}.
     * */
    public int getRainDropSize() {
        return mRainDropSize;
    }

    /**
     * Set the size of the {@link RainDrop}.
     *
     * @param rainDropSize - value to set Raindrop size
     *
     */
    public void setRainDropSize(int rainDropSize) {
        this.mRainDropSize = rainDropSize;
        checkRainDropSizeValue();
    }

    /**
     * Get the max speed of the {@link RainDrop}.
     * */
    public float getRainDropMaxSpeed() {
        return mRainDropMaxSpeed;
    }

    /**
     * Set the max speed of the {@link RainDrop}.
     *
     * @param rainDropMaxSpeed - value to set maximum speed of RainDrop
     *
     */
    public void setRainDropMaxSpeed(float rainDropMaxSpeed) {
        this.mRainDropMaxSpeed = rainDropMaxSpeed;
        checkRainDropSpeedValue();
    }

    /**
     * Get the minimum speed of the {@link RainDrop}.
     * */
    public float getRainDropMinSpeed() {
        return mRainDropMinSpeed;
    }

    /**
     * Set the minimum speed of the {@link RainDrop}.
     *
     * @param rainDropMinSpeed - value to set minimum speed of RainDrop
     *
     */
    public void setRainDropMinSpeed(float rainDropMinSpeed) {
        this.mRainDropMinSpeed = rainDropMinSpeed;
        checkRainDropSpeedValue();
    }

    /**
     * Get the slope of the {@link RainDrop}.
     *
     * */
    public float getRainDropSlope() {
        return mRainDropSlope;
    }

    /**
     * Set the slope of the {@link RainDrop}.
     *
     * @param rainDropSlope - the value of raindrop slope that needs to be set to rain drops
     *
     */
    public void setRainDropSlope(float rainDropSlope) {
        this.mRainDropSlope = rainDropSlope;
        checkRainDropSlopeValue();
    }

    public static Color changeParamToColor(int color) {
        Color hmosColor = new Color(color);
        return hmosColor;
    }
}

