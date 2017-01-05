package tonnysunm.com.acornote.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import tonnysunm.com.acornote.AcornoteApplication;

public class Item extends RealmObject implements Parcelable {
    @PrimaryKey
    public int id;

    public String title;
    public String imgUrl;
    public String url;

    public Folder folder;

    public Item() {}

    public Item(int id, String title, String imgUrl, String url, Folder folder) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
        this.url = url;
        this.folder = folder;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean hasImage() { return imgUrl != null; }

    public boolean hasUrl() {
        return url != null;
    }

    //CRUD
    public static void findAllAsync(RealmChangeListener<RealmResults<Item>> listener) {
        RealmResults<Item> result = AcornoteApplication.REALM.where(Item.class).findAllAsync();
        result.addChangeListener(listener);
    }

    public static void findAllInFolderAsync(int folderId, RealmChangeListener<RealmResults<Item>> listener) {
        if (folderId == 0) {
            findAllAsync(listener);
            return;
        }

        RealmResults<Item> result = AcornoteApplication.REALM.where(Item.class)
                .equalTo("folder.id", folderId)
                .findAllAsync();

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
        dest.writeString(this.imgUrl);
        dest.writeString(this.url);
        dest.writeParcelable(this.folder, flags);
    }

    protected Item(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.imgUrl = in.readString();
        this.url = in.readString();
        this.folder = in.readParcelable(Folder.class.getClassLoader());
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}