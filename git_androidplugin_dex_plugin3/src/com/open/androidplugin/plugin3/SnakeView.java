
package com.open.androidplugin.plugin3;

import java.util.ArrayList;
import java.util.Random;

import com.open.androidplugin.plugin3.resource.ImageFactory;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * SnakeView: implementation of a simple game of Snake
 * 
 * 
 */
public class SnakeView extends TileView implements OnTouchListener,OnGestureListener{
	
	private GestureDetector mGestureDetector;//手势监听器
	private final int FLING_MIN_DISTANCE=50;

    private static final String TAG = "SnakeView";

    /**
     * Current mode of application: READY to run, RUNNING, or you have already
     * lost. static final ints are used instead of an enum for performance
     * reasons.
     */
    private int mMode = READY;
    public static final int PAUSE = 0;
    public static final int READY = 1;
    public static final int RUNNING = 2;
    public static final int LOSE = 3;

    /**
     * Current direction the snake is headed.
     */
    private int mDirection = NORTH;
    private int mNextDirection = NORTH;
    private static final int NORTH = 1;//北
    private static final int SOUTH = 2;//南
    private static final int EAST = 3;//东
    private static final int WEST = 4;//西

    /**
     * Labels for the drawables that will be loaded into the TileView class
     */
    private static final int RED_STAR = 1;
    private static final int YELLOW_STAR = 2;
    private static final int GREEN_STAR = 3;
    private static final int FOOD_APPLE = 4;

    /**
     * mScore: used to track the number of apples captured 追踪
     * mMoveDelay: number of milliseconds between snake movements. This will decrease as apples are captured. 减少   捕获
     */
    private long mScore = 0;
    private long mMoveDelay = 600;
    /**
     * mLastMove: tracks the absolute time when the snake last moved, and is used
     * to determine if a move should be made based on mMoveDelay.
     */
    private long mLastMove;

    /**
     * mSnakeTrail: a list of Coordinates that make up the snake's body 列表中的坐标，组成蛇的身体
     * mAppleList: the secret location of the juicy apples the snake craves. 蛇渴望 的多汁的苹果的秘密地点
     */
    private ArrayList<Coordinate> mSnakeTrail = new ArrayList<Coordinate>();
    private ArrayList<Coordinate> mAppleList = new ArrayList<Coordinate>();

    /**
     * Everyone needs a little randomness in their life
     */
    private static final Random RNG = new Random();

    /**
     * Create a simple handler that we can use to cause animation to happen.  We
     * set ourselves as a target and we can use the sleep()
     * function to cause an update/invalidate to occur at a later date.
     */
    private RefreshHandler mRedrawHandler = new RefreshHandler();

    class RefreshHandler extends Handler 
    {
        @Override
        public void handleMessage(Message msg) 
        {
            SnakeView.this.update();
            SnakeView.this.invalidate();
        }

        public void sleep(long delayMillis) 
        {
        	this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    };


    /**
     * Constructs a SnakeView based on inflation from XML
     * 
     * @param context
     * @param attrs
     */
    public SnakeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        initSnakeView();
   }

    public SnakeView(Context context, AttributeSet attrs, int defStyle) {
    	super(context, attrs, defStyle);
    	this.mContext=context;
    	initSnakeView();
    }

    Context mContext;
    private void initSnakeView() {
    	
    	Toast.makeText(mContext, "-----按向上滑动开始游戏-----", Toast.LENGTH_LONG).show();
    	this.setOnTouchListener(this);
		this.setLongClickable(true);
		mGestureDetector=new GestureDetector(this);
    	
//        setFocusable(true);
       
        resetTiles(5);

        Drawable redstar = new BitmapDrawable(ImageFactory.getImage("redstar.png"));  
        Drawable yellowstar = new BitmapDrawable(ImageFactory.getImage("yellowstar.png"));
        Drawable greenstar = new BitmapDrawable(ImageFactory.getImage("greenstar.png"));
        Drawable food_apple = new BitmapDrawable(ImageFactory.getImage("apple.jpg"));
        
        loadTile(RED_STAR, redstar);
        loadTile(YELLOW_STAR, yellowstar);
        loadTile(GREEN_STAR, greenstar);
        loadTile(FOOD_APPLE, food_apple);
        
        this.setBackgroundColor(Color.WHITE);
    }
    

