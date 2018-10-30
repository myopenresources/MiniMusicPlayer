package com.cjh.listener;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;

import com.cjh.music.MusicPlayer;
import com.cjh.ui.EastPanel;
import com.cjh.util.MusicConstant;

/**
 * 音乐播放器监听
 * @author cjh
 *
 */
public class MusicControllerListener implements ControllerListener{
	
	/**
	 * 单例
	 */
	private static  MusicControllerListener m = new MusicControllerListener();
	
	
	/**
	 * 私有构造方法
	 */
	private MusicControllerListener(){
		
	}
	
	/**
	 * 获得实例
	 * @return
	 */
	public static MusicControllerListener getInstance(){
		if(null==m){
			System.out.println("音乐播放器监听初始化出错！");
			return null;
		}
		return m;
	}
	
	
	
	/**
	 * 实现控制方法
	 */
	public void controllerUpdate(ControllerEvent ce) {
        
		//如果播放结束
        if (ce instanceof EndOfMediaEvent) {
            try {
            	//暂停一秒
                Thread.sleep(1000);
                
            } catch (InterruptedException ex) {
            	System.out.println(ex);
            }
            
            //顺序播放
            if (MusicConstant.MODE1==EastPanel.getplayModel()) {
            	MusicPlayer.orderMusic();
            
            //列表循环
            }else if(MusicConstant.MODE2 == EastPanel.getplayModel()){
            	MusicPlayer.nextMusic();
            
            //单曲循环
            } else if (MusicConstant.MODE3 == EastPanel.getplayModel()) {
            	MusicPlayer.singleMuisc();
                
            //随机播放
            } else if (MusicConstant.MODE4 == EastPanel.getplayModel()) {
                MusicPlayer.randomMusic();
                
            //倒序播放
            } else if(MusicConstant.MODE5 == EastPanel.getplayModel()){
            	MusicPlayer.invertedOrderMusic(); 
            }
        }
    }
}
