package com.nilin.etherealmuisc;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;


/**
 * Created by liangd on 2017/10/25.
 */
/**
 * Created by liangd on 2017/10/25.
 */
@Entity
public class Music {
    @Id(autoincrement = true)
    private Long id;
    @Index(unique = true) // 唯一性
    private String song;
    private String singer;
    private String path;
    @Generated(hash = 570329522)
    public Music(Long id, String song, String singer, String path) {
        this.id = id;
        this.song = song;
        this.singer = singer;
        this.path = path;
    }
    @Generated(hash = 1263212761)
    public Music() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSong() {
        return this.song;
    }
    public void setSong(String song) {
        this.song = song;
    }
    public String getSinger() {
        return this.singer;
    }
    public void setSinger(String singer) {
        this.singer = singer;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }


}