    private void initNewGame() {
        mSnakeTrail.clear();
        mAppleList.clear();

        // For now we're just going to load up a short default eastbound snake
        // that's just turned north

        mSnakeTrail.add(new Coordinate(7, 7));
        mSnakeTrail.add(new Coordinate(6, 7));
        mSnakeTrail.add(new Coordinate(5, 7));
        mSnakeTrail.add(new Coordinate(4, 7));
        mSnakeTrail.add(new Coordinate(3, 7));
        mSnakeTrail.add(new Coordinate(2, 7));
        mNextDirection = NORTH;

        // Two apples to start with
        addRandomApple();
        addRandomApple();

        mMoveDelay = 600;
        mScore = 0;
    }


    /**
     * Given a ArrayList of coordinates, we need to flatten them into an array of
     * ints before we can stuff them into a map for flattening and storage.
     * 
     * @param cvec : a ArrayList of Coordinate objects
     * @return : a simple array containing the x/y values of the coordinates
     * as [x1,y1,x2,y2,x3,y3...]
     */
    private int[] coordArrayListToArray(ArrayList<Coordinate> cvec) {
        int count = cvec.size();
        int[] rawArray = new int[count * 2];
        for (int index = 0; index < count; index++) {
            Coordinate c = cvec.get(index);
            rawArray[2 * index] = c.x;
            rawArray[2 * index + 1] = c.y;
        }
        return rawArray;
    }

    /**
     * Save game state so that the user does not lose anything
     * if the game process is killed while we are in the 
     * background.
     * 
     * @return a Bundle with this view's state
     */
    public Bundle saveState() {
        Bundle map = new Bundle();

        map.putIntArray("mAppleList", coordArrayListToArray(mAppleList));
        map.putInt("mDirection", Integer.valueOf(mDirection));
        map.putInt("mNextDirection", Integer.valueOf(mNextDirection));
        map.putLong("mMoveDelay", Long.valueOf(mMoveDelay));
        map.putLong("mScore", Long.valueOf(mScore));
        map.putIntArray("mSnakeTrail", coordArrayListToArray(mSnakeTrail));

        return map;
    }

    /**
     * Given a flattened array of ordinate pairs, we reconstitute them into a
     * ArrayList of Coordinate objects
     * 
     * @param rawArray : [x1,y1,x2,y2,...]
     * @return a ArrayList of Coordinates
     */
    private ArrayList<Coordinate> coordArrayToArrayList(int[] rawArray) {
        ArrayList<Coordinate> coordArrayList = new ArrayList<Coordinate>();

        int coordCount = rawArray.length;
        for (int index = 0; index < coordCount; index += 2) {
            Coordinate c = new Coordinate(rawArray[index], rawArray[index + 1]);
            coordArrayList.add(c);
        }
        return coordArrayList;
    }

    /**
     * Restore game state if our process is being relaunched
     * 
     * @param icicle a Bundle containing the game state
     */
    public void restoreState(Bundle icicle) {
        setMode(PAUSE);

        mAppleList = coordArrayToArrayList(icicle.getIntArray("mAppleList"));
        mDirection = icicle.getInt("mDirection");
        mNextDirection = icicle.getInt("mNextDirection");
        mMoveDelay = icicle.getLong("mMoveDelay");
        mScore = icicle.getLong("mScore");
        mSnakeTrail = coordArrayToArrayList(icicle.getIntArray("mSnakeTrail"));
    }

