package com.cjh.music;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * ������Ϣ
 * @author cjh
 *
 */
public class MusicInfo {

	/**
	 * ��������
	 */
	private String musicName = "";
	
	/**
	 * ����·��
	 */
	private String musicPath = "";
	
	/**
	 * ����
	 */
	private String musicArtist;
	
	/**
	 * ר��
	 */
	private String musicAlbum;
	
	/**
	 * ·���е���������
	 */
	private String musicOrignName;
	
	/**
	 * ��
	 */
	private String year;
	
	/**
	 * ע��
	 */
	private String comment;
	
	/**
	 * ����
	 */
	private final Charset GBK = Charset.forName("GBK");
	
	/**
	 * ����128�ֽ���Ϣ
	 */
	private byte[] tag;
	
	/**
	 * �Ƿ�ɲ���
	 */
    private boolean canPlay = true;

	private RandomAccessFile raf;
    
    
    /**
     * Ĭ�Ϲ��췽��
     * @param musicPath
     */
    public MusicInfo(String musicPath) {
        this.musicPath = musicPath;
        loadMusicInfo();
        readSongInfo();
    }
    
    /**
     * ���ظ�����Ϣ
     */
    private void loadMusicInfo() {
        try {
            File musicFile = new File(musicPath);
            musicOrignName = musicFile.getName().substring(0, musicFile.getName().length() - 4);
            raf = new RandomAccessFile(musicFile, "r");
            byte[] array = new byte[128];
            raf.seek(raf.length() - 128);
            raf.read(array); 
            tag = array;
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } 
    }
    
    /**
     * ��ȡ������Ϣ
     */
    private void readSongInfo() {
        if (tag.length != 128 || !isValid()) {
            return;
        } else {
            musicName = getString(3, 33);
            musicArtist = getString(33, 63);
            musicAlbum = getString(63, 93);
            year = getString(93, 97);
            comment = getString(97, 125);
        }
    }
    
    /**
     * ��֤�����Ƿ�����
     * @return
     */
    private boolean isValid() {
        byte[] array = Arrays.copyOfRange(tag, 0, 3);
        String s = new String(array, GBK);
        return "TAG".equals(s);
    }
    
    /**
     * ��ȡ������Ϣ
     * @param from
     * @param to
     * @return
     */
    private String getString(int from, int to) {
        byte[] array = Arrays.copyOfRange(tag, from, to);
        int length = 0;
        for (byte b : array) {
            if (b != 0) {
                length++;
            } else {
                break;
            }
        }
        return new String(array, 0, length, GBK).trim();
    }

	public String getMusicName() {
		//����޷���ø�������ʹ��·���е�����
		if("".equals(musicName)){
			musicName = musicOrignName;
		}
		return musicName;
	}
	
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}

	public String getMusicPath() {
		return musicPath;
	}

	public void setMusicPath(String musicPath) {
		this.musicPath = musicPath;
	}

	public String getMusicArtist() {
		if(null==musicArtist || "".equals(musicArtist)){
			musicArtist="δ֪����";
		}
		return musicArtist;
	}

	public void setMusicArtist(String musicArtist) {
		this.musicArtist = musicArtist;
	}

	public String getMusicAlbum() {
		if(null==musicAlbum || "".equals(musicAlbum)){
			musicAlbum="δ֪ר��";
		}
		return musicAlbum;
	}

	public void setMusicAlbum(String musicAlbum) {
		this.musicAlbum = musicAlbum;
	}

	public String getMusicOrignName() {
		return musicOrignName;
	}

	public void setMusicOrignName(String musicOrignName) {
		this.musicOrignName = musicOrignName;
	}

	public String getYear() {
		if(null==year || "".equals(year)){
			year = "δ֪ʱ��";
		}
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getComment() {
		if(null==comment || "".equals(comment)){
			comment="��";
		}
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public byte[] getTag() {
		return tag;
	}

	public void setTag(byte[] tag) {
		this.tag = tag;
	}

	public boolean isCanPlay() {
		return canPlay;
	}

	public void setCanPlay(boolean canPlay) {
		this.canPlay = canPlay;
	}

	public Charset getGBK() {
		return GBK;
	}
    
    
    
    
}
