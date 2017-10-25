package com.nilin.etherealmuisc;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;


/**
 * Created by liangd on 2017/10/25.
 */
@Entity
public class Music {
    @Id(autoincrement = true)
    private Long id;
    @Index(unique = true) // 唯一性
    private String music;
    private String path;
    @Generated(hash = 889838720)
    public Music(Long id, String music, String path) {
        this.id = id;
        this.music = music;
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
    public String getMusic() {
        return this.music;
    }
    public void setMusic(String music) {
        this.music = music;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }

}
