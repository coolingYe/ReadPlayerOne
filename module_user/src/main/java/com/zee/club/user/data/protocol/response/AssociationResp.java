package com.zee.club.user.data.protocol.response;

import android.os.Parcel;
import android.os.Parcelable;

public class AssociationResp implements Parcelable {

    int associationId;
    String associationName;
    boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public AssociationResp(int associationId, String associationName) {
        this.associationId = associationId;
        this.associationName = associationName;
    }

    protected AssociationResp(Parcel in) {
        associationId = in.readInt();
        associationName = in.readString();
    }

    public static final Creator<AssociationResp> CREATOR = new Creator<AssociationResp>() {
        @Override
        public AssociationResp createFromParcel(Parcel in) {
            return new AssociationResp(in);
        }

        @Override
        public AssociationResp[] newArray(int size) {
            return new AssociationResp[size];
        }
    };

    public int getAssociationId() {
        return associationId;
    }

    public String getAssociationName() {
        return associationName;
    }

    @Override
    public String toString() {
        return "AssociationResp{" +
                "associationId=" + associationId +
                ", associationName='" + associationName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(associationId);
        parcel.writeString(associationName);
    }
}
