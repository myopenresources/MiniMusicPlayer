package com.cjh.listener;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;

import com.cjh.music.MusicPlayer;
import com.cjh.ui.EastPanel;
import com.cjh.util.MusicConstant;

/**
 * ���ֲ���������
 * @author cjh
 *
 */
public class MusicControllerListener implements ControllerListener{
	
	/**
	 * ����
	 */
	private static  MusicControllerListener m = new MusicControllerListener();
	
	
	/**
	 * ˽�й��췽��
	 */
	private MusicControllerListener(){
		
	}
	
	/**
	 * ���ʵ��
	 * @return
	 */
	public static MusicControllerListener getInstance(){
		if(null==m){
			System.out.println("���ֲ�����������ʼ������");
			return null;
		}
		return m;
	}
	
	
	
	/**
	 * ʵ�ֿ��Ʒ���
	 */
	public void controllerUpdate(ControllerEvent ce) {
        
		//������Ž���
        if (ce instanceof EndOfMediaEvent) {
            try {
            	//��ͣһ��
                Thread.sleep(1000);
                
            } catch (InterruptedException ex) {
            	System.out.println(ex);
            }
            
            //˳�򲥷�
            if (MusicConstant.MODE1==EastPanel.getplayModel()) {
            	MusicPlayer.orderMusic();
            
            //�б�ѭ��
            }else if(MusicConstant.MODE2 == EastPanel.getplayModel()){
            	MusicPlayer.nextMusic();
            
            //����ѭ��
            } else if (MusicConstant.MODE3 == EastPanel.getplayModel()) {
            	MusicPlayer.singleMuisc();
                
            //�������
            } else if (MusicConstant.MODE4 == EastPanel.getplayModel()) {
                MusicPlayer.randomMusic();
                
            //���򲥷�
            } else if(MusicConstant.MODE5 == EastPanel.getplayModel()){
            	MusicPlayer.invertedOrderMusic(); 
            }
        }
    }
}
