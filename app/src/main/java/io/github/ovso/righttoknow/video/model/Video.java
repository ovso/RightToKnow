package io.github.ovso.righttoknow.video.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 9. 7
 */

@Getter @IgnoreExtraProperties @ToString @EqualsAndHashCode(callSuper = false) public class Video {
  private String title;
  private String date;
  private String url;
  private String video_id;

  public static ArrayList<Video> convertToItems(DataSnapshot dataSnapshot) {
    final Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
    final ArrayList<Video> items = new ArrayList<>();
    while (iterator.hasNext()) {
      Video v = iterator.next().getValue(Video.class);
      items.add(v);
    }
    return sorted(items);
  }

  private static ArrayList<Video> sorted(ArrayList<Video> items) {
    Comparator<Video> comparator = new Comparator<Video>() {
      @Override public int compare(Video t1, Video t2) {
        return t2.getDate().compareTo(t1.getDate());
      }
    };
    Collections.sort(items, comparator);
    return items;
  }
}
