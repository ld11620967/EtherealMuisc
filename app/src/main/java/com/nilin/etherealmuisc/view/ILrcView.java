package com.nilin.etherealmuisc.view;

import java.util.List;


public interface ILrcView {
    
    /**
     * set the lyric rows to display
     */
    void setLrc(List<LrcRow> lrcRows);
    
    /**
     * seek to position of lyric rows
     */
    void seekLrc(int position);
    
    /**
     * seek lyric row to special time
     * @time time to be seek
     * 
     */
    void seekLrcToTime(long time);
    
    void setListener(LrcViewListener l);
    
    public static interface LrcViewListener {
        
        /**
         * when lyric line was seeked by user
         */
        void onLrcSeeked(int newPosition, LrcRow row);
    }
}
