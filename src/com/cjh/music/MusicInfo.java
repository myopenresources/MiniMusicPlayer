package com.cjh.music;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 音乐信息
 * @author cjh
 *
 */
public class MusicInfo {

	/**
	 * 音乐名称
	 */
	private String musicName = "";
	
	/**
	 * 音乐路径
	 */
	private String musicPath = "";
	
	/**
	 * 歌手
	 */
	private String musicArtist;
	
	/**
	 * 专辑
	 */
	private String musicAlbum;
	
	/**
	 * 路径中的音乐名称
	 */
	private String musicOrignName;
	
	/**
	 * 年
	 */
	private String year;
	
	/**
	 * 注释
	 */
	private String comment;
	
	/**
	 * 编码
	 */
	private final Charset GBK = Charset.forName("GBK");
	
	/**
	 * 音乐128字节信息
	 */
	private byte[] tag;
	
	/**
	 * 是否可播放
	 */
    private boolean canPlay = true;

	private RandomAccessFile raf;
    
    
    /**
     * 默认构造方法
     * @param musicPath
     */
    public MusicInfo(String musicPath) {
        this.musicPath = musicPath;
        loadMusicInfo();
        readSongInfo();
    }
    
    /**
     * 加载歌曲信息
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
     * 读取歌曲信息
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
     * 验证音乐是否完整
     * @return
     */
    private boolean isValid() {
        byte[] array = Arrays.copyOfRange(tag, 0, 3);
        String s = new String(array, GBK);
        return "TAG".equals(s);
    }
    
    /**
     * 获取音乐信息
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
		//如果无法获得歌曲名，使用路径中的名称
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
			musicArtist="未知歌手";
		}
		return musicArtist;
	}

	public void setMusicArtist(String musicArtist) {
		this.musicArtist = musicArtist;
	}

	public String getMusicAlbum() {
		if(null==musicAlbum || "".equals(musicAlbum)){
			musicAlbum="未知专辑";
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
			year = "未知时间";
		}
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getComment() {
		if(null==comment || "".equals(comment)){
			comment="无";
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
