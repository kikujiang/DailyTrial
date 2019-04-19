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

    private var totalH:Int = 0
    private var totalW:Int = 0
    private var currentTop:Int = 0

    private lateinit var bitmap: Bitmap


    private lateinit var mPaint:Paint
    private lateinit var mPath: Path
    private lateinit var mode: PorterDuffXfermode
    private lateinit var rectF: RectF


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

        rectF.top = currentTop.toFloat()

        val sc = canvas?.saveLayer(0f,0f,totalW.toFloat(),totalH.toFloat(),mPaint)
        canvas?.drawBitmap(bitmap,0f,0f,null)
        mPaint.xfermode = mode
        canvas?.drawRect(rectF,mPaint)
        mPaint.xfermode = null

        canvas?.restoreToCount(sc!!)

        if(currentTop > 0){
            currentTop--
            postInvalidate()
        }

        if(currentTop == 0){
            currentTop = bitmap.height
            postInvalidate()
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(bitmap.width,bitmap.height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        totalW = w
        totalH = h
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
        mPaint = Paint().apply {
            style = Paint.Style.FILL
            isDither = true
            isAntiAlias = true
            isFilterBitmap = true
            color = Color.RED
        }

        bitmap = BitmapFactory.decodeResource(resources,R.mipmap.test)

        currentTop = bitmap.height

        mPath = Path()

        rectF = RectF(0f,currentTop.toFloat(),bitmap.width.toFloat(),bitmap.height.toFloat())

        mode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    }

}