    /*
     * handles key events in the game. Update the direction our snake is traveling
     * based on the DPAD. Ignore events that would cause the snake to immediately
     * turn back on itself.
     * 
     * (non-Javadoc)
     * 
     * @see android.view.View#onKeyDown(int, android.os.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
    	
    	 Toast.makeText(mContext, "-----show-----", Toast.LENGTH_LONG).show();
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP)
        {
            if (mMode == READY | mMode == LOSE) 
            {
                /*
                 * At the beginning of the game, or the end of a previous one,
                 * we should start a new game.
                 */
                initNewGame();
                setMode(RUNNING);
                update();
                return (true);
            }

            if (mMode == PAUSE) 
            {
                /*
                 * If the game is merely paused, we should just continue where
                 * we left off.
                 */
                setMode(RUNNING);
                update();
                return (true);
            }

            if (mDirection != SOUTH) 
            {
                mNextDirection = NORTH;
            }
            return (true);
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) 
        {
            if (mDirection != NORTH) 
            {
                mNextDirection = SOUTH;
            }
            return (true);
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) 
        {
            if (mDirection != EAST) 
            {
                mNextDirection = WEST;
            }
            return (true);
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) 
        {
            if (mDirection != WEST) 
            {
                mNextDirection = EAST;
            }
            return (true);
        }

        return super.onKeyDown(keyCode, msg);
    }

    /**
     * Sets the TextView that will be used to give information (such as "Game
     * Over" to the user.
     * 
     * @param newView
     */
    public void setTextView(TextView newView) {
//        mStatusText = newView;
    }

    /**
     * Updates the current mode of the application (RUNNING or PAUSED or the like)
     * as well as sets the visibility of textview for notification
     * 
     * @param newMode
     */
    public void setMode(int newMode) 
    {
        int oldMode = mMode;
        mMode = newMode;

        if (newMode == RUNNING & oldMode != RUNNING) 
        {
            update();
            return;
        }

    }

    /**
     * Selects a random location within the garden that is not currently covered
     * by the snake. Currently _could_ go into an infinite loop if the snake
     * currently fills the garden, but we'll leave discovery of this prize to a
     * truly excellent snake-player.
     * 
     */
    private void addRandomApple() 
    {
        Coordinate newCoord = null;
        boolean found = false;
        while (!found) {
            // Choose a new location for our apple
            int newX = 1 + RNG.nextInt(mXTileCount - 2);
            int newY = 1 + RNG.nextInt(mYTileCount - 2);
            newCoord = new Coordinate(newX, newY);

            // Make sure it's not already under the snake
            boolean collision = false;
            int snakelength = mSnakeTrail.size();
            for (int index = 0; index < snakelength; index++) {
                if (mSnakeTrail.get(index).equals(newCoord)) {
                    collision = true;
                }
            }
            // if we're here and there's been no collision, then we have
            // a good location for an apple. Otherwise, we'll circle back
            // and try again
            found = !collision;
        }
        if (newCoord == null) {
            Log.e(TAG, "Somehow ended up with a null newCoord!");
        }
        mAppleList.add(newCoord);
    }


    /**
     * Handles the basic update loop, checking to see if we are in the running
     * state, determining if a move should be made, updating the snake's location.
     */
    public void update() 
    {
        if (mMode == RUNNING) 
        {
            long now = System.currentTimeMillis();

            if (now - mLastMove > mMoveDelay) 
            {
                clearTiles();
                updateWalls();
                updateSnake();
                updateApples();
                mLastMove = now;
            }
            mRedrawHandler.sleep(mMoveDelay);
        }

    }

    /**
     * Draws some walls.
     * 
     */
    private void updateWalls() 
    {
        for (int x = 0; x < mXTileCount; x++) 
        {
            setTile(GREEN_STAR, x, 0);
            setTile(GREEN_STAR, x, mYTileCount - 1);
        }
        for (int y = 1; y < mYTileCount - 1; y++) 
        {
            setTile(GREEN_STAR, 0, y);
            setTile(GREEN_STAR, mXTileCount - 1, y);
        }
    }

    /**
     * Draws some apples.
     * 
     */
    private void updateApples() 
    {
        for (Coordinate c : mAppleList) 
        {
//            setTile(YELLOW_STAR, c.x, c.y);
        	setTile(FOOD_APPLE, c.x, c.y);
        }
    }

    /**
     * Figure out which way the snake is going, see if he's run into anything (the
     * walls, himself, or an apple). If he's not going to die, we then add to the
     * front and subtract from the rear in order to simulate motion. If we want to
     * grow him, we don't subtract from the rear.
     * 
     */
    private void updateSnake() 
    {
        boolean growSnake = false;

        // grab the snake by the head
        Coordinate head = mSnakeTrail.get(0);
        Coordinate newHead = new Coordinate(1, 1);

        mDirection = mNextDirection;

        switch (mDirection) 
        {
	        case EAST: 
	        {
	            newHead = new Coordinate(head.x + 1, head.y);
	            break;
	        }
	        case WEST: 
	        {
	            newHead = new Coordinate(head.x - 1, head.y);
	            break;
	        }
	        case NORTH: 
	        {
	            newHead = new Coordinate(head.x, head.y - 1);
	            break;
	        }
	        case SOUTH: 
	        {
	            newHead = new Coordinate(head.x, head.y + 1);
	            break;
	        }
        }

        // Collision detection 碰撞检测
        // For now we have a 1-square wall around the entire arena
        if ((newHead.x < 1) || (newHead.y < 1) || (newHead.x > mXTileCount - 2)|| (newHead.y > mYTileCount - 2)) 
        {
            setMode(LOSE);
            return;
        }

        // Look for collisions with itself //自身检测，头有没有碰到自身
        int snakelength = mSnakeTrail.size();
        for (int snakeindex = 0; snakeindex < snakelength; snakeindex++) 
        {
            Coordinate c = mSnakeTrail.get(snakeindex);
            if (c.equals(newHead)) 
            {
                setMode(LOSE);
                return;
            }
        }

        // Look for apples 有没有碰到苹果
        int applecount = mAppleList.size();
        for (int appleindex = 0; appleindex < applecount; appleindex++) 
        {
            Coordinate c = mAppleList.get(appleindex);
            if (c.equals(newHead)) 
            {
                mAppleList.remove(c);
                addRandomApple();
                
                mScore++;
                mMoveDelay *= 0.9;

                growSnake = true;
            }
        }

        // push a new head onto the ArrayList and pull off the tail
        mSnakeTrail.add(0, newHead);
        // except if we want the snake to grow
        if (!growSnake) 
        {
            mSnakeTrail.remove(mSnakeTrail.size() - 1);
        }

        int index = 0;
        for (Coordinate c : mSnakeTrail) 
        {
            if (index == 0) 
            {
                setTile(YELLOW_STAR, c.x, c.y);
            } 
            else 
            {
                setTile(RED_STAR, c.x, c.y);
            }
            index++;
        }

    }

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		
//		Toast.makeText(mContext, "-----onFling-----", Toast.LENGTH_LONG).show();
		int hx=(int)(e2.getX()-e1.getX());
		int vy=(int)(e1.getY()-e2.getY());
		
		
		boolean isX=Math.abs(hx)>Math.abs(vy);
		if(isX&&Math.abs(hx)<this.FLING_MIN_DISTANCE)
		{
			return false;
		}
		else if(!isX&&Math.abs(vy)<this.FLING_MIN_DISTANCE)
		{
			return false;
		}

		if(isX)
		{
			if(hx>0)//向右边滑动
			{
				  if (mDirection != WEST) 
		            {
		                mNextDirection = EAST;
		            }
		            return (true);
			}
			else
			{
				 if (mDirection != EAST) 
		            {
		                mNextDirection = WEST;
		            }
		            return (true);
			}
		}
		else
		{
			if(vy>0)//向上滑动
			{
		          if (mMode == READY | mMode == LOSE) 
		            {
		                /*
		                 * At the beginning of the game, or the end of a previous one,
		                 * we should start a new game.
		                 */
		                initNewGame();
		                setMode(RUNNING);
		                update();
		                return (true);
		            }

		            if (mMode == PAUSE) 
		            {
		                /*
		                 * If the game is merely paused, we should just continue where
		                 * we left off.
		                 */
		                setMode(RUNNING);
		                update();
		                return (true);
		            }

		            if (mDirection != SOUTH) 
		            {
		                mNextDirection = NORTH;
		            }
		            return (true);
			}
			else
			{
				if (mDirection != NORTH) 
	            {
	                mNextDirection = SOUTH;
	            }
	            return (true);
			}
		}
		
//		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
//		Toast.makeText(mContext, "-----onTouch-----", Toast.LENGTH_LONG).show();
		return mGestureDetector.onTouchEvent(event);
	}    
}
