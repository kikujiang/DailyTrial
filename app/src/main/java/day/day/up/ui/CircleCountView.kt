package day.day.up.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import day.day.up.R

class CircleCountView @JvmOverloads constructor(ctx:Context,attr: AttributeSet,defStyle:Int) :View(ctx,attr,defStyle){

    private val mContext:Context = ctx

    private var mWidth:Float = 200f
    private var mHeight:Float = 200f
    private var mRadius:Float = 100f
    private var mStrokeWidth:Float = 95f

    private lateinit var mPaint:Paint

    init {
        initAttrs(ctx, attr, defStyle)
        initPaint()
    }

    private var index:Int = 0
        set(value) {
            field = value
            invalidate()
        }

    private var mBorderWidth:Float = 10f
        set(value) {
            field = value
            invalidate()
        }

    private var mBorderColor:Int = Color.parseColor("#ff9900")
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mPaint.strokeWidth = mStrokeWidth
        canvas?.drawCircle(mWidth,mHeight,mRadius,mPaint)
    }

    private fun initAttrs(ctx:Context,attr: AttributeSet?,defStyle:Int){
        val array = ctx.obtainStyledAttributes(attr, R.styleable.CircleCountView,defStyle,0)
        (0..array.length())
            .asSequence()
            .map { array.getIndex(it) }
            .forEach {
                when(it){
                    R.styleable.CircleCountView_percent ->
                        index = array.getInt(it,0)
                    R.styleable.CircleCountView_border_width ->
                        mBorderWidth = array.getDimension(it,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,5f,resources.displayMetrics))
                    R.styleable.CircleCountView_border_color ->
                        mBorderColor = array.getColor(it,Color.parseColor("#ff9900"))
                }
            }
        array.recycle()
    }

    private fun initPaint(){
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
        }
    }

}