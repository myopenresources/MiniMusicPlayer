package com.cjh.thread;


/**
 * �����������߳�
 * @author 
 */
public class SliderUpdateThread {

	/**
	 * runnable��
	 */
    private static SliderUpdateRunnable su = new SliderUpdateRunnable();
    
    /**
     * �߳�
     */
    private static Thread st = new Thread(su);

    private SliderUpdateThread() {
    	
    }

    /**
     * ��ʼ���߳�
     */
    public static void initThread() {
        if (st.isAlive()) {
            st.interrupt();
        }
        su.init();
        st = new Thread(su);
    }

    /**
     * ����߳�
     * @return
     */
    public static Thread getThread() {
        return st;
    }

    /**
     * �߳�ֹͣ
     */
    public static void pause() {
        su.pause();
    }

    /**
     * �߳̿���
     */
    public static void resume() {
        su.resume();
    }

}
