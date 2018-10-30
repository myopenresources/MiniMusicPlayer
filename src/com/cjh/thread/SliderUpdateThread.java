package com.cjh.thread;


/**
 * 滑动条更新线程
 * @author 
 */
public class SliderUpdateThread {

	/**
	 * runnable类
	 */
    private static SliderUpdateRunnable su = new SliderUpdateRunnable();
    
    /**
     * 线程
     */
    private static Thread st = new Thread(su);

    private SliderUpdateThread() {
    	
    }

    /**
     * 初始化线程
     */
    public static void initThread() {
        if (st.isAlive()) {
            st.interrupt();
        }
        su.init();
        st = new Thread(su);
    }

    /**
     * 获得线程
     * @return
     */
    public static Thread getThread() {
        return st;
    }

    /**
     * 线程停止
     */
    public static void pause() {
        su.pause();
    }

    /**
     * 线程开启
     */
    public static void resume() {
        su.resume();
    }

}
