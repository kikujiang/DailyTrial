package day.day.up.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import day.day.up.R

class CircleCountView @JvmOverloads constructor(ctx:Context,attr: AttributeSet?=null,defStyle:Int=0) :View(ctx,attr,defStyle){

    private val tag:String = "count"

    private var mWidth:Float = 200f
    private var mHeight:Float = 200f
    private var mRadius:Float = 100f
    private var mStrokeWidth:Float = 1f

    private var controlX:Float = 0f
    private var controlY:Float = 0f
    private var waveY:Float = 0f


    private lateinit var mPaint:Paint
    private lateinit var mPaint1:Paint
    private lateinit var mCanvas: Canvas
    private lateinit var mPath: Path
    private lateinit var mode: PorterDuffXfermode


    init {
        Log.d(tag,"start")
        initAttrs(ctx, attr, defStyle)
        Log.d(tag,"middle")
        initPaint()
        Log.d(tag,"end")

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
            style = Paint.Style.FILL
        }
        mPaint1 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
        }

        waveY = 7f/8*height
        controlY = 17f / 16*height

        mPath = Path()

        mCanvas = Canvas().apply {
            mPaint1.strokeWidth = mStrokeWidth
            mPaint1.color = Color.GREEN
            drawCircle(mWidth,mHeight,mRadius,mPaint1)
        }

        mode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    }

}