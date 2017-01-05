package tonnysunm.com.acornote.model;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import tonnysunm.com.acornote.AcornoteApplication;
import tonnysunm.com.acornote.R;

public class Folder extends RealmObject implements Parcelable {

    @PrimaryKey
    public int id;

    public String title;
    public String color;

    public Folder(){ }

    public Folder(int id, String title, String color) {
        this.id = id;
        this.title = title;
        this.color = color;
    }

    //getter and setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    static public void createInitialData(Realm realm) {
        final Resources res = AcornoteApplication.getContext().getResources();
        String[] titles = res.getStringArray(R.array.folderTitlesDemo);
        String[] colors = res.getStringArray(R.array.folderColors);

        int folderId = 1;
        int itemId = 1;
        for (String title : titles) {
            Folder folder = realm.createObject(Folder.class, folderId++);
            folder.setTitle(title);
            String color = colors[new Random().nextInt(colors.length)];
            folder.setColor(color);

            String[] titles1 = res.getStringArray(R.array.itemTitlesDemo);

            for (String item1 : titles1) {
                Item item = realm.createObject(Item.class, itemId++);
                item.setTitle(item1);
                item.setFolder(folder);
            }
        }
    }

    //CRUD
    public static void findAllAsync(RealmChangeListener<RealmResults<Folder>> listener) {
        RealmResults<Folder> result = AcornoteApplication.REALM.where(Folder.class).findAllAsync();
        result.addChangeListener(listener);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.color);
    }

    protected Folder(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.color = in.readString();
    }

    public static final Parcelable.Creator<Folder> CREATOR = new Parcelable.Creator<Folder>() {
        @Override
        public Folder createFromParcel(Parcel source) {
            return new Folder(source);
        }

        @Override
        public Folder[] newArray(int size) {
            return new Folder[size];
        }
    };
